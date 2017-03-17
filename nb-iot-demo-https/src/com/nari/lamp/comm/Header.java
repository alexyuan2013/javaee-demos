package com.nari.lamp.comm;

import java.nio.ByteBuffer;

import com.nari.lamp.service.BCD;

public class Header{
	/**
	 * 消息id
	 */
	private byte[] msgId = new byte[2];
	private byte[] msgAttr = new byte[2];
	private byte[] deviceId = new byte[6];
	private byte[] msgNum = new byte[2];
	
	private static int msgNumAuto = 1;
	
	private int getMsgNumAuto(){
		if(msgNumAuto < 65535){
			msgNumAuto += 1;
		} else {
			msgNumAuto = 1;
		}
		return msgNumAuto;
	}
	
	public Header(byte[] header){
		if(header.length >= 12){
			this.msgId[0] = header[0];
			this.msgId[1] = header[1];
			this.msgAttr[0] = header[2];
			this.msgAttr[1] = header[3];
			this.deviceId[0] = header[4];
			this.deviceId[1] = header[5];
			this.deviceId[2] = header[6];
			this.deviceId[3] = header[7];
			this.deviceId[4] = header[8];
			this.deviceId[5] = header[9];
			this.msgNum[0] = header[10];
			this.msgNum[1] = header[11];
		}
	}
	
	public Header(){
		//--设置msgId
		ByteBuffer word = ByteBuffer.allocate(2);
		word.putShort((short)0x8000);
		//WORD——小端模式，先传低位，再传高位
		this.msgId[0] = word.array()[1];
		this.msgId[1] = word.array()[0];
		//--设置msgAttr
		word = ByteBuffer.allocate(2);
		word.putShort((short)0);
		//WORD——小端模式，先传低位，再传高位
		this.msgAttr[0] = word.array()[1];
		this.msgAttr[1] = word.array()[0];
		//--设置deviceId
		this.deviceId[0] = BCD.DecimalToBCD(2)[0];
		this.deviceId[1] = 0x04;
		this.deviceId[2] = 0x00;
		this.deviceId[3] = 0x02;
		this.deviceId[4] = 0x20;
		this.deviceId[5] = 0x12;
		//设置消息流水号
		word = ByteBuffer.allocate(2);
		word.putShort((short)getMsgNumAuto());
		//WORD——小端模式，先传低位，再传高位
		this.msgNum[0] = word.array()[1];
		this.msgNum[1] = word.array()[0];
	}
	
	

	public byte[] getMsgId() {
		return msgId;
	}

	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	public byte[] getMsgAttr() {
		return msgAttr;
	}

	public void setMsgAttr(byte[] msgAttr) {
		this.msgAttr = msgAttr;
	}

	public byte[] getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(byte[] deviceId) {
		this.deviceId = deviceId;
	}

	public byte[] getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(byte[] msgNum) {
		this.msgNum = msgNum;
	}

	
	public byte[] getAllHeader(){
		byte[] header = new byte[18];
		header[0] = this.msgId[0];
		header[1] = this.msgId[1];
		header[2] = this.msgAttr[0];
		header[3] = this.msgAttr[1];
		header[4] = this.deviceId[0];
		header[5] = this.deviceId[1];
		header[6] = this.deviceId[2];
		header[7] = this.deviceId[3];
		header[8] = this.deviceId[4];
		header[9] = this.deviceId[5];
		header[10] = this.msgNum[0];
		header[11] = this.msgNum[1];
		return header;
	}
	
	public short getParsedMsgId(){
		if(this.msgId == null){
			return -1;
		}
		ByteBuffer wrapped = ByteBuffer.wrap(this.msgId); // big-endian by default
		return wrapped.getShort(); // 1
	}
	
	
}
