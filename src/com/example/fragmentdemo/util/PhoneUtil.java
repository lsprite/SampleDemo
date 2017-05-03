package com.example.fragmentdemo.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class PhoneUtil {
	public static String getPsdnIp() {
		System.out.println("++++getPsdnIp");
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return "{\"result\":\"true\",\"ipaddress\":\""
								+ inetAddress.getHostAddress().toString()
								+ "\"}";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"result\":\"false\"}";
	}
}
