package com.margin.snap.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.margin.snap.framwork.SnapConfiguration;
import com.margin.snap.framwork.SnapConfigurator;

import java.lang.reflect.Method;

/**
 * Created by : mr.lu
 * Created at : 2019-11-27 at 16:55
 * Description: SIM卡工具
 */
public class SIMUtils {
    private static final String TAG = "SIMUtils";

    private static Context getContext() {
        return SnapConfigurator.getInstance().getConfiguration(SnapConfiguration.APP_CONTEXT);

    }

    /**
     * 获得卡槽数，默认为1
     *
     * @return 返回卡槽数
     */
    public static int getSimCount() {
        int count = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SubscriptionManager mSubscriptionManager = (SubscriptionManager) getContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                if (mSubscriptionManager != null) {
                    count = mSubscriptionManager.getActiveSubscriptionInfoCountMax();
                    return count;
                }
            } catch (Exception ignored) {
            }
        }
        try {
            count = Integer.parseInt(getReflexMethod("getPhoneCount"));
        } catch (Exception ignored) {
        }
        return count;
    }

    /**
     * 获取Sim卡的国家代码
     *
     * @return 国家代码
     */
    public static String getSimCountryIso() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSimCountryIso() : null;
    }

    /**
     * 通过反射调取@hide的方法
     *
     * @param predictedMethodName 方法名
     * @return 返回方法调用的结果
     */
    private static String getReflexMethod(String predictedMethodName) {
        String result = null;
        TelephonyManager telephony = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Method getSimID = telephonyClass.getMethod(predictedMethodName);
            Object ob_phone = getSimID.invoke(telephony);
            if (ob_phone != null) {
                result = ob_phone.toString();
            }
        } catch (Exception e) {
            Log.e(TAG, "getReflexMethod: ", e);
        }
        return result;
    }
}
