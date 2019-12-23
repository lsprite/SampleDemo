package com.example.fragmentdemo.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.os.Build;

public class PhoneUtil {
	public static String getPsdnIp() {
		System.out.println("++++getPsdnIp");
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return "{\"result\":\"true\",\"ipaddress\":\"" + inetAddress.getHostAddress().toString()
								+ "\"}";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"result\":\"false\"}";
	}

	public static String PseudoUniqueID() {
		try {
			return "35" + // we make this look like a valid IMEI
					Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
					+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
					+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
					+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
					+ Build.USER.length() % 10; // 13 digits

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}

	}
}
