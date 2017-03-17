package com.nari.lamp.comm;

import java.nio.ByteBuffer;
import com.nari.lamp.service.BCD;
import com.nari.lamp.service.BodyEncodeDecode;

/**
 * 应答终端或向终端发送指令
 * @author alex
 *
 */
public class LampCommSender {
	/**
	 * 通用应答
	 * @param msgNo 对应的终端消息的流水号
	 * @param msgIdSrc 对应的终端消息的ID
	 * @param result 结果：0——成功/确认，1——失败，2——消息有误，3——不支持
	 * @return 应答指令的base64编码
	 */
	public static String replyCommon(byte[] msgNo, byte[] msgIdSrc, byte result){
		byte[] msgBody = new byte[5];
		msgBody[0] = msgNo[0];
		msgBody[1] = msgNo[1];
		msgBody[2] = msgIdSrc[0];
		msgBody[3] = msgIdSrc[1];
		msgBody[4] = result;
		Header header = new Header();
		//设置消息id：0x8000
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0x8000);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	
	/**
	 * 应答终端登录
	 * @param msgNo 消息流水号，从终端登录消息中获取
	 * @param result 登录结果：0——成功，1——已登录， 2——数据库中无该终端，3——消息有误
	 * @return 应答指令的base64编码
	 */
	public static String replyLogin(byte[] msgNo, byte result){
		byte[] msgBody = new byte[3];
		msgBody[0] = msgNo[0];
		msgBody[1] = msgNo[1];
		msgBody[2] = result;
		Header header = new Header();
		//设置消息id：0x8001
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0x8001);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header,msgBody);
		return obj.toBase64String();
	}
	/**
	 * 单灯遥控操作
	 * @param lampNum 控制灯盏数，这里只控制一盏灯
	 * @param lampState 开关及节能百分比：0x00——关，0x01~0x64节能百分比
	 * @return
	 */
	public static String remoteSwitch(byte lampState){
		byte[] msgBody = new byte[3];
		msgBody[0] = 0x01;
		msgBody[1] = 0x01;//灯序号
		msgBody[2] = lampState;//节能百分比或关
		Header header = new Header();
		//设置消息id：0xB110
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0xB110);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	/**
	 * 查询单灯状态
	 * @return
	 */
	public static String queryLampState(){
		byte[] msgBody = null;
		Header header = new Header();
		//设置消息id：0xB508
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0xB508);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	/**
	 * 设置经纬度开关时间
	 * @return
	 */
	public static String setLonLatTime(){
		return "";
	}
	/**
	 * 设置临时开关灯策略
	 * @param day 有效天数
	 * @param openHour 开始时间：时
	 * @param openMinute 开灯时间：分
	 * @param closeHour 关灯时间：时
	 * @param closeMinute 关灯时间：分
	 * @return
	 */
	public static String setTempSwitchStrategy(byte day, int openHour, int openMinute, int closeHour, int closeMinute){
		byte[] msgBody = new byte[5];
		msgBody[0] = day;
		msgBody[1] = BCD.DecimalToBCD(openHour)[0];
		msgBody[2] = BCD.DecimalToBCD(openMinute)[0];
		msgBody[3] = BCD.DecimalToBCD(closeHour)[0];
		msgBody[4] = BCD.DecimalToBCD(closeMinute)[0];
		Header header = new Header();
		//设置消息id：0x8001
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0xB101);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	/**
	 * 设置单灯亮度
	 * @param used 是否启用：0x00——不启用，0x01——启用
	 * @param h1 第一阶段时间：时
	 * @param m1 第一阶段时间：分
	 * @param b1 第一阶段亮度：0x00~0x64
	 * @param h2
	 * @param m2
	 * @param b2
	 * @param h3
	 * @param m3
	 * @param b3
	 * @param h4
	 * @param m4
	 * @param b4
	 * @param h5
	 * @param m5
	 * @param b5
	 * @return
	 */
	public static String setBrightness(byte used, 
			int h1, int m1, byte b1,
			int h2, int m2, byte b2,
			int h3, int m3, byte b3,
			int h4, int m4, byte b4,
			int h5, int m5, byte b5){
		byte[] msgBody = new byte[31];
		msgBody[0] = used;
		msgBody[1] = BCD.DecimalToBCD(h1)[0];
		msgBody[2] = BCD.DecimalToBCD(m1)[0];
		msgBody[3] = b1;
		msgBody[4] = b1;
		msgBody[5] = b1;
		msgBody[6] = b1;
		msgBody[7] = BCD.DecimalToBCD(h2)[0];
		msgBody[8] = BCD.DecimalToBCD(m2)[0];
		msgBody[9] = b2;
		msgBody[10] = b2;
		msgBody[11] = b2;
		msgBody[12] = b2;
		msgBody[13] = BCD.DecimalToBCD(h3)[0];
		msgBody[14] = BCD.DecimalToBCD(m3)[0];
		msgBody[15] = b3;
		msgBody[16] = b3;
		msgBody[17] = b3;
		msgBody[18] = b3;
		msgBody[19] = BCD.DecimalToBCD(h4)[0];
		msgBody[20] = BCD.DecimalToBCD(m4)[0];
		msgBody[21] = b4;
		msgBody[22] = b4;
		msgBody[23] = b4;
		msgBody[24] = b4;
		msgBody[25] = BCD.DecimalToBCD(h5)[0];
		msgBody[26] = BCD.DecimalToBCD(m5)[0];
		msgBody[27] = b5;
		msgBody[28] = b5;
		msgBody[29] = b5;
		msgBody[30] = b5;
		Header header = new Header();
		//设置消息id：0xB104
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0xB104);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	/**
	 * 设置单灯手动/自动模式
	 * @param mode 控制模式：0x00——自动，0x01——手动
	 * @return
	 */
	public static String setControlMode(byte mode){
		byte[] msgBody = new byte[1];
		msgBody[0] = mode;
		Header header = new Header();
		//设置消息id：0xB106
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0xB106);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	/**
	 * 设置经纬度开关时间
	 * @param days
	 * @return
	 */
	public static String setLonLatSwitchTime(int startMonth, int startDay, int days){
		byte[] msgBody = new byte[4 + days*4];
		msgBody[0] = BCD.DecimalToBCD(startMonth)[0];
		msgBody[1] = BCD.DecimalToBCD(startDay)[0];
		ByteBuffer word = ByteBuffer.allocate(2);
		word.putShort((short)days);
		//WORD——小端模式，先传低位，再传高位
		msgBody[2] = word.array()[1];
		msgBody[3] = word.array()[0];
		for(int i=0; i<days; i++){
			ByteBuffer dword = ByteBuffer.allocate(4);
			dword.putInt(0x18290530);
			//WORD——小端模式，先传低位，再传高位
			msgBody[4 + 4*i + 0] = dword.array()[3];
			msgBody[4 + 4*i + 1] = dword.array()[2];
			msgBody[4 + 4*i + 2] = dword.array()[1];
			msgBody[4 + 4*i + 3] = dword.array()[0];
		}
		Header header = new Header();
		//设置消息id：0x8200
		byte[] msgId = new byte[2];
		ByteBuffer bf = ByteBuffer.allocate(2);//长度为2byte
		bf.putShort((short)0x8200);
		//WORD——小端模式，先传低位，再传高位
		msgId[0] = bf.array()[1];
		msgId[1] = bf.array()[0];
		header.setMsgId(msgId);
		LampCommObject obj = new LampCommObject(header, msgBody);
		return obj.toBase64String();
	}
	
	
	public static void main(String[] args) {
		LampCommObject bb = new LampCommObject("aCMAaAEAAAACBAACIBINADIAAAEyMSAgIDEwMTIxMEcgATVlVnciNYUW");
		byte[] msgNo = bb.getHeader().getMsgNum();
		byte result = 0x00;
		System.out.println(replyLogin(msgNo, result));
		System.out.println(remoteSwitch((byte)0x00));
		System.out.println(queryLampState());
		byte day = 0x01;
		System.out.println(setTempSwitchStrategy(day, 18, 10, 4, 30));
		byte used = 0x01,
			   b1 = 0x64,
			   b2 = 0x64,
			   b3 = 0x64,
		       b4 = 0x64,
		       b5 = 0x64;
		System.out.println(setBrightness(used, 18, 20, b1, 20, 10, b2, 22, 10, b3, 1, 10, b4, 4, 10, b5));
		byte mode = 0x01;
		System.out.println(setControlMode(mode));
		System.out.println(setLonLatSwitchTime(1,1,30));
		
	}

}
