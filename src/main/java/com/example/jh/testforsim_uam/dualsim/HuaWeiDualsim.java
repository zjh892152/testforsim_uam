package com.example.jh.testforsim_uam.dualsim;

import java.lang.reflect.Method;
import java.util.Objects;

import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 未使用
 */
public class HuaWeiDualsim extends DualsimBase {
    private int simId_1;
    private int simId_2;
    // private String imei_1;
    // private String imei_2;
//	private String imsi_1;
//	private String imsi_2;

    public HuaWeiDualsim(Context context) {
//        Object obj = null;
//		Object obj = context.getSystemService("phone_msim");
//		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        try {
//            Class<?> cx = Class.forName("android.telephony.MSimTelephonyManager");
//            simId_1 = 0;
//            simId_2 = 1;
//            // Method getDeviceid = cx.getMethod("getDeviceId", int.class);
//            Method getSubscriberid = cx.getMethod("getSubscriberId", int.class);
//            // imei_1 = (String) getDeviceid.invoke(obj, simId_1);
//            // imei_2 = (String) getDeviceid.invoke(obj, simId_2);
//            simImsi1 = (String) getSubscriberid.invoke(obj, simId_1);
//            simImsi2 = (String) getSubscriberid.invoke(obj, simId_2);
//            if (isDouble(simImsi1, simImsi2)) {
//                isDoubleSim = true;
//            } else {
//                isDoubleSim = false;
//            }
//        } catch (Exception e) {
//            Log.d("mydebug", "try check Huawei dual sim failed");
//            isDoubleSim = false;
//        }
    }

    private boolean isDouble(String imsi_1, String imsi_2) {
        if (TextUtils.isEmpty(imsi_1) || TextUtils.isEmpty(imsi_2)) {
            return false;
        } else {
            if ((imsi_1.startsWith("46000") || imsi_1.startsWith("46002")) && (imsi_2.startsWith("46000") || imsi_2.startsWith("46002"))) {
                return true;
            }

        }
        return false;
    }

    @Override
    public int getSimState(int simID) {
        return 0;
    }

    @Override
    public String getImsi(int simID) {
        return null;
    }

    @Override
    public boolean sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, int simId) {
        return false;
    }
}
