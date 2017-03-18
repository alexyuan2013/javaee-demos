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
	public static final int QUERY_INTERNAL_PARAMS = 0x0335;//终端单灯内部参数应答消息
	
	
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
			LampAlarmObject alarm = new LampAlarmObject(lco.getBody());
			alarm.toDatabase();
			result = replyAlarm(lco);
			break;
		case QUERY_STATE_ID:
			LampStateObject state = new LampStateObject(lco.getBody());
			state.toDatabase();
			result = replyCommon(lco);
			break;
		case QUERY_INTERNAL_PARAMS:
			//LampStateObject state = new LampStateObject(lco.getBody());
			//state.toDatabase();
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
		System.out.println(receiver.receiver("aD0AaAg1AAACBAACIBIUAAQAAQogAAAVBzAQIEaSBQAAMQAAAAAAAAIBZOIAAAAAZAAAAAACZOIAAAAAMgAAAAB7phY="));
	}

}
