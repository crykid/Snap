package com.margin.snap;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.margin.snap.framwork.BaseTopActivity;
import com.margin.snap.framwork.SnapConfigurator;
import com.margin.snap.utils.BaseStationUtils;
import com.margin.snap.utils.DeviceInfoUtils;
import com.margin.snap.utils.SIMUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceInfoActivity extends BaseTopActivity {
    private static final String TAG = "DeviceInfoActivity";
    @BindView(R.id.tv_device_info)
    TextView tvDeviceInfo;
    @BindView(R.id.tv_device_content)
    TextView tvDeviceContent;
    private final String LINE_FEED = "\n -- ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        ButterKnife.bind(this);

        final String content = getContent();

        tvDeviceContent.setText(content);
        Log.d(TAG, "onCreate: " + content);
    }

    private String getContent() {

        StringBuffer sb = new StringBuffer("-");
        sb.append("Top Activity : ")
                .append(SnapConfigurator.getInstance().getTopRunningActivityName())
                .append(LINE_FEED)
                .append("Total ram : ")
                .append(DeviceInfoUtils.getTotalRam())
                .append(LINE_FEED)
                .append("Available ram : ")
                .append(DeviceInfoUtils.getAvailableRam())
                .append(LINE_FEED)
                .append("Rom info : ")
                .append(DeviceInfoUtils.getStorageInfo(DeviceInfoUtils.INTERNAL_STORAGE))
                .append(LINE_FEED)
                .append("Total Rom : ")
                .append(DeviceInfoUtils.getTotalRom())
                .append(LINE_FEED)
                .append("Available Rom : ")
                .append(DeviceInfoUtils.getAvailableRom())
                .append(LINE_FEED)
                .append("CPU name : ")
                .append(DeviceInfoUtils.getCpuName())
                .append(LINE_FEED)
                .append("Cpu Type : ")
                .append(DeviceInfoUtils.getCPUType())
                .append(LINE_FEED)
                .append("Cpu num : ")
                .append(DeviceInfoUtils.getCPUNum())
                .append(LINE_FEED)
                .append("OS Version : ")
                .append(DeviceInfoUtils.getOSVersion())
                .append(LINE_FEED)
                .append("Model : ")
                .append(DeviceInfoUtils.getModel())
                .append(LINE_FEED)
                .append("Power : ")
                .append(DeviceInfoUtils.getPowerPercent())
                .append("%")
                .append(LINE_FEED)
                .append("Language : ")
                .append(DeviceInfoUtils.getDeviceDefaultLanguage())
                .append(LINE_FEED)
                .append("AndroidId : ")
                .append(DeviceInfoUtils.getAndroidId())
                .append(LINE_FEED)
                .append("DeviceTimeNow : ").append(DeviceInfoUtils.getTimeNow()).append(LINE_FEED)
                .append("Last BootTime : ")
                .append(DeviceInfoUtils.getLastBootTime())
                .append(LINE_FEED)
                .append("DevideOperationTime : ")
                .append(DeviceInfoUtils.getPerformanceTimeMilles() + "")
                .append(LINE_FEED)
                .append("HourFormat : ").append(DeviceInfoUtils.get24HoutMode()).append(LINE_FEED)
                .append("ScreenBrightness : ").append(DeviceInfoUtils.getScreenBrightness()).append(LINE_FEED)
                .append("LockTime : ").append(DeviceInfoUtils.getSleepTime()).append(LINE_FEED)
                .append("TimeZone : ").append(DeviceInfoUtils.getTimeZone()).append(LINE_FEED)
                .append("PhoneNumber : ").append(DeviceInfoUtils.getPhoneNumber()).append(LINE_FEED)
                //wifi
                .append("WIFI-Name : ").append(DeviceInfoUtils.getWIFIInfo(DeviceInfoUtils.WIFI_INFO_NAME)).append(LINE_FEED)
                .append("WIFI-gateWay : ").append(DeviceInfoUtils.getWIFIInfo(DeviceInfoUtils.WIFI_INFO_GATEWAY)).append(LINE_FEED)
                .append("WIFI-IP: ").append(DeviceInfoUtils.getWIFIInfo(DeviceInfoUtils.WIFI_INFO_IP)).append(LINE_FEED)
                .append("WIFI-MAC : ").append(DeviceInfoUtils.getWIFIInfo(DeviceInfoUtils.WIFI_INFO_MAC)).append(LINE_FEED)
                .append("WIFI-MASK : ").append(DeviceInfoUtils.getWIFIInfo(DeviceInfoUtils.WIFI_INFO_NETMASK)).append(LINE_FEED)

                .append("IpAdress v4 : ").append(DeviceInfoUtils.getIPAddress(true)).append(LINE_FEED)
                .append("IpAdress v6: ").append(DeviceInfoUtils.getIPAddress(false)).append(LINE_FEED)
                .append("Gateway: ").append(DeviceInfoUtils.getGateway()).append(LINE_FEED)
                //基站信息
                .append("Cell-ID: ").append(BaseStationUtils.getCellInfo(BaseStationUtils.CELLLOCATION_INFO_CELLID)).append(LINE_FEED)
                .append("Cell-LAC: ").append(BaseStationUtils.getCellInfo(BaseStationUtils.CELLLOCATION_INFO_LAC)).append(LINE_FEED)
                .append("Cell-MCC: ").append(BaseStationUtils.getCellInfo(BaseStationUtils.CELLLOCATION_INFO_MCC)).append(LINE_FEED)
                .append("Cell-MNC: ").append(BaseStationUtils.getCellInfo(BaseStationUtils.CELLLOCATION_INFO_MNC)).append(LINE_FEED)
                //SIM信息
                .append("SIM-INFO mcc: ").append(SIMUtils.getSimCountryIso()).append(LINE_FEED)
                .append("Bluetooth-MAC: ").append(DeviceInfoUtils.getBluetoothMACAddress()).append(LINE_FEED)
                .append("device_info : ").append(DeviceInfoUtils.getDeviceName()).append(LINE_FEED)
                .append("OperatorName : ").append(DeviceInfoUtils.getOperatorName()).append(LINE_FEED)
                .append("DeviceID : ").append(DeviceInfoUtils.getDeviceId()).append(LINE_FEED)

        ;

        return sb.toString();
    }
}
