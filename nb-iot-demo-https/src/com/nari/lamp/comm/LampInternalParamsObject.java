package com.nari.lamp.comm;

public class LampInternalParamsObject {
	
	private int msgNo;//对应单灯内部参数查询消息的流水号；应答包有，设置包没有
	private int paramsNum; //参数列表的个数
	private ParamObject versions; //版本信息
	private ParamObject address;//单灯地址
	private ParamObject lampPoleId;//灯杆编号
	private ParamObject lampLonLat;//经纬度信息
	private ParamObject lampNum;//所属灯杆的灯盏数
	private ParamObject lampOrder;//所属灯杆的灯盏输出顺序
	private ParamObject alarmMask;//报警屏蔽字
	private ParamObject keyAlarm;//关键标识
	private ParamObject leakageUserd;//漏电保护启用标志：0x00——启用，0x01~0xff禁用
	private ParamObject leakageLimit1;//漏电电流阈值
	private ParamObject leakageTime1;//漏电极限不驱动时间，单位ms
	private ParamObject leakageTime2;//漏电延时重合时间，单位ms
	private ParamObject lonLatSwitchUsed;//经纬度开关是否启用：0禁用，1启用
	private ParamObject leakageLimit2;//漏电电压阈值
	private ParamObject alarmInterval;//报警间隔分钟数
	private ParamObject alarmLimit1;//电容故障报警功率因数限值，单位0.01
	
	public class ParamObject {
		public int id;
		public int length;
		public byte[] content; 
	}

}
