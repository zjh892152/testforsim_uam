package com.example.jh.testforsim_uam.dualsim;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 联发科芯片双卡类
 */
public class MTKDualsim extends DualsimBase {

    private static final String MTK_PLATFORM_KEY = "ro.mediatek.platform";
    private static final String MTK_GIONEE_PLATFORM_KEY = "ro.gn.platform.support";
    private TelephonyManager myTelephonyManager;
    private Object myMTKTMInstance;
    private Class myGeminiSmsManagerClass;
    private Object mySmsManagerExInstance;

    public MTKDualsim(Context myContext) {

        myTelephonyManager = (TelephonyManager) myContext.getSystemService(Activity.TELEPHONY_SERVICE);
        myMTKTMInstance = getDefault();
        isDoubleSim = isMTKDoubleSim();
        sim0State = getSimState(0);
        if (isDoubleSim) ;
        sim1State = getSimState(1);
        sim0Imsi = getImsi(0);
        if (isDoubleSim)
            sim1Imsi = getImsi(1);
        initSM();
    }


    public static boolean isMTKSystem() {
        boolean isMTKSystem = false;
        try {
            //Normal MTK Platform
            String normalMTKPlatform;
            Log.d("mydebug", "check MTKSystem");
            if (!TextUtils.isEmpty(normalMTKPlatform = SystemProperties.getProperty(MTK_PLATFORM_KEY))) {
                if (normalMTKPlatform.startsWith("MT") || normalMTKPlatform.startsWith("mt"))
                    isMTKSystem = true;
            }
            //Gionee MTK Platform
            if (!isMTKSystem) {
                String gioneeMTKPlatform;
                Log.d("mydebug", "check MTKSystem");
                if (!TextUtils.isEmpty(gioneeMTKPlatform = SystemProperties.getProperty(MTK_GIONEE_PLATFORM_KEY))) {
                    if (gioneeMTKPlatform.startsWith("MT") || gioneeMTKPlatform.startsWith("mt"))
                        isMTKSystem = true;
                }
            }
            Log.d("mydebug", "check MTKSystem");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMTKSystem;
    }


    /**
     * 判断当前mtk手机是否是 双卡手机
     *
     * @return
     */
    private boolean isMTKDoubleSim() {
        boolean isDoubleSim = false;
        try {
            if (!(isDoubleSim = (boolean) myTelephonyManager.getClass().getDeclaredMethod("mtkGeminiSupport").invoke(myTelephonyManager)))
                isDoubleSim = (boolean) myTelephonyManager.getClass().getDeclaredMethod("isMultiSimEnabled").invoke(myTelephonyManager);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mydebug", "isMTKDoubleSim-error:" + e.getMessage());
        }
        return isDoubleSim;
    }

    private void initSM() {
        Class smsManagerClass;
        try {
            if (myGeminiSmsManagerClass == null)
                myGeminiSmsManagerClass = Class.forName("android.telephony.gemini.GeminiSmsManager");
            mySmsManagerExInstance = (smsManagerClass = Class.forName("com.mediatek.telephony.SmsManagerEx")).getDeclaredMethod("getDefault").invoke(smsManagerClass.newInstance());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Object getDefault() {
        Class<?> clazz;
        Object mtkTMInstance = null;
        try {
            mtkTMInstance = (clazz = Class.forName("com.mediatek.telephony.TelephonyManagerEx")).getDeclaredMethod("getDefault").invoke(clazz);
        } catch (Exception e) {
            Log.d("mydebug", "isMTKDoubleSim-error:" + e.getMessage());
        }
        return mtkTMInstance;
    }

    @Override
    public int getSimState(int simID) {
        try {
            if (myMTKTMInstance != null)
                return (int) myMTKTMInstance.getClass().getDeclaredMethod("getSimState", int.class).invoke(myMTKTMInstance, simID);
            else if (myTelephonyManager != null)
                return (int) myTelephonyManager.getClass().getDeclaredMethod("getCallStateGemini", int.class).invoke(myMTKTMInstance, simID);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mydebug", "isMTKDoubleSim-error:" + e.getMessage());
        }

        return -1;
    }

    @Override
    public String getImsi(int simID) {
        try {
            if (myMTKTMInstance != null)
                return (String) myMTKTMInstance.getClass().getDeclaredMethod("getSubscriberId", int.class).invoke(myMTKTMInstance, simID);
            else if (myTelephonyManager != null)
                return (String) myTelephonyManager.getClass().getDeclaredMethod("getSubscriberIdGemini", int.class).invoke(myMTKTMInstance, simID);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("mydebug", "isMTKDoubleSim-error:" + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, int simId) {

        try {
            if (mySmsManagerExInstance != null) {
                mySmsManagerExInstance.getClass().getDeclaredMethod("sendDataMessage", new Class[]{String.class, String.class, short.class, byte[].class, PendingIntent.class, PendingIntent.class}).invoke(mySmsManagerExInstance, new Object[]{destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent});
                return true;
            } else {
                myGeminiSmsManagerClass.getDeclaredMethod("sendDataMessageGemini", new Class[]{String.class, String.class, short.class, byte[].class, PendingIntent.class, PendingIntent.class}).invoke(myGeminiSmsManagerClass.newInstance(), new Object[]{destinationAddress, scAddress, destinationPort, data, sentIntent, deliveryIntent});
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
