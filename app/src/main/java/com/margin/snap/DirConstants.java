package com.margin.snap;

import android.os.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by : mr.lu
 * Created at : 2019-11-05 at 12:19
 * Description:
 */
public class DirConstants {

    /**
     * DIRECTORY_PICTURES
     */


    public static final String ALBUM_OCR_BANKCARD = "ocr_bankcard";
    public static final String ALBUM_OCR_IDCARD = "ocr_idcard";
    public static final String ALBUM_OCR_DRIVINGLICENCE = "ocr_drivinglicence";
    //启动页
    public static final String ALBUM_SPLASH = "splash";
    //相机
    public static final String ALBUM_CAMERA = "camera";
    /**
     * DIRECTORY_DOCUMENTS
     */
    public static final String DOCUMENT_AGREEMENT = "agreement";

    public static final String DOWNLOAD_APK = "apk";


    public static final Map<String, String> DIRECTORY_PARENT = new HashMap<String, String>() {{

        put(ALBUM_OCR_BANKCARD, Environment.DIRECTORY_PICTURES);
        put(ALBUM_OCR_IDCARD, Environment.DIRECTORY_PICTURES);
        put(ALBUM_OCR_DRIVINGLICENCE, Environment.DIRECTORY_PICTURES);
        put(ALBUM_SPLASH, Environment.DIRECTORY_PICTURES);
        put(ALBUM_CAMERA, Environment.DIRECTORY_PICTURES);

        put(DOCUMENT_AGREEMENT, Environment.DIRECTORY_DOCUMENTS);
        put(DOWNLOAD_APK, Environment.DIRECTORY_DOWNLOADS);
    }};


}
