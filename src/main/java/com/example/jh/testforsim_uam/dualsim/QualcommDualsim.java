package com.example.jh.testforsim_uam.dualsim;


import java.util.Locale;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


/**
 * 高通芯片双卡类
 */
public class QualcommDualsim extends DualsimBase {
    private static TelephonyManager myTelephonyManager = null;

    private static final String QUALCOMM_CMDC_PLATFORM = "persist.loc.nlp_name";
    private static final String QUALCOMM_CMDC_PLATFORM_KEY = "com.qualcomm.location";
    private static final String QUALCOMM_XIAOMI_PLATFORM = "ro.boot.hardware";
    private static final String QUALCOMM_XIAOMI_PLATFORM_KEY = "qcom";
    //采用的处理器
    private static final String QUALCOMM_NUBIA_PLATFORM_KEY = "ro.product.board";
    private static final String QUALCOMM_NUBIA_PLATFORM_VALUE = "msm";
    //主板平台
    private static final String QUALCOMM_BOARD_PLATFORM = "ro.board.platform";
    //根据主板型号判断
    private static final String QUALCOMM_BOARD_PLATFORM_KEY = "hi3630";
    private Object myQualcommTMInstance;
    private Context myContext;

    public QualcommDualsim(Context myContext) {

        this.myContext = myContext;
        isDoubleSim = true;
        sim0State = getSimState(0);
        sim1State = getSimState(1);
        sim0Imsi = getImsi(0);
        sim1Imsi = getImsi(1);

    }


    /**
     * 判断是否高通系统
     * 通过CPU，主板型号判断较局限
     *
     * @param myContext
     * @return
     */
    public static boolean isQualcommSystem(Context myContext) {

        //移动
        if (isCMDualSimQualcommSystem(myContext)) {
            Log.d("mydebug", "CM-System");
            return true;
        }

        //努比亚
        if (isNubiaDualSimQualcommSystem(myContext)) {
            Log.d("mydebug", "NUBIA-System");
            return true;
        }

        //小米
        if (isXiaoMiQualcommSystem()) {
            Log.d("mydebug", "XIAOMI-System");
            return true;
        }

        //华为手机
        if (isBoardQualcommSystem(myContext)) {
            Log.d("mydebug", "HUAWEI-System");
            return true;
        }

        return false;
    }

