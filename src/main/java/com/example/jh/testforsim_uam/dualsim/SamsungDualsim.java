package com.example.jh.testforsim_uam.dualsim;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;


/**
 * 三星双卡类
 * API>=21还未测试
 */
public class SamsungDualsim extends DualsimBase {
    private final static String CLASS_SAMSUNG_MULTISIMMANAGER = "com.samsung.android.telephony.MultiSimManager";
    private final static String CLASS_MULTISIMMANAGER = "android.telephony.MultiSimTelephonyManager";
    private Object mySamsungSMObject;

    public SamsungDualsim(Context context) {

        Log.d("mydebug", "SDK_INT = " + currentapiVersion);
        if (currentapiVersion < 21)
            isDoubleSim = checkByStrings();
        else
            isDoubleSim = checkByfunction();

        sim0State = getSimState(0);
        if (isDoubleSim)
            sim1State = getSimState(1);

        sim0Imsi = getImsi(0);
        if (isDoubleSim)
            sim1Imsi = getImsi(1);

//        try {
//            Class<?> cx = Class.forName("android.telephony.MultiSimTelephonyManager");
//            //Object obj = context.getSystemService("phone_msim");
//            Object obj = cx.newInstance();
//            int simId_1 = 0;
//            int simId_2 = 1;
//            Method getdefault = cx.getMethod("getDefault", int.class);
//            tm1 = (TelephonyManager) getdefault.invoke(obj, simId_1);
//            tm2 = (TelephonyManager) getdefault.invoke(obj, simId_2);
//             String imei_1 = tm1.getDeviceId();
//             String imei_2 = tm2.getDeviceId();
//            int simstatus_1 = tm1.getSimState();
//            int simstatus_2 = tm2.getSimState();
//            simImsi1= (String) tm1.getClass().getMethod("getSubscriberId").invoke(tm1);
//
//            simImsi1 = tm1.getSubscriberId();
//            simImsi2 = tm2.getSubscriberId();
//            Log.d("mydebug","simImsi1:"+simImsi1);
//            Log.d("mydebug","simImsi2:"+simImsi2);
//
//            if (isDouble(simImsi1, simImsi2))
//                isDoubleSim = true;
//            else
//                isDoubleSim = false;
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            isDoubleSim = false;
//            tm1 = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            simImsi1 = tm1.getSubscriberId();
//        }


//        Log.d("mydebug", "isDoubleSim:" + isDoubleSim);
//        Log.d("mydebug", "imsi_1:" + simImsi1);
//        if (isDoubleSim)
//            Log.d("mydebug", "imsi_2:" + simImsi2);
//        Log.d("mydebug", "sim_state1:" + getPhoneState(0));
//        if (isDoubleSim)
//            Log.d("mydebug", "sim_state2:" + getPhoneState(1));


    }

    public static boolean isSamsungSystem() {
        return Constants.SAMSUNG.equalsIgnoreCase(Build.MANUFACTURER);
    }

