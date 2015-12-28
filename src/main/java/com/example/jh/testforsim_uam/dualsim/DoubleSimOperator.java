package com.example.jh.testforsim_uam.dualsim;


import android.content.Context;
import android.os.Build;
import android.util.Log;

public final class DoubleSimOperator {
    public static DualsimBase getDoubleSim(Context mContext) {

        if (MTKDualsim.isMTKSystem())
            return new MTKDualsim(mContext);
        if (SamsungDualsim.isSamsungSystem())
            return new SamsungDualsim(mContext);
        if (QualcommDualsim.isQualcommSystem(mContext))
            return new QualcommDualsim(mContext);

        return null;
//		if ( SsoConstants.PHONE_HUAWEI.equalsIgnoreCase(android.os.Build.MANUFACTURER) )
//		{
//			return new HuaWeiDualsim(mContext);
//		}
//		else if ( SsoConstants.PHONE_SANXING.equalsIgnoreCase(android.os.Build.MANUFACTURER) )
//		{
//			return new SamsungDualsim(mContext);
//		}
//		else
//		{
//			DualsimBase ret = new QualcommDualsim(mContext);
//
//			if(ret.isDoubleSim())
//				return ret;
//			else
//				return new NormalDoulsimCheck(mContext);
//		}

    }

//	public static boolean sendDoubleDataSms(Context context, int type, String manufacturer, byte[] content)
//	{
//		try
//		{
//			Class<?> smsManagerClass = null;
//			Method sendDataMessage = null;
//
//			// 华为手机发送数据短信
//			if ( SsoConstants.PHONE_HUAWEI.equalsIgnoreCase(manufacturer) )
//			{
//				smsManagerClass = null;
//				Class<?>[] sendDataMessagePamas = { String.class, String.class, Short.TYPE, byte[].class, PendingIntent.class, PendingIntent.class, Integer.TYPE };
//				sendDataMessage = null;
//				smsManagerClass = Class.forName("android.telephony.MSimSmsManager");
//				Method method = smsManagerClass.getMethod("getDefault", new Class[] {});
//				Object smsManager = method.invoke(smsManagerClass, new Object[] {});
//				sendDataMessage = smsManagerClass.getMethod("sendDataMessage", sendDataMessagePamas);
//				// PendingIntent sentIntentEvent =
//				// PendingIntent.getBroadcast(mContext, 0, sentIntent, 0);
//				// Intent deliveryIntent = new
//				// Intent(AuthnConstants.ACTION_DELIVERY);
//				// PendingIntent deliveryIntentEvent =
//				// PendingIntent.getBroadcast(mContext, 0, deliveryIntent, 0);
//				sendDataMessage.invoke(smsManager, HostConfig.getDataSmsDest(), null, HostConfig.getDataSmsPort(), content, null, null, type == SsoConstants.SELECT_SIM_2 ? 1 : 0);
//
//				return true;
//			}
//			// 三星手机发送数据短信接口判断卡1卡2，暂时定死1.现在接口参数为short类型
//			else if ( SsoConstants.PHONE_SANXING.equalsIgnoreCase(manufacturer) )
//			{
//				Class<?>[] sendDataMessagePamas = { String.class, String.class, Short.TYPE, Short.TYPE, byte[].class, PendingIntent.class, PendingIntent.class };
//				smsManagerClass = Class.forName("android.telephony.SmsManager");
//				Method method = smsManagerClass.getMethod("getDefault", new Class[] {});
//				Object smsManager = method.invoke(smsManagerClass, new Object[] {});
//				sendDataMessage = smsManagerClass.getMethod("sendDataMessage", sendDataMessagePamas);
//				// PendingIntent sentIntentEvent =
//				// PendingIntent.getBroadcast(mContext, 0, sentIntent, 0);
//				// Intent deliveryIntent = new
//				// Intent(AuthnConstants.ACTION_DELIVERY);
//				// PendingIntent deliveryIntentEvent =
//				// PendingIntent.getBroadcast(mContext, 0, deliveryIntent, 0);
//				sendDataMessage.invoke(smsManager, HostConfig.getDataSmsDest(), null, HostConfig.getDataSmsPort(), type == SsoConstants.SELECT_SIM_2 ? 1 : 0, content, null, null);
//
//				return true;
//			}
//			// 高通双卡解决方案发送数据短信接口判断卡1卡2，现在接口参数为short类型
//			else if(SsoConstants.PHONE_QUALCOMM.equalsIgnoreCase(manufacturer) ) {
//				return new QualcommDualsim(context).sendDataMsg(type, HostConfig.getDataSmsDest(), null, HostConfig.getDataSmsPort(), (short)0, content, null, null);
//			}
//
//			return false;
//
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			return false;
//		}
//	}

    // public String getSimPhoneNumber(int paramInt)
    // {
    // // TODO Auto-generated method stub
    // return null;
    // }
    //
    // public int getDataState(int paramInt)
    // {
    // // TODO Auto-generated method stub
    // return 0;
    // }
    //
    // public static String getIMSI(int paramInt, Context mContext)
    // {
    // if ( TextUtils.isEmpty(android.os.Build.MANUFACTURER) )
    // {
    // return paramInt == 1 ?
    // NormalDoulsimCheck.getInstance(mContext).getImeiSIM2() :
    // NormalDoulsimCheck.getInstance(mContext).getImeiSIM1();
    // }
    // else if ( SsoConstants.PHONE_HUAWEI.equals(android.os.Build.MANUFACTURER)
    // )
    // {
    // return new HuaWeiDualsim(mContext).getIMSI(paramInt);
    // }
    // else if (
    // SsoConstants.PHONE_SANXING.equals(android.os.Build.MANUFACTURER) )
    // {
    // return new SamsungDualsim(mContext).getIMSI(paramInt);
    // }
    // else
    // {
    // return paramInt == 1 ?
    // NormalDoulsimCheck.getInstance(mContext).getImeiSIM2() :
    // NormalDoulsimCheck.getInstance(mContext).getImeiSIM1();
    // }
    // }

}
