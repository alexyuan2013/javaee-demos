package com.nari.lamp.comm;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

import com.nari.lamp.service.BCD;
import com.nari.lamp.service.BodyEncodeDecode;

/**
 * 接收终端数据
 * @author alex
 *
 */
public class LampCommReceiver {
	public static final int COMMON_MSG_ID = 0x0000; //终端通用消息
	public static final int LOGIN_MSG_ID = 0x0100; //终端登录消息
	public static final int HEART_BEAT_ID = 0x0200;//终端心跳消息
	public static final int ALARM_MSG_ID = 0x0935;//终端报警消息
	public static final int QUERY_STATE_ID = 0x0835;//终端单灯状态应答消息
	
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
			byte[] bb = new byte[2];
			bb[0] = body[1];
			bb[1] = body[0];
			ByteBuffer wrapped = ByteBuffer.wrap(bb);
			this.msgNo = wrapped.getInt();
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
			this.thisTime = wrapped.getInt();
			bb = new byte[1];
			bb[0] = body[19];
			wrapped = ByteBuffer.wrap(bb);
			this.voltage = wrapped.getInt();
			bb = new byte[2];
			bb[0] = body[21];
			bb[1] = body[20];
			wrapped = ByteBuffer.wrap(bb);
			this.current = wrapped.getInt();
			bb = new byte[2];
			bb[0] = body[23];
			bb[1] = body[22];
			wrapped = ByteBuffer.wrap(bb);
			this.inWater = wrapped.getInt();
			bb = new byte[1];
			bb[0] = body[24];
			wrapped = ByteBuffer.wrap(bb);
			this.stateNum = wrapped.getInt();
			this.statePackages = new StatePackage[this.stateNum];
			for(int i=0; i<this.stateNum; i++){
				bb = new byte[1];
				bb[0] = body[24 + i*12 +1];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].lampNo = wrapped.getInt();
				bb[0] = body[24 + i*12 +2];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].switchRange = wrapped.getInt();
				bb[0] = body[24 + i*12 +3];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].voltage = wrapped.getInt();
				bb = new byte[2];
				bb[0] = body[24 + i*12 +5];
				bb[1] = body[24 + i*12 +4];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].current = wrapped.getInt();
				bb[0] = body[24 + i*12 +7];
				bb[1] = body[24 + i*12 +6];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].power = wrapped.getInt();
				bb = new byte[1];
				bb[0] = body[24 +i*12 + 8];
				wrapped = ByteBuffer.wrap(bb);
				this.statePackages[i].powerFactor = wrapped.getInt();
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
	/**
	 * 终端上报报警解析
	 * @author alex
	 *
	 */
	public class LampAlarm {
		/**
		 * 报警标志数组
		 */
		private ArrayList<Integer> alarmFlag;
		
		public LampAlarm(byte[] body){
			alarmFlag = new ArrayList<Integer>();
			byte[] dword = new byte[4];
			dword[0] = body[3];
			dword[1] = body[2];
			dword[2] = body[1];
			dword[3] = body[0];
			ByteBuffer wrapper = ByteBuffer.wrap(dword);
			int flag = wrapper.getInt();
			String flagStr = Integer.toBinaryString(flag);
			int length = flagStr.length();
			for(int i=length-1; i>=0; i--){
				if(flagStr.charAt(i) == '1'){
					alarmFlag.add(i);
				}
			}
		}
		
		public void toDatabase(){
			System.out.println("LampAlarm对象将写入数据库");
		}
	}
	
	
	/**
	 * 接受消息并应答
	 * @param src
	 */
	public String receiver(String src){
		String result="";
		LampCommObject lco = new LampCommObject(src);
		int msgID = BodyEncodeDecode.byte2Integer(lco.getHeader().getMsgId());
		System.out.println(msgID);
		switch(msgID){
		case COMMON_MSG_ID:
			result = replyCommon(lco);
			break;
		case LOGIN_MSG_ID:
			result = replyLogin(lco);
			break;
		case HEART_BEAT_ID:
			result = replyCommon(lco);
			break;
		case ALARM_MSG_ID:
			LampAlarm alarm = new LampAlarm(lco.getBody());
			alarm.toDatabase();
			result = replyAlarm(lco);
			break;
		case QUERY_STATE_ID:
			LampStateObject lso = new LampStateObject(lco.getBody());
			lso.toDatabase();
			result = replyCommon(lco);
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * 应答终端通用消息
	 * @param lco 终端通用消息
	 * @return 应答指令base64字符串
	 */
	private String replyCommon(LampCommObject lco){
		byte[] msgNo = lco.getHeader().getMsgNum();
		byte[] msgIdSrc = lco.getHeader().getMsgId();
		byte result = 0x00;
		return LampCommSender.replyCommon(msgNo, msgIdSrc, result);
	}
	
	/**
	 * 应答终端的登录消息
	 * @param lco 终端登录消息
	 * @return 应答指令base64字符串
	 */
	private String replyLogin(LampCommObject lco){
		byte[] msgNum = lco.getHeader().getMsgNum();
		byte result = 0x00;
		return LampCommSender.replyLogin(msgNum, result);
	}
	
	/**
	 * 应答终端的报警消息
	 * @param lco 终端报警消息
	 * @return 应答指令base64字符串
	 */
	private String replyAlarm(LampCommObject lco){
		byte[] msgNo = lco.getHeader().getMsgNum();
		byte[] msgIdSrc = lco.getHeader().getMsgId();
		byte result = 0x00;
		return LampCommSender.replyCommon(msgNo, msgIdSrc, result);
	}
	
	public static void main(String[] args) {
		LampCommReceiver receiver = new LampCommReceiver();
		System.out.println(receiver.receiver("aCMAaAEAAAACBAACIBIQADIAAAEyMSAgIDEwMTIxMEcgATVlVnciyoYW"));
	}

}
