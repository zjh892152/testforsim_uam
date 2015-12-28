package com.example.jh.testforsim_uam.dualsim;

import android.app.PendingIntent;

public abstract class DualsimBase {
    // 抽象基类
    protected boolean isDoubleSim = false;
    protected String sim0Imsi;
    protected String sim1Imsi;
    protected int sim0State, sim1State;
    public static int currentapiVersion = android.os.Build.VERSION.SDK_INT;

    public boolean isDoubleSim() {
        return isDoubleSim;
    }

    public String getSim0Imsi() {
        return sim0Imsi;
    }

    public int getSim0State() {
        return sim0State;
    }

    public String getSim1Imsi() {
        return sim1Imsi;
    }

    public int getSim1State() {
        return sim1State;
    }

    public abstract int getSimState(int simID);

    public abstract String getImsi(int simID);

    public abstract boolean sendDataMessage(String destinationAddress, String scAddress, short destinationPort,
                                            byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent, int simId);


    //	// 0是卡1，,1是卡2以下类同
//	public abstract String getSimPhoneNumber(int paramInt); // 返回手机号码
//
//	public abstract int getDataState(int paramInt);// 返回数据状态
//
//	public abstract String getIMSI(int paramInt);// 返回手机标识
//
//	public abstract String getIMSI(int paramInt, Context paramContext);// 返回手机标识
//
//	public abstract int getPhoneState(int paramInt);// 返回手机状态
//
//	public abstract boolean isServiceAvaliable(int paramInt);// 服务是否可用
//
//	public abstract boolean isSimStateIsReady(int paramInt);// 卡是否在使用
//
//	public abstract int getSimOperator(int paramInt);// 服务商（电信、移动、联通）
//
//	protected abstract Object getITelephonyMSim(int paramInt);// 获取操作接口
//
//	protected abstract Object getMSimTelephonyManager(int paramInt);// 获取操作接口

}