    private static boolean checkByStrings() {


        int simSlotCount = 0;
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.android.internal.telephony.MultiSimManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("mydebug", "ClassNotFoundException");
        }
        Method myMethod = null;
        try {
            myMethod = clazz.getDeclaredMethod("getSimSlotCount");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.d("mydebug", "NoSuchMethodException");
        }
        try {
            if ((simSlotCount = (int) myMethod.invoke(clazz.newInstance())) >= 2) {
                Log.d("mydebug", "android.internal.telephony.MultiSimManager card double");
                return true;
            } else {
                Log.d("mydebug", "android.internal.telephony.MultiSimManager card double");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private static boolean checkByfunction() {
        Class clazz = null;
        int simSlotCount = 0;

        try {
            clazz = Class.forName("com.samsung.android.telephony.MultiSimManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d("mydebug", "ClassNotFoundException");

        }
        if (clazz != null) {
            Method myMethod = null;
            try {
                myMethod = clazz.getDeclaredMethod("getSimSlotCount");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                Log.d("mydebug", "NoSuchMethodException");
            }
            try {
                if ((simSlotCount = (int) myMethod.invoke(clazz.newInstance())) >= 2) {
                    Log.d("mydebug", "com.samsung.android.telephony.MultiSimManager card double");
                    return true;
                } else {
                    Log.d("mydbeug", "com.samsung.android.telephony.MultiSimManager card single");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    private Object getTMDefault(int simID) {
        try {

            if (currentapiVersion < 21) {
                Class clazz = null;
                return (clazz = Class.forName(CLASS_MULTISIMMANAGER)).getDeclaredMethod("getDefault", int.class).invoke(clazz.newInstance(), getLogicalSimSlot(simID));
            } else {
                if (mySamsungSMObject == null)
                    return mySamsungSMObject = Class.forName(CLASS_SAMSUNG_MULTISIMMANAGER).newInstance();
                else
                    return mySamsungSMObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getSMDefault(int simID) {
        Class simManagerClass, simSmsManagerClass;
        try {
            if (currentapiVersion < 21) {
                return (simSmsManagerClass = Class.forName("android.telephony.MultiSimSmsManager")).getDeclaredMethod("getDefault", int.class).invoke(simSmsManagerClass.newInstance(),
                        (int) (simManagerClass = Class.forName("com.android.internal.telephony.MultiSimManager")).getDeclaredMethod("getLogicalSimSlot", int.class).invoke(simManagerClass.newInstance(), simID));
            } else
                return (simSmsManagerClass = Class.forName("android.telephony.SmsManager")).getDeclaredMethod("getDefault").invoke(simSmsManagerClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getLogicalSimSlot(int simID) {
        Class clazz = null;
        int slot = 0;
        try {
            clazz = Class.forName("com.android.internal.telephony.MultiSimManager");
            Method myMethod = clazz.getDeclaredMethod("getLogicalSimSlot", int.class);
            slot = (int) myMethod.invoke(clazz.newInstance(), simID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slot;
    }

    @Override
    public int getSimState(int simID) {
        //TelephonyManager
        Object myObject = null;
        try {

            if (currentapiVersion < 21)
                return (int) (myObject = getTMDefault(simID)).getClass().getDeclaredMethod("getSimState").invoke(myObject);
            else
                return (int) getTMDefault(simID).getClass().getDeclaredMethod("getSimState", int.class).invoke(getTMDefault(simID), simID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getImsi(int simID) {
        //TelephonyManager
        Object myObject = null;
        try {
            //为什么getDeclaredMethod会报NoSuchMethod
            if (currentapiVersion < 21)
                return (String) (myObject = getTMDefault(simID)).getClass().getMethod("getSubscriberId").invoke(myObject);
            else
                return (String) getTMDefault(simID).getClass().getDeclaredMethod("getSubscriberId", int.class).invoke(getTMDefault(simID), simID);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public boolean sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, int simId) {
        Object simSMObject = getSMDefault(simId);
        try {
            if (!(currentapiVersion < 21))
                simSMObject.getClass().getDeclaredMethod("setDefaultSubId", new Class<?>[]{int.class, long.class}).invoke(simSMObject, new Object[]{2, ((long[]) (simSMObject.getClass().getDeclaredMethod("getSubId", int.class).invoke(simSMObject, simId)))[0]});
            simSMObject.getClass().getDeclaredMethod("sendDataMessage", new Class[]{String.class, String.class, short.class, byte[].class, PendingIntent.class, PendingIntent.class}).invoke(simSMObject, new Object[]{destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent});
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //    public int getPhoneState(int paramInt) {
//        return paramInt == 0 ? tm1.getSimState() : tm2.getSimState();
//    }

//    private boolean isDouble(String imsi_1, String imsi_2) {
//       return false;
//        if (TextUtils.isEmpty(imsi_1) || TextUtils.isEmpty(imsi_2)) {
//            return false;
//        } else {
//            if (imsi_1.startsWith("46000") || imsi_1.startsWith("46002") && imsi_2.startsWith("46000") || imsi_2.startsWith("46002")) {
//                return true;
//            }
//
//        }
//        return false;
//    }

}