    /**
     * 判断移动双卡系统
     *
     * @param myContext
     * @return
     */
    private static boolean isCMDualSimQualcommSystem(Context myContext) {

        if (!"CMDC".equals(android.os.Build.MANUFACTURER)) {
            Log.d("mydebug", "!cmdc");
            return false;
        }

        if (currentapiVersion >= 21) {
            try {
                return (boolean) (myTelephonyManager = (TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE)).getClass().getDeclaredMethod("isMultiSimEnabled").invoke(myTelephonyManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String execResult = SystemProperties.getProperty(QUALCOMM_CMDC_PLATFORM);
                if (!TextUtils.isEmpty(execResult)) {
                    if (execResult.equals(QUALCOMM_CMDC_PLATFORM_KEY))
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 判断努比亚双卡系统
     *
     * @param myContext
     * @return
     */
    private static boolean isNubiaDualSimQualcommSystem(Context myContext) {
        TelephonyManager telephonyManager;

        if (!("nubia".equals(android.os.Build.MANUFACTURER.toLowerCase(Locale.ENGLISH)))) {
            Log.d("mydebug", "!nubia");
            return false;
        }
        if (currentapiVersion >= 21) {
            try {
                return (boolean) (myTelephonyManager = (TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE)).getClass().getDeclaredMethod("isMultiSimEnabled").invoke(myTelephonyManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String execResult = SystemProperties.getProperty(QUALCOMM_NUBIA_PLATFORM_KEY);
                Log.d("mydebug", "nubia execResult:" + execResult);
                if (!TextUtils.isEmpty(execResult)) {
                    int index = 0;
                    if ((index = execResult.toLowerCase().indexOf(QUALCOMM_NUBIA_PLATFORM_VALUE)) >= 0) {
                        Log.d("mydebug", "nubia index:" + index);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    /**
     * 判断小米双卡系统
     *
     * @return
     */
    private static boolean isXiaoMiQualcommSystem() {
        if (!"xiaomi".equals(android.os.Build.MANUFACTURER.toLowerCase(Locale.ENGLISH))) {
            Log.d("mydebug", "!xiaomi");
            return false;
        }
        try {
            String execResult = SystemProperties.getProperty(QUALCOMM_XIAOMI_PLATFORM);
            if (!TextUtils.isEmpty(execResult)) {
                if (execResult.equals(QUALCOMM_XIAOMI_PLATFORM_KEY))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 判断华为双卡系统（某个型号）
     *
     * @param myContext
     * @return
     */
    private static boolean isBoardQualcommSystem(Context myContext) {
        TelephonyManager telephonyManager;

        if (!"huawei".equals(android.os.Build.MANUFACTURER.toLowerCase(Locale.ENGLISH))) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            try {
                return (boolean) (telephonyManager = (TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE)).getClass().getDeclaredMethod("isMultiSimEnabled").invoke(telephonyManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String execResult = SystemProperties.getProperty(QUALCOMM_BOARD_PLATFORM);
                Log.d("mydebug", "huawei-execResult:" + execResult);
                if (!TextUtils.isEmpty(execResult)) {
                    if (execResult.equals(QUALCOMM_BOARD_PLATFORM_KEY))
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    protected Object getDefault() {
        Class<?> clazz;
        try {
            return (clazz = Class.forName("android.telephony.MSimTelephonyManager")).getDeclaredMethod("getDefault").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected Object getSubScriptionId(int simID) {
        Class clazz;
        try {
            return (clazz = Class.forName("android.telephony.SubscriptionManager")).getDeclaredMethod("getSubId", int.class).invoke(clazz.newInstance(), simID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getSimState(int simID) {

        try {
            if (currentapiVersion >= 21) {
                if (myTelephonyManager == null)
                    myTelephonyManager = ((TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE));
                return (int) myTelephonyManager.getClass().getDeclaredMethod("getSimState", int.class).invoke(myTelephonyManager, simID);
            } else {
                if (myQualcommTMInstance == null)
                    myQualcommTMInstance = getDefault();
                return (int) myQualcommTMInstance.getClass().getDeclaredMethod("getSimState", int.class).invoke(myQualcommTMInstance, simID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mydebug", "getSimState-error:" + e.getMessage());
        }

        return -1;


    }


    @Override
    public String getImsi(int simID) {
        try {

            if (currentapiVersion >= 21) {
                if (myTelephonyManager == null)
                    myTelephonyManager = ((TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE));
                return (String) myTelephonyManager.getClass().getDeclaredMethod("getSubscriberId", int.class).invoke(myTelephonyManager);
            } else {
                if (myQualcommTMInstance == null)
                    myQualcommTMInstance = getDefault();
                return (String) myQualcommTMInstance.getClass().getMethod("getSubscriberId", int.class).invoke(myQualcommTMInstance, simID);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mydebug", "getImsi-error:" + e.getMessage());
        }
        return null;


    }

    @Override
    public boolean sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, int simId) {

        Class<?> smClass;
        Object smObject;
        try {
            (smObject = (smClass = Class.forName("android.telephony.MSimSmsManager")).getDeclaredMethod("getDefault").invoke(smClass.newInstance())).getClass().getDeclaredMethod("sendDataMessage", new Class[]{String.class, String.class, short.class, byte[].class, PendingIntent.class, PendingIntent.class}).invoke(smObject, new Object[]{destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * // Field descriptor #22 I
     * public static final int SIM_STATE_ABSENT = 1;
     * <p/>
     * // Field descriptor #22 I
     * public static final int SIM_STATE_PIN_REQUIRED = 2;
     * <p/>
     * // Field descriptor #22 I
     * public static final int SIM_STATE_PUK_REQUIRED = 3;
     * <p/>
     * // Field descriptor #22 I
     * public static final int SIM_STATE_NETWORK_LOCKED = 4;
     * <p/>
     * // Field descriptor #22 I
     * public static final int SIM_STATE_READY = 5;
     *
     * @param paramInt
     * @return
     */
//    public int getPhoneState(int paramInt) {
//        try {
//            Class<?> cx = Class.forName("android.telephony.TelephonyManager");
//            Method mtd = cx.getDeclaredMethod("getSimState", int.class);
//
//            return (Integer) mtd.invoke(myTelephonyManager, paramInt);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return TelephonyManager.SIM_STATE_ABSENT;
//
//    }

    // 发送数据短信
//    public boolean sendDataMsg(int type, String destAddr, String scAddr, short destPort, short srcPort, byte[] data, Intent sentIntent, Intent deliverIntent) {
//
//        try {
//            int[] subId;
//            Class<?> cx = Class.forName("android.telephony.SubscriptionManager");
//            Method mtd = cx.getMethod("getSubId", int.class);
//
//            subId = (int[]) mtd.invoke(cx, type - 1);
//
//            Class<?> smsMngCls = Class.forName("android.telephony.SmsManager");
//            Method getsubMtd = smsMngCls.getMethod("getSmsManagerForSubscriptionId", int.class);
//
//            SmsManager smsManager = (SmsManager) getsubMtd.invoke(null, subId[0]);
//
//            Class<?>[] sendDataMessagePamas = {String.class, String.class, Short.TYPE, Short.TYPE, byte[].class, PendingIntent.class, PendingIntent.class};
//            Method sendMsgMtd = smsMngCls.getDeclaredMethod("sendDataMessage", sendDataMessagePamas);
//
//            sendMsgMtd.invoke(smsManager, destAddr, scAddr, destPort, srcPort, data, sentIntent, deliverIntent);
//
//            return true;
//
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return false;
//
//    }


}
