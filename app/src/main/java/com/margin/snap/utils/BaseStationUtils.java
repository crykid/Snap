package com.margin.snap.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.margin.snap.framwork.SnapConfiguration;
import com.margin.snap.framwork.SnapConfigurator;

/**
 * Created by : mr.lu
 * Created at : 2019-11-27 at 15:49
 * Description:
 */
public class BaseStationUtils {
    private static final String TAG = "BaseStationUtils";

    private static Context getContext() {
        return SnapConfigurator.getInstance().getConfiguration(SnapConfiguration.APP_CONTEXT);

    }

    /**
     * 基站号CELL_ID
     */
    public final static int CELLLOCATION_INFO_LAC = 1;
    /**
     * 基站定位区号LAC
     */
    public final static int CELLLOCATION_INFO_CELLID = 2;

    /**
     * mobile CountryCode Base - MCC
     */
    public final static int CELLLOCATION_INFO_MCC = 3;

    /**
     * mobile NetCode Base - MNC
     */
    public final static int CELLLOCATION_INFO_MNC = 4;

    /**
     * <h6>获取【基站LAC】、【基站CI】/【基站CELLID】、【基站MCC】、【基站MNC】</h6>
     * <p>
     * MCC：Mobile Country Code，移动国家码，MCC的资源由国际电联（ITU）统一分配和管理，唯一识别移动用户
     * 所属的国家，共3位，中国为460;
     * </p>
     * <p>
     * MNC:Mobile Network Code，移动网络码，共2位，中国移动TD系统使用00，中国联通GSM系统使用01，中国移
     * 动GSM系统使用02，中国电信CDMA系统使用03，一个典型的IMSI号码为460030912121001
     * </P>
     * 注：需要权限-定位
     * 附：免费查询接口：http://www.cellocation.com/interfac/
     * 例如：http://api.cellocation.com/cell/?mcc=460&mnc=0&lac=9365&ci=4843&output=json
     *
     * @param name :
     *             CELLLOCATION_INFO_LAC : 基站号CELL_ID;
     *             CELLLOCATION_INFO_LAC : 基站定位区号LAC;
     *             CELLLOCATION_INFO_MCC : 基站移动国家码MCC;
     *             CELLLOCATION_INFO_MNC : 基站移动网络码MNC;
     * @return
     */
    public static String getCellInfo(int name) {
        if (name <= 0) name = CELLLOCATION_INFO_LAC;
        TelephonyManager manager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

        try {
            //6.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return null;
                } else {

                    //---1---基站定位区号
                    int lac = -1;
                    //---2--- 基站号cell id
                    int cid = -1;
                    String operator = manager.getNetworkOperator();
                    //---3---mobileCountryCodeBase
                    int mcc = Integer.parseInt(operator.substring(0, 3));
                    //---4---mobileNetCodeBase
                    int mnc = Integer.parseInt(operator.substring(3));
                    CellLocation cel = manager.getCellLocation();

                    int nPhoneType = manager.getPhoneType();
                    //---移动联通--- GsmCellLocation
                    if (nPhoneType == 2 && cel instanceof GsmCellLocation) {

                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
                        // 基站号cell id
                        cid = gsmCellLocation.getCid();
                        if (cid > 0) {
                            if (cid != 65535) {
                                //基站定位区号
                                lac = gsmCellLocation.getLac();
                            }
                        }
                        //---电信---
                    } else if (nPhoneType == 2 && cel instanceof CdmaCellLocation) {
                        Log.e(TAG, "-----------------》电信");
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cel;
                        //cdma网络识别码
                        lac = cdmaCellLocation.getNetworkId();
                        //系统标识号,-1 unknown
                        final int sid = cdmaCellLocation.getSystemId();
                        //CDMA网络识别码
                        final int nid = cdmaCellLocation.getNetworkId();
                        //CDMA基站识别码
                        final int bid = cdmaCellLocation.getBaseStationId();
                        cid = bid;
                    }


                    //-------根据类型返回需要的字段--------
                    String info = null;
                    switch (name) {

                        case CELLLOCATION_INFO_LAC:
                            info = String.valueOf(lac);
                            break;
                        case CELLLOCATION_INFO_CELLID:
                            info = String.valueOf(cid);
                            break;
                        case CELLLOCATION_INFO_MCC:
                            info = String.valueOf(mcc);
                            break;
                        case CELLLOCATION_INFO_MNC:
                            info = String.valueOf(mnc);
                            break;
                    }

                    return info;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getCellInfo: ", e);
        }
        return null;
    }


}
