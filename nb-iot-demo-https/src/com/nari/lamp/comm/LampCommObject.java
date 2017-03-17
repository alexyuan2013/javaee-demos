package com.nari.lamp.comm;

import java.nio.ByteBuffer;
import java.util.Date;

import com.nari.lamp.service.BCD;
import com.nari.lamp.service.BodyEncodeDecode;

public class LampCommObject {
	/**
	 * 起始字符——0x68
	 */
	private byte startChar;
	/**
	 * 结束字符——0x16
	 */
	private byte endChar;
	/**
	 * 长度——消息头+消息体
	 */
	private short length = 0;
	/**
	 * 检验
	 */
	private byte[] CRC = null;
	/**
	 * 消息头——12字节
	 */
	private Header header = null;
	/**
	 * 消息体
	 */
	private byte[] body = null;
	/**
	 * 构造函数——解析消息体
	 * @param src base64字符
	 */
	public LampCommObject(String src){
		byte[] arr = BodyEncodeDecode.decoder(src);
		if(arr.length > 19){//长度至少为19
			//解析起始字符
			this.startChar = arr[0];
			//解析长度
			byte[] bb = new byte[2];
			bb[0] = arr[2];
			bb[1] = arr[1];
			ByteBuffer wrapped = ByteBuffer.wrap(bb); // big-endian by default
			this.length = wrapped.getShort();
			//解析消息头
			byte[] hh = new byte[12];
			for(int i = 0; i < 12; i++){
				hh[i] = arr[4+i]; 
			}
			this.header = new Header(hh);
			//解析消息体
			this.body = new byte[this.length - 12];
			for(int i=0; i<this.body.length; i++){
				this.body[i] = arr[16+i];
			}
			//解析CRC
			this.CRC = new byte[2];
			this.CRC[0] = arr[arr.length-3];
			this.CRC[1] = arr[arr.length-2];
			//解析结束字符
			this.endChar = arr[arr.length-1];
		}
	}
	/**
	 * 构造函数——构造消息体
	 * @param src base64字符
	 */
	public LampCommObject(Header header, byte[] body){
		//设置起始字符
		this.startChar = 0x68;
		//设置长度
		this.length = (body==null) ? 12: (short)(body.length + 12);
		//设置消息头
		this.header = header;
		//设置body
		this.body = body;
		//设置CRC
//		this.CRC = new byte[2];
//		this.CRC[0] = 0x00;
//		this.CRC[1] = 0x00;
		genCRC();
		//设置结束字符
		this.endChar = 0x16;
	
	}
	/**
	 * CRC算法
	 */
	public void genCRC(){
		int ax, lsb;
		ax=0xFFFF; 
		for(int i=0; i<this.length; i++){
			int data;
			if(i<12){
				data = this.getHeader().getAllHeader()[i]&0xff;
			} else {
				data = this.getBody()[i-12]&0xff;
			}
			ax ^= data;
			System.out.println(ax);
			for(int j=0; j<8; j++){
				lsb = ax&0x0001;
				ax = ax>>1;
				if(lsb!=0){
					ax ^=0xA001;
				}
			}
		}
		ByteBuffer bf = ByteBuffer.allocate(4);		 
		bf.putInt(ax);
		//bf.putChar((char)((ax & 0xFF) << 8 | 0xFF & (ax >> 8)));
		this.CRC = new byte[2];
		this.CRC[0] = bf.array()[3];
		this.CRC[1] = bf.array()[2];
	}
	
	
	public byte getStartChar() {
		return startChar;
	}

	public byte getEndChar() {
		return endChar;
	}

	public short getLength() {
		return length;
	}

	public byte[] getCRC() {
		return CRC;
	}

	public Header getHeader() {
		return header;
	}

	public byte[] getBody() {
		return body;
	}
	
	public void setStartChar(byte startChar) {
		this.startChar = startChar;
	}

	public void setEndChar(byte endChar) {
		this.endChar = endChar;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public void setCRC(byte[] cRC) {
		CRC = cRC;
	}

	public void setHeader(Header header) {
		this.header = header;
		genCRC();
	}

	public void setBody(byte[] body) {
		this.body = body;
		genCRC();
	}
	
	
	public String toBase64String(){
		byte[] arr = new byte[this.length + 7];
		//起始字符
		arr[0] = 0x68;
		//设置长度
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort(this.length);
		//WORD——小端模式，先传低位，再传高位
		arr[1] = bf.array()[1];
		arr[2] = bf.array()[0];
		//起始字符
		arr[3] = 0x68;
		
		//消息头
		for(int i=0; i<12; i++){
			arr[4+i] = this.header.getAllHeader()[i];
		}
		//消息体
		for(int i=0; this.body != null && i<this.body.length; i++){
			arr[16+i] = this.body[i];
		}
		//CRC
		arr[arr.length-3] = this.CRC[0]; 
		arr[arr.length-2] = this.CRC[1];
		//结束字符
		arr[arr.length-1] = 0x16;
		return BodyEncodeDecode.encoder(arr);
	}

	

}
