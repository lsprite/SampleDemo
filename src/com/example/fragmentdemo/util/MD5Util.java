package com.example.fragmentdemo.util;

import java.security.MessageDigest;

public class MD5Util {
	/**
	 * MD5 加密
	 * 
	 * @param passwd
	 *            待加密字符串
	 * @return
	 */
	public static String encrypt(String passwd) {
		try {
			byte[] res1 = passwd.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5".toUpperCase());
			md.update(res1);
			byte[] hash = md.digest();
			StringBuffer d = new StringBuffer("");
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i] & 0xFF;
				if (v < 16)
					d.append("0");
				d.append(Integer.toString(v, 16) + "");
			}
			return d.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 *
	 * MD5加密
	 * 
	 * @param plainText
	 *            明文
	 *
	 * @return 32位密文
	 */

	public static String encryption(String plainText) {

		String re_md5 = new String();

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(plainText.getBytes("ISO-8859-1"));

			byte b[] = md.digest();

			// PrintConsoleLog.printlnConsoleLog("明文MD5字节数组："+Arrays.toString(b));
			re_md5 = byte2HexStr(b);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return re_md5;

	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param byte[]
	 *            b byte数组
	 * @return String 每个Byte值之间空格分隔(没有空格)
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			// sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}
}
