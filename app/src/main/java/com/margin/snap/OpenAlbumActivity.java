package com.margin.snap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.margin.snap.framwork.BaseTopActivity;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileBatchCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OpenAlbumActivity extends BaseTopActivity implements View.OnClickListener {

    private static final String TAG = "OpenAlbumActivity";
    private final int ALBUM_REQUEST_CODE = 11;
    RecyclerView recyclerView;
    Button btnOpenAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_album);
        recyclerView = findViewById(R.id.album_list);
        btnOpenAlbum = findViewById(R.id.btn_album_open);
    }


    private void openAlbum(final int number) {

        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
//                .btnBgColor(ContextCompat.getColor(this, R.color.default_blue))
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
//                .backResId(R.mipmap.ic_back_white)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
//                .titleBgColor(ContextCompat.getColor(this, R.color.default_blue))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(number)
                .build();

        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, ALBUM_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_album_open:

                openAlbum(3);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra("result")) {
            List<String> pathList = data.getStringArrayListExtra("result");
            ArrayList<String> localPaths = new ArrayList<>(pathList);
            String[] paths = {};
            localPaths.toArray(paths);

            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();

            Tiny.getInstance().source(paths).batchAsFile().withOptions(options).batchCompress(new FileBatchCallback() {
                @Override
                public void callback(boolean isSuccess, String[] outfiles, Throwable t) {
                    Log.d(TAG, "callback: SUCCESS = " + isSuccess);
                    if (outfiles != null && outfiles.length > 0) {
                        StringBuffer sb = new StringBuffer("all file path :");
                        for (String path : outfiles) {
                            sb.append("\n" + path);
                        }

                        Log.d(TAG, "callback: " + sb.toString());
                    }
                }

            });
        }
    }
}
