package com.example.jh.testforsim_uam.dualsim;

import java.lang.reflect.Method;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.TelephonyManager;

public final class NormalDoulsimCheck extends DualsimBase {

    public NormalDoulsimCheck(Context context) {
//
//
//		TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
//
//		simImsi1 = telephonyManager.getDeviceId();
//		simImsi2 = null;
//
//		try
//		{
//			simImsi1 = getDeviceIdBySlot(context, "getDeviceIdGemini", 0);
//			simImsi2 = getDeviceIdBySlot(context, "getDeviceIdGemini", 1);
//		}
//		catch (GeminiMethodNotFoundException e)
//		{
//			e.printStackTrace();
//
//			try
//			{
//				simImsi1 = getDeviceIdBySlot(context, "getDeviceId", 0);
//				simImsi2 = getDeviceIdBySlot(context, "getDeviceId", 1);
//			}
//			catch (GeminiMethodNotFoundException e1)
//			{
//				// Call here for next manufacturer's predicted method name
//				// if you wish
//				e1.printStackTrace();
//			}
//		}
//
//		this.isSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
//		this.isSIM2Ready = false;
//
//		try
//		{
//
//			this.isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0);
//			this.isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1);
//		}
//		catch (GeminiMethodNotFoundException e)
//		{
//
//			e.printStackTrace();
//
//			try
//			{
//				this.isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0);
//				this.isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1);
//			}
//			catch (GeminiMethodNotFoundException e1)
//			{
//				e1.printStackTrace();
//			}
//		}
    }

    private String getDeviceIdBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException {

        String imei = null;

        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try {

            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);

            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            Object ob_phone = getSimID.invoke(telephony, obParameter);

            if (ob_phone != null) {
                imei = ob_phone.toString();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            throw new GeminiMethodNotFoundException(predictedMethodName);
        }

        return imei;
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

//	private boolean getSIMStateBySlot(Context context, String predictedMethodName, int slotID) throws GeminiMethodNotFoundException
//	{
//
//		boolean isReady = false;
//
//		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//		try
//		{
//
//			Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
//
//			Class<?>[] parameter = new Class[1];
//			parameter[0] = int.class;
//			Method getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter);
//
//			Object[] obParameter = new Object[1];
//			obParameter[0] = slotID;
//			Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);
//
//			if ( ob_phone != null )
//			{
//				int simState = Integer.parseInt(ob_phone.toString());
//				if ( simState == TelephonyManager.SIM_STATE_READY )
//				{
//					isReady = true;
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw new GeminiMethodNotFoundException(predictedMethodName);
//		}
//
//		return isReady;
//	}

    private class GeminiMethodNotFoundException extends Exception {

        private static final long serialVersionUID = -996812356902545308L;

        public GeminiMethodNotFoundException(String info) {
            super(info);
        }
    }
}
