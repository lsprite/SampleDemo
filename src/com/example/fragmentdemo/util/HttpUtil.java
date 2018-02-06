package com.example.fragmentdemo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	public static String posturl(ArrayList<NameValuePair> nameValuePairs, String url, String encode) {
		String tmp = "";
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			String logString = url + "?";
			for (int i = 0; i < nameValuePairs.size(); i++) {
				NameValuePair nameValuePair = nameValuePairs.get(i);
				if (i == (nameValuePairs.size() - 1)) {
					logString += nameValuePair.getName() + "=" + nameValuePair.getValue();
				} else {
					logString += nameValuePair.getName() + "=" + nameValuePair.getValue() + "&";
				}
				LogUtil.e(nameValuePair.getName() + "=" + nameValuePair.getValue());
			}
			LogUtil.e("HttpManager", "HttpManager Post:" + logString);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			tmp = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		LogUtil.e(tmp);
		return tmp;
	}

	public static String posturl(String str, String url, String encode) {
		String tmp = "";
		InputStream is = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Content-type", "application/xml");
			httppost.setEntity(new StringEntity(str, "utf-8"));
			String logString = url + "?";
			LogUtil.e("HttpManager", "HttpManager Post:" + logString);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, encode));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			tmp = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		LogUtil.e(tmp);
		return tmp;
	}

}
