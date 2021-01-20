package com.margin.snap.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.margin.snap.framwork.SnapConfiguration;
import com.margin.snap.framwork.SnapConfigurator;

import net.vidageek.mirror.dsl.Mirror;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by : mr.lu
 * Created at : 2019-11-25 at 13:59
 * Description:
 */
public class DeviceInfoUtils {
    private static final String TAG = "DeviceInfoUtils";

    private static Context getContext() {
        return SnapConfigurator.getInstance().getConfiguration(SnapConfiguration.APP_CONTEXT);

    }

    /**
     * 获取顶部activity名称
     *
     * @return
     */
    public static String getRunningAppProcess() {


        return SnapConfigurator.getInstance().getTopRunningActivityName();
    }


    /**
     * 获取【屏幕尺寸】a*b
     *
     * @return
     */
    public static String getScreenSize() {
        String width = String.valueOf(getScreenWidth());
        String height = String.valueOf(getScreenHeight());
        return width + "*" + height;
    }

    /**
     * 获取【屏幕的宽度】（单位：px）
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取【屏幕的高度】（单位：px）
     *
     * @return 屏幕高
     */
    public static int getScreenHeight() {
        return getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static String getTotalRam() {


        ActivityManager.MemoryInfo info = getMemoryInfo();


        return Formatter.formatFileSize(getContext(), info.totalMem);

    }


    public static String getAvailableRam() {
        ActivityManager.MemoryInfo info = getMemoryInfo();

        return Formatter.formatFileSize(getContext(), info.availMem);

    }

    private static ActivityManager.MemoryInfo getMemoryInfo() {
        final Context context = getContext();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return info;
    }

    public static final int INTERNAL_STORAGE = 0;
    public static final int EXTERNAL_STORAGE = 1;

    /**
     * 获取手机存储 ROM 信息
     * <p>
     * type： 用于区分内置存储于外置存储的方法
     * <p>
     * 内置SD卡 ：INTERNAL_STORAGE = 0;
     * <p>
     * 外置SD卡： EXTERNAL_STORAGE = 1;
     **/
    public static String getStorageInfo(int type) {

        String path = getStoragePath(getContext(), type);
        /**
         * 无外置SD 卡判断
         * **/
        if (isSDCardMount() == false || TextUtils.isEmpty(path)) {
            return "无外置SD卡";
        }

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
        String stotageInfo;

        long blockCount = statFs.getBlockCountLong();
        long bloackSize = statFs.getBlockSizeLong();
        long totalSpace = bloackSize * blockCount;

        long availableBlocks = statFs.getAvailableBlocksLong();
        long availableSpace = availableBlocks * bloackSize;

        stotageInfo = "可用/总共："
                + Formatter.formatFileSize(getContext(), availableSpace) + "/"
                + Formatter.formatFileSize(getContext(), totalSpace);

        return stotageInfo;

    }

    /**
     * 使用反射方法 获取手机存储路径
     **/
    private static String getStoragePath(Context context, int type) {


        return null;
    }

    /**
     * 判断SD是否挂载
     */
    public static boolean isSDCardMount() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取【所有ROM]（G）
     *
     * @return
     */
    public static String getTotalRom() {
        String path = getStoragePath(getContext(), INTERNAL_STORAGE);

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
//        String stotageInfo;

        long blockCount = statFs.getBlockCountLong();
        long bloackSize = statFs.getBlockSizeLong();
        long totalSpace = bloackSize * blockCount;

//        long availableBlocks = statFs.getAvailableBlocksLong();
//        long availableSpace = availableBlocks * bloackSize;

//        stotageInfo = "可用/总共："
//                + Formatter.formatFileSize(getContext(), availableSpace) + "/"
//                + Formatter.formatFileSize(getContext(), totalSpace);

        return Formatter.formatFileSize(getContext(), totalSpace);
    }

