package com.nari.lamp.comm;

import java.nio.ByteBuffer;
import java.util.ArrayList;


/**
 * 终端上报报警解析
 * @author alex
 *
 */
public class LampAlarmObject {
	/**
	 * 报警标志数组
	 */
	private ArrayList<Integer> alarmFlag;
	
	public LampAlarmObject(byte[] body){
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
	
	public void toDatabase(String deviceId){
		System.out.println(deviceId);
		System.out.println("LampAlarm对象将写入数据库");
	}
}

