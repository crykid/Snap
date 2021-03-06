package com.margin.snap;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;

import com.margin.snap.framwork.BaseTopActivity;
import com.margin.snap.framwork.SnapConfigurator;
import com.margin.snap.utils.FileUtils;
import com.margin.snap.utils.PhoneUtils;
import com.margin.snap.utils.TestMethodUtils;
import com.margin.snap.utils.testmethod.ClickTest;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileBatchCallback;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseTopActivity {
    private static final String TAG = "MainActivity";


    ImageView ivTarget;
    TextView tvDeviceId;

    @BindView(R.id.ll_main_testcontainer)
    LinearLayoutCompat mLlTestContainer;
    Uri mUri = null;
    String path = null, parentPath = null;

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            //	level加%就是当前电量了
            SnapConfigurator.getInstance().bettery(level);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //获取电量
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        TestMethodUtils.find(this, mLlTestContainer);


        ivTarget = findViewById(R.id.iv_main_target);
        tvDeviceId = findViewById(R.id.tv_main_device_id);


//        initSnapDir();


        getDeviceInfo();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult p: " + permissions[0]);
        Log.d(TAG, "onRequestPermissionsResult result : " + grantResults[0]);
    }

    /**
     * 获取设备信息
     */
    private void getDeviceInfo() {

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PermissionChecker.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
//        }

        //空
//        String deviceId = PhoneUtils.getIMEI();
        //每次变化的
        String uniqueID = UUID.randomUUID().toString();
        tvDeviceId.setText(" UniqueId = " + uniqueID
                + "\nAndroidId = " + PhoneUtils.getAndroidId()
                + "\nIMSI = " + PhoneUtils.getIMSI());

    }

    /**
     * 初始化项目文件
     */
    private void initSnapDir() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES))
            FileUtils.createAppDir();
            FileUtils.getDirectory(this, DirConstants.ALBUM_CAMERA);
        }
    }


    public void openCamera() {

        // 步骤一：创建存储照片的文件
        path = FileUtils.generateFilePath(this, DirConstants.ALBUM_CAMERA, System.currentTimeMillis() + ".jpg");
        File f = new File(path);

        File parent = f.getParentFile();
        parentPath = parent.getAbsolutePath();
        Log.d(TAG, "openCamera:parentPath = " + parentPath + " IS_REDIRECTORY = " + parent.isDirectory());

        File file = new File(path);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //步骤二：Android 7.0及以上获取文件 Uri
            mUri = FileProvider.getUriForFile(this, "com.margin.snap.provider", file);
        } else {
            //步骤三：获取文件Uri
            mUri = Uri.fromFile(file);
        }

        //步骤四：调取系统拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, 100);
    }


    @ClickTest("camera")
    public void opnCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        } else {
            openCamera();
        }
    }

    @ClickTest("permission")
    public void permission() {
        startActivity(new Intent(this, PermissionActivity.class));
    }

    @ClickTest()
    public void openAlbum() {
        startActivity(new Intent(this, OpenAlbumActivity.class));
    }

    @ClickTest
    public void deviceInfo() {
        startActivity(new Intent(this, DeviceInfoActivity.class));
    }

    @ClickTest
    public void theme() {
        startActivity(new Intent(this, ThemeTestActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {

            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
            options.isKeepSampling = false;
            options.overrideSource = true;
            options.size = 300;
            Tiny.getInstance().source(path).asFile().withOptions(options).compress(new FileCallback() {
                @Override
                public void callback(boolean isSuccess, String outfile, Throwable t) {
                    Log.d(TAG, "callback1: IS_SUCCESS = " + isSuccess + " outfile = " + outfile);
                    File file = new File(path);
                    Log.d(TAG, "callback1: " + file.length() / 1024);

                }
            });

            Log.d(TAG, "callback1: AAAA");


        }
    }


    private void compress() {
        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        Tiny.getInstance().source("").batchAsFile().withOptions(options).batchCompress(new FileBatchCallback() {
            @Override
            public void callback(boolean isSuccess, String[] outfiles, Throwable t) {

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
        }
    }
}
