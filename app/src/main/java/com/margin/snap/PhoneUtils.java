package com.margin.snap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by : mr.lu
 * Created at : 2019-11-08 at 09:30
 * Description:
 */
public class PhoneUtils {

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) ApplicationUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getDeviceId() : null;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 手机恢复出厂设置后会重置
     *
     * @return
     */
    public static String getAndroidId() {

        return Settings.System.getString(ApplicationUtils.getApp().getContentResolver(), Settings.Secure.ANDROID_ID);
    }



    @SuppressLint("MissingPermission")
    public static String getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) ApplicationUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSubscriberId() : null;
        } catch (Exception e) {
            return "";
        }
    }

}
