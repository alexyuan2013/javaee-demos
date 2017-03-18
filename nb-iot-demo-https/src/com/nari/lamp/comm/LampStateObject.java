package com.nari.lamp.comm;

import java.nio.ByteBuffer;

import com.nari.lamp.service.BCD;

//单灯状态应答包
public class LampStateObject {
	public int msgNo;//应答流水号
	public int controlMode;//控制模式
	public int alarmFlag;//报警标志
	public DateTime dateTime;//设备时间
	public long sumTime;//设备累计运行时间，单位分钟
	public int thisTime;//本次开灯运行时间，单位分钟
	public int voltage;//漏电电压
	public int current;//漏电电流
	public int inWater;//水浸
	public int stateNum;//应答状态包个数（1-4）
	public StatePackage[] statePackages;//应答状态包
	public class StatePackage{
		public int lampNo;//灯序号（1-4）
		public int switchRange;//开关档位:0x00——关，0x01~0x64节能百分比
		public int voltage;//电压
		public int current;//电流
		public int power;//功率
		public int powerFactor;//功率因数
		public int reserved;//预留
	}
	public class DateTime {
		public int year;
		public int month;
		public int day;
		public int hour;
		public int minute;
		public int second;
	}
	
	public LampStateObject(byte[] body){
		this.dateTime = new DateTime();
		byte[] bb = new byte[2];
		bb[0] = body[1];
		bb[1] = body[0];
		ByteBuffer wrapped = ByteBuffer.wrap(bb);
		this.msgNo = wrapped.getShort();
		this.controlMode = body[2];
		bb = new byte[4];
		bb[0] = body[6];
		bb[1] = body[5];
		bb[2] = body[4];
		bb[3] = body[3];
		wrapped = ByteBuffer.wrap(bb);
		this.alarmFlag = wrapped.getInt();
		bb = new byte[1];
		bb[0] = body[7];
		this.dateTime.year = (int)BCD.BCDToDecimal(bb);
		bb[0] = body[8];
		this.dateTime.month = (int)BCD.BCDToDecimal(bb);
		bb[0] = body[9];
		this.dateTime.day = (int)BCD.BCDToDecimal(bb);
		bb[0] = body[10];
		this.dateTime.hour = (int)BCD.BCDToDecimal(bb);
		bb[0] = body[11];
		this.dateTime.minute = (int)BCD.BCDToDecimal(bb);
		bb[0] = body[12];
		this.dateTime.second = (int)BCD.BCDToDecimal(bb);
		bb = new byte[4];
		bb[0] = body[16];
		bb[1] = body[15];
		bb[2] = body[14];
		bb[3] = body[13];
		wrapped = ByteBuffer.wrap(bb);
		this.sumTime = wrapped.getInt();
		bb = new byte[2];
		bb[0] = body[18];
		bb[1] = body[17];
		wrapped = ByteBuffer.wrap(bb);
		this.thisTime = wrapped.getShort();
		bb = new byte[1];
		bb[0] = body[19];
		wrapped = ByteBuffer.wrap(bb);
		this.voltage = wrapped.get();
		bb = new byte[2];
		bb[0] = body[21];
		bb[1] = body[20];
		wrapped = ByteBuffer.wrap(bb);
		this.current = wrapped.getShort();
		bb = new byte[2];
		bb[0] = body[23];
		bb[1] = body[22];
		wrapped = ByteBuffer.wrap(bb);
		this.inWater = wrapped.getShort();
		bb = new byte[1];
		bb[0] = body[24];
		wrapped = ByteBuffer.wrap(bb);
		this.stateNum = wrapped.get();
		this.statePackages = new StatePackage[this.stateNum];
		for(int i=0; i<this.stateNum; i++){
			bb = new byte[1];
			this.statePackages[i] = new StatePackage();
			bb[0] = body[24 + i*12 +1];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].lampNo = wrapped.get();
			bb[0] = body[24 + i*12 +2];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].switchRange = wrapped.get();
			bb[0] = body[24 + i*12 +3];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].voltage = wrapped.get();
			bb = new byte[2];
			bb[0] = body[24 + i*12 +5];
			bb[1] = body[24 + i*12 +4];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].current = wrapped.getShort();
			bb[0] = body[24 + i*12 +7];
			bb[1] = body[24 + i*12 +6];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].power = wrapped.getShort();
			bb = new byte[1];
			bb[0] = body[24 +i*12 + 8];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].powerFactor = wrapped.get();
			bb = new byte[4];
			bb[0] = body[24 +i*12 + 9];
			bb[1] = body[24 +i*12 + 10];
			bb[2] = body[24 +i*12 + 11];
			bb[3] = body[24 +i*12 + 12];
			wrapped = ByteBuffer.wrap(bb);
			this.statePackages[i].reserved = wrapped.getInt();
		}
		
	}
	
	public void toDatabase(){
		System.out.println("LampStateObject对象将写入数据库");
	}
}