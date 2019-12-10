package com.margin.snap;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by : mr.lu
 * Created at : 2019-11-04 at 13:51
 * Description:
 */
public class FileUtils {

    private static final String APP_ROOT_DIRNAME = "com.margin.snap";
    /**
     * Android Q 因为使用了私有目录，实际文件路径和Q 以前的路径并不相同，所以我配置了两个fileProvider;
     * Android Q : 的fileProvider name使用了系统默认名称"files"，在context.getExternalFilesDir(type),传null时就不使用了
     * beforeAndroid Q:这个路径和fileProvider中保持一致；
     */
    private static final String PROVIDER_NAME_BEFORE_Q = "snap_files";
    private static final String TAG = "FileUtils";


    public static String getAppRootDirName() {
        return APP_ROOT_DIRNAME;

    }

    /**
     * 根据名称 在app目录下 创建存储路径
     *
     * @param context
     * @param dir      目录。注意，这个目录必须提前定义好在{@link DirConstants}
     * @param fileName
     * @return
     */
    public static String generateFilePath(Context context, String dir, String fileName) {
        String path = getDirectory(context, dir)
                + File.separator + fileName;
        Log.d(TAG, "getFilePath: path=" + path);
        return path;
    }

    /**
     * 创建App文件目录
     */
    public static void createAppDir() {
        try {

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/"
                        + APP_ROOT_DIRNAME;
                Log.d(TAG, "createAppDir: app_abs_path = " + path);
                File f = new File(path);
                if (!f.exists()) {
                    f.mkdirs();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建不同的相册
     * 注意：当前api>=29 的时候，文件目录是私有的
     * 注意：文件dirName必须提前定义好
     * 注意：Android Q 和 之前的，文件目录路径并不相同！
     *
     * @param context
     * @param dirName 文件名，必须提前定义好
     * @return
     */
    public static String getDirectory(Context context, String dirName) {
        try {
            if (!DirConstants.DIRECTORY_PARENT.containsKey(dirName)) {
                throw new IllegalArgumentException("请在 DirConstants.DIRECTORY_PARENT 中定义好文件名和所属类型");
            }
            File file = null;


            //android Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //系统目录 + app私有目录 + 自定义目录
                //path=/storage/emulated/0/Android/data/packageName/files/Pictures/camera/1572936803409.jpg
                file = new File(context.getExternalFilesDir(DirConstants.DIRECTORY_PARENT.get(dirName)), dirName);

            } else {
                //path=/storage/emulated/packageName/files/Pictures/camera/1572936803409.jpg
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + getAppRootDirName()
                        + File.separator + PROVIDER_NAME_BEFORE_Q
                        + File.separator + DirConstants.DIRECTORY_PARENT.get(dirName)
                        + File.separator + dirName;
                file = new File(path);

            }
            if (!file.mkdirs()) {
                Log.e(TAG, "Directory not created");
            }
            String path = file.getAbsolutePath();
            Log.d(TAG, "createAlbumDir: path = " + path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "createAlbumDir: ", e);
        }
        return null;
    }


    public static boolean isExternalStorageWritable() {
        final String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
