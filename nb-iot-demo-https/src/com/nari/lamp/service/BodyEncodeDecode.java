package com.nari.lamp.service;

import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.nari.lamp.comm.LampCommObject;

public class BodyEncodeDecode {
	
	public static byte[] decoder(String src){
		byte[] result = Base64.decodeBase64(src);
		return result;
	}
	public static String encoder(byte[] src){
		return Base64.encodeBase64String(src);
	}
	/**
	 * 将byte数组转换成数值，用于比较
	 * @param _byte
	 * @return
	 */
	public static int byte2Integer(byte[] _byte){
		ByteBuffer bf = ByteBuffer.wrap(_byte);
		if(_byte.length == 2){
			return bf.getShort();
		}
		if(_byte.length == 4){
			return bf.getInt();
		}
		return 0;
	}
	
	public static void main(String[] arsg){
		LampCommObject bb = new LampCommObject("aCMAaAEAAAACBAACIBJJADIAAAEyMSAgIDEwMTIxMEcgATVlVnciNnUW");
		System.out.println(bb.toBase64String());
		Date now = new Date();
		System.out.println(now.getYear()-100);
		byte[] body = null;
		LampCommObject dd = new LampCommObject(bb.getHeader(), bb.getBody());
		System.out.println(dd.toBase64String());
		bb = new LampCommObject("dxFmIg==");
		byte[] coder = decoder("dxFmIg==");
		//System.out.println(decoder("dxFmIg=="));
		body = new byte[2];
		body[0] = 0x00;
		body[1] = 0x00;
		int b2Int = byte2Integer(body);
		int cc = 0x0000;
		byte[] fdfd = BCD.DecimalToBCD(0x00);
		System.out.println(fdfd);
		System.out.println(Integer.toBinaryString(0xFFFF));
		short ax, lsb;
		ax=(short)0xFFFF;
		ByteBuffer bf = ByteBuffer.allocate(2);
		bf.putShort(ax);
		byte[] ff = bf.array();
		System.out.println(Integer.toBinaryString(-15297));
		byte aa = (byte)0xff;
		System.out.println(Integer.toBinaryString(0xff));
	}

}