    /**
     * 【可用ROM] (G)
     *
     * @return
     */
    public static String getAvailableRom() {

        String path = getStoragePath(getContext(), INTERNAL_STORAGE);

        File file = new File(path);
        StatFs statFs = new StatFs(file.getPath());
//        String stotageInfo;

        long blockCount = statFs.getBlockCountLong();
        long bloackSize = statFs.getBlockSizeLong();
//        long totalSpace = bloackSize * blockCount;

        long availableBlocks = statFs.getAvailableBlocksLong();
        long availableSpace = availableBlocks * bloackSize;

//        stotageInfo = "可用/总共："
//                + Formatter.formatFileSize(getContext(), availableSpace) + "/"
//                + Formatter.formatFileSize(getContext(), totalSpace);

        return Formatter.formatFileSize(getContext(), availableSpace);
    }

    /**
     * 【获取电量】（%）
     *
     * @return
     */
    public static int getPowerPercent() {

        return SnapConfigurator.getInstance().getConfiguration(SnapConfiguration.BATTERY);
    }

    /**
     * 【CPU架构类型】
     *
     * @return
     */
    public static String getCPUType() {
        return Build.SUPPORTED_ABIS[0];
    }

    private final static int DEVICEINFO_UNKNOWN = 0;

    /**
     * 获取【CPU核心数】
     *
     * @return
     */
    public static int getCPUNum() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            // Gingerbread doesn't support giving a single application access to both cores, but a
            // handful of devices (Atrix 4G and Droid X2 for example) were released with a dual-core
            // chipset and Gingerbread; that can let an app in the background run without impacting
            // the foreground application. But for our purposes, it makes them single core.
            return 1;
        }
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (SecurityException e) {
            cores = DEVICEINFO_UNKNOWN;
        } catch (NullPointerException e) {
            cores = DEVICEINFO_UNKNOWN;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 获取【CPU名称】
     *
     * @return
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
//            for (int i = 0; i < array.length; i++) {
//            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取手机【Android版本】
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取当前手机【系统语言】。
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取【设备ID】
     * 手机恢复出厂设置后会重置
     *
     * @return
     */
    public static String getAndroidId() {

        return Settings.System.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取 【IMEI】【设备ID】码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI 码
     */
    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return getAndroidId();
            } else {
                if (tm != null && !TextUtils.isEmpty(tm.getDeviceId())) {
                    return tm.getDeviceId();
                } else {
                    return getAndroidId();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getIMEI: ", e);
            return getAndroidId();
        }
    }

    /**
     * 判断是【否是平板】
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean getDeviceType() {

        return (getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 【厂商】
     *
     * @return
     */
    public static String getManufacturer() {

        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取【运营商名称】,需要sim卡
     */
    public static String getOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String operatorName1 = telephonyManager.getSimOperatorName();

        String operator = telephonyManager.getSimOperator();
        Log.d(TAG, "getOperatorName1: " + operatorName1);
        String operatorName = "";
        if (operator != null) {

            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
//                operatorName = "CMCC";
                operatorName = "中国移动";
            } else if (operator.equals("46001") || operator.equals("46006")|| operator.equals("46009")) {
//                operatorName = "CUCC";
                operatorName = "中国联通";
            } else if (operator.equals("46003") || operator.equals("46011")) {
//                operatorName = "CTCC";
                operatorName = "中国电信";
            } else {
                operatorName = "其它";
            }
        }
        return operatorName;

//        //经测试，AndroidQ 那不到这个字段；
//        if (!TextUtils.isEmpty(operatorName1)) {
//            if (operatorName1.equals("CMCC")) {
//                return "中国移动";
//            } else if (operatorName1.equals("CUCC")) {
//                return "中国联通";
//            } else if (operatorName1.equals("CTCC")) {
//                return "中国电信";
//            } else {
//                return "其它";
//            }
//        }
//
//        return operatorName1;
    }


    /**
     * 检查【是否root】，true-root
     *
     * @return
     */
    public static boolean isRoot() {

        try {

            return checkRootFile() != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断是否包含root文件
     *
     * @return
     */
    private static File checkRootFile() {
        File file = null;
        String[] paths = {"/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su"};
        for (String path : paths) {
            file = new File(path);
            if (file.exists()) return file;
        }
        return file;
    }


    /**
     * 检查【WIFI是否可用】
     *
     * @return
     */
    public static boolean isWifiEnabled() {

        WifiManager wifiMgr = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 获取【WIFI名称】
     *
     * @return
     */
    public static String getConnectWifiSsid() {

        if (!isWifiEnabled()) return "WIFI is not enable ";

        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        String wifiInfo1 = wifiInfo.getSSID().replace("\"", "")

                .replace("\"", "");

        return wifiInfo1;

    }


    /**
     * 获取【当前时间】
     *
     * @return
     */
    public static String getTimeNow() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(Calendar.getInstance().getTime());
    }

    /**
     * 系统 【运行时间】
     *
     * @return
     */
    public static String getPerformancePeriod() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(getPerformanceTimeMilles());
    }

    /**
     * 系统上次【开机时间】
     * 上次开机时间 = 现在 - 运行时间；
     *
     * @return
     */
    public static String getLastBootTime() {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long nowStamp = Calendar.getInstance().getTimeInMillis();
            Log.d(TAG, "getLastBootTime now: " + df.format(nowStamp));
            Long performanceTimeMilles = getPerformanceTimeMilles();
            Log.d(TAG, "getLastBootTime performanceTimeMilles: " + df.format(performanceTimeMilles));
            Long lastRebootTimeMilles = nowStamp - performanceTimeMilles;
            return df.format(lastRebootTimeMilles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 系统【运行时间】，单位纳秒
     *
     * @return
     */
    public static long getPerformanceTimeMilles() {

        return SystemClock.elapsedRealtimeNanos() / 1000000;
    }

    /**
     * 获取系统【时间格式】
     *
     * @return
     */
    public static String get24HoutMode() {

        return android.text.format.DateFormat.is24HourFormat(getContext()) ? "24" : "12";
    }

    public static String getSystemDateFormat() {
        // TODO: 2019-11-27
        return null;
    }

    /**
     * 获取【屏幕亮度】
     *
     * @return
     */
    public static String getScreenBrightness() {
        try {
            float curBrightnessValue = android.provider.Settings.System.getInt(
                    getContext().getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            return String.valueOf(curBrightnessValue);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "function: ", e);
        }
        return null;
    }

    /**
     * 获取【休眠时间/锁屏时间】
     * 单位 S
     *
     * @return
     */
    public static String getSleepTime() {
        try {
            //毫秒格式的
            int screenOffTime = Settings.System.getInt(getContext().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT);
            //毫秒格式
            return String.valueOf(screenOffTime);

        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return "0";
    }

    /**
     * 获得【时区】
     *
     * @return
     */
    public static String getTimeZone() {
        //设置时区？
//        mDummyDate = Calendar.getInstance();
//        mDummyDate.setTimeZone(now.getTimeZone());
//        mDummyDate.set(now.get(Calendar.YEAR), 11, 31, 13, 0, 0);
        Calendar now = Calendar.getInstance();
        return getTimeZoneText(now.getTimeZone(), true);

    }

    private static String getTimeZoneText(TimeZone tz, boolean includeName) {
        Date now = new Date();

        SimpleDateFormat gmtFormatter = new SimpleDateFormat("ZZZZ");
        gmtFormatter.setTimeZone(tz);
        String gmtString = gmtFormatter.format(now);
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        Locale l = Locale.getDefault();
        boolean isRtl = TextUtils.getLayoutDirectionFromLocale(l) == View.LAYOUT_DIRECTION_RTL;
        gmtString = bidiFormatter.unicodeWrap(gmtString,
                isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);

        if (!includeName) {
            return gmtString;
        }

        return gmtString;
    }


    /**
     * 获取【手机号码】
     *
     * @return
     */
    public static String getPhoneNumber() {
        TelephonyManager phoneMgr = (TelephonyManager) getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return null;
        } else {
            return phoneMgr.getLine1Number();
        }
    }

    /**
     * 获取 【IP地址】、【IP-v4地址】、【IP-v6地址】
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @param useIPv4 是否用 IPv4 ,true - IPv4,false - IPv6
     * @return IP 地址
     */
    public static String getIPAddress(final boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回 10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
//                        Log.d(TAG, "getIPAddress: original ip : " + hostAddress);
                        //ip-V4 192.168.43.68
                        //ip-V6 fe80::9cf7:d5ff:fea6:e52b%wlan0
//                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
//                        if (useIPv4) {
//                            if (isIPv4) return hostAddress;
//                        } else {
//                            if (inetAddress instanceof Inet6Address) {
//                                int index = hostAddress.indexOf('%');
//                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
//                            }
//                        }
                        if (useIPv4) {
                            if (inetAddress instanceof Inet4Address) {
                                return hostAddress;
                            }
                        } else {
                            if (inetAddress instanceof Inet6Address) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取【WIFI-Ip地址】
     *
     * @return
     */
    public static String getWIFI_IPAddress() {

        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
        return ipAddress;

    }

    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * WIFI-ip地址
     */
    public final static int WIFI_INFO_IP = 1;
    /**
     * WIFI-网关
     */
    public final static int WIFI_INFO_GATEWAY = 2;
    /**
     * WIFI-名称
     */
    public final static int WIFI_INFO_NAME = 3;
    /**
     * WIFI-mac地址
     */
    public final static int WIFI_INFO_MAC = 4;
    /**
     * WIFI-子网掩码
     */
    public final static int WIFI_INFO_NETMASK = 5;

    /**
     * <H6>获取WIFI信息</H6>
     *
     * <p>
     * SSID:（Service Set Identifier）AP唯一的ID码，用来区分不同的网络，最多可以有32个字符，无线终端和AP的
     * SSID必须相同方可通信。无线网卡设置了不同的SSID就可以进入不同网络，SSID通常由AP广播出来，通过XP自带的扫描
     * 功能可以相看当前区域内的SSID。出于安全考虑可以不广播SSID，此时用户就要手工设置SSID才能进入相应的网络。
     * 简单说，SSID就是一个局域网的名称，只有设置为名称相同SSID的值的电脑才能互相通信。</p>
     * <p>
     * ESSID:是infrastructure的应用，一个扩展的服务装置ESS (Extended service set)由二个或多个BSS组成，
     * 形成单一的子网。使用者可于ESS上roaming及存取BSSs中的任何资料，其中Access Points必须设定相同的ESSID
     * 及channel才能允许roaming。 </p>
     * <p>
     * BSS:是一种特殊的Ad-hoc LAN的应用，一个无线网络至少由一个连接到有线网络的AP和若干无线工作站组成，
     * 这种配置称为一个基本服务装置BSS (Basic Service Set)。一群计算机设定相同的BSS名称，即可自成一个group，
     * 而此BSS名称，即所谓BSSID。</p>
     * <p>
     * BSSID: 通常，手机WLAN中，bssid其实就是无线路由的MAC地址.
     * </p>
     * <p>
     * ESSID: 也可认为是SSID, WIFI 网络名
     * </p>
     *
     * @param name <br>
     *             WIFI-ip地址   :       WIFI_INFO_IP = 1;
     *             WIFI-网关     :  WIFI_INFO_GATEWAY = 2;
     *             WIFI-名称     :     WIFI_INFO_NAME = 3;
     *             WIFI-MAC地址  :      WIFI_INFO_MAC = 4;
     *             WIFI-子网掩码  :  WIFI_INFO_NETMASK = 5;
     * @return
     */
    public static String getWIFIInfo(final int name) {

        try {
            WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (!wifiManager.isWifiEnabled()) {
                return null;
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String wifiIp = long2ip(wifiInfo.getIpAddress());
                String wifiMac = wifiInfo.getMacAddress();   //此方法在高版本时返回异常数据
                String ssid = wifiInfo.getSSID();
                if (ssid.contains("\"")) {
                    ssid = ssid.replaceAll("\"", "");
                }
                String bssid = wifiInfo.getBSSID();
                Log.d("DeviceInfoUtils", "当设备处于wifi状态时，获取当前设备的ip wifiIp ->" + wifiIp);
                Log.d("DeviceInfoUtils", "当设备处于wifi可用状态时，获取当前设备的mac地址 wifiMac ->" + wifiMac);
                Log.d("DeviceInfoUtils", "当前连接的无线网络名称 ssid ->" + ssid);
                Log.d("DeviceInfoUtils", "当前连接无线网络的BSSID bssid ->" + bssid);
                DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                String gateway = long2ip(dhcpInfo.gateway);
                String wifiNetmask = long2ip(dhcpInfo.netmask);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dns1", long2ip(dhcpInfo.dns1));
                jsonObject.put("dns2", long2ip(dhcpInfo.dns2));
                String dnsAddress = jsonObject.toString();
                Log.d("DeviceInfoUtils", "当前连接无线网络的网关地址 gateway ->" + gateway);
                Log.d("DeviceInfoUtils", "当前连接无线网络的子网掩码 wifiNetmask ->" + wifiNetmask);
                Log.d("DeviceInfoUtils", "当前活动网络的dns地址 dnsAddress ->" + dnsAddress);
                String info = null;
                switch (name) {
                    case WIFI_INFO_IP:
                        info = wifiIp;
                        break;
                    case WIFI_INFO_GATEWAY:
                        info = gateway;
                        break;
                    case WIFI_INFO_NAME:
                        info = ssid;
                        break;
                    case WIFI_INFO_MAC:
                        info = wifiMac;
                        break;
                    case WIFI_INFO_NETMASK:
                        info = wifiNetmask;
                        break;
                }
                return info;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }

    /**
     * 获取【网关】
     *
     * @return
     */
    public static String getGateway() {
        String[] arr;
        try {
            Process process = Runtime.getRuntime().exec("ip route list table 0");
            String data = null;
            BufferedReader ie = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String string = in.readLine();

            arr = string.split("\\s+");
            return arr[2];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     */
    public static String getMac() {


        return null;
    }

    /**
     * 获取【蓝牙MAC地址】
     *
     * @return
     */
    public static String getBluetoothMACAddress() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                Object bluetoothManagerService = new Mirror().on(adapter).get().field("mService");
                if (bluetoothManagerService == null) {
                    Log.e(TAG, " could't find bluethothManagerService");
                    return null;
                }
                Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();
                if (address != null && address instanceof String) {
                    Log.d(TAG, "getBluetoothMACAddress: address =  " + address);
                    return (String) address;
                }
            } else {
                @SuppressLint("ServiceCast") BluetoothAdapter adapter = (BluetoothAdapter) getContext().getSystemService(Context.BLUETOOTH_SERVICE);

                return adapter.getAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * @return
     */
    public static String getDeviceName() {

//        DevicePolicyManager m = (DevicePolicyManager) getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        final String deviceName = Build.DEVICE;
        final String deviceName = bluetoothAdapter.getName();
        final String brand = Build.BRAND;
        final String model = Build.MODEL;
        final String user = Build.USER;

        Log.d(TAG, "getDeviceName: deviceName: " + deviceName);
        Log.d(TAG, "getDeviceName: brand: " + brand);
        Log.d(TAG, "getDeviceName: model: " + model);
        Log.d(TAG, "getDeviceName: user: " + user);

        return null;
    }


}
