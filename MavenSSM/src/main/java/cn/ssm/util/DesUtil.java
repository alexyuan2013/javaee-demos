package cn.ssm.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {
	private static final String Algorithm = "DES"; // 定义 加密算法,可用

	// DES,DESede,Blowfish
	// srcΪ�����ܵ����ݻ�������Դ
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyteΪ������Կ������Ϊ24�ֽ�
	// srcΪ���ܺ�Ļ�����
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// ������Կ
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// ����
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// ת����ʮ�������ַ���
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	// 16 ���� ת 2 ����
	public static byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	private static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	// ����
	public static String Encrypt(String str, byte[] key) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encrypt = encryptMode(key, str.getBytes());
		return byte2hex(encrypt);
	}

	// ����
	public static byte[] EncryptRetByte(byte[] src, byte[] key) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] encrypt = encryptMode(key, src);
		return encrypt;
	}

	// ����
	public static String Decrypt(String str, byte[] key) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		byte[] decrypt = decryptMode(key, hex2byte(str));
		return new String(decrypt);
	}

	public static void main(String[] args) {
		// String driverClass = "com.mysql.jdbc.Driver";
		// String url =
		// "jdbc:mysql://192.168.20.18:3306/tbkt?useUnicode=true&characterEncoding=UTF-8";
		// String userName = "tbkt";
		// String userPassword = "1qaz2wsx";
		String driverClass = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/newsdb?useUnicode=true&amp;characterEncoding=utf8";
		String userName = "root";
		String userPassword = "root";

		DesUtil des = new DesUtil();
		String key = "0002000200020002";

		String jdbcDriverClassName = Encrypt(driverClass, hex2byte(key));// 加密
		String jdbcUrl = Encrypt(url, hex2byte(key));// 加密
		String jdbcUsername = Encrypt(userName, hex2byte(key));// 加密
		String jdbcPassword = Encrypt(userPassword, hex2byte(key));// 加密
		System.out.println("jdbc.driverClassName:" + jdbcDriverClassName);
		System.out.println("jdbc.url:" + jdbcUrl);
		System.out.println("jdbc.username:" + jdbcUsername);
		System.out.println("jdbc.password:" + jdbcPassword);

	}
}
