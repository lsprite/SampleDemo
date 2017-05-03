package com.example.fragmentdemo.sll;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.fragmentdemo.MainApplication;
import com.example.fragmentdemo.util.SMS4;

/**
 * @COMPANY:sunnyTech
 * @CLASS:HttpManager
 * @DESCRIPTION:网络通讯
 * @AUTHOR:Sunny
 * @VERSION:v1.0
 * @DATE:2014-10-21 下午5:34:38
 */
public class HttpManager {

	public static String posturl(ArrayList<NameValuePair> nameValuePairs,
			String url, String encode) {
		String tmp = "";
		InputStream is = null;
		try {
			// HttpClient httpclient = new DefaultHttpClient();
			DefaultHttpClient httpclient = HttpsUtil
					.getSslHttpClient(MainApplication.getInstance()
							.getApplicationContext());
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			String logString = url + "?";
			for (int i = 0; i < nameValuePairs.size(); i++) {
				NameValuePair nameValuePair = nameValuePairs.get(i);
				if (i == (nameValuePairs.size() - 1)) {
					logString += nameValuePair.getName() + "="
							+ nameValuePair.getValue();
				} else {
					logString += nameValuePair.getName() + "="
							+ nameValuePair.getValue() + "&";
				}
			}
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encode));
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
		// LogUtil.e(tmp);
		// return tmp;
		System.out.println(tmp);
		System.out.println(SMS4.decodeSMS4toString(tmp.trim()));
		return SMS4.decodeSMS4toString(tmp.trim());

	}

	public static String geturl(String url, String encode) {
		String tmp = "";
		InputStream is = null;
		try {
			// HttpClient httpclient = new DefaultHttpClient();
			DefaultHttpClient httpclient = HttpsUtil
					.getSslHttpClient(MainApplication.getInstance()
							.getApplicationContext());
			// HttpPost httppost = new HttpPost(url);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			return "error";
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, encode));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			tmp = sb.toString();
		} catch (Exception e) {
			return "error";
		}
		// LogUtil.e(tmp);
		// return tmp;
		return SMS4.decodeSMS4toString(tmp.trim());

	}

	/**
	 * @param url
	 *            servlet的地址
	 * @param params
	 *            要传递的参数
	 * @param files
	 *            要上传的文件
	 * @return true if upload success else false
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	// public static String uploadFiles(Context context, String url,
	// Map<String, String> params, Map<String, File> filesMap,
	// String encode) {
	// String tmp = "";
	// InputStream is = null;
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// httpClient.getParams().setParameter(
	// CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	// HttpPost httpRequest = new HttpPost(url);
	// MultipartEntity mEntity = new MultipartEntity();
	// // 添加上传参数
	// for (Map.Entry<String, String> entry : params.entrySet()) {
	// ContentBody strBody = new StringBody(entry.getValue());
	// mEntity.addPart(entry.getKey(), strBody);
	// LogUtil.e(entry.getKey() + "=" + entry.getValue());
	// }
	// // 添加上传的文件
	// Iterator<String> iterator = filesMap.keySet().iterator();
	// while (iterator.hasNext()) {
	// String fileName = iterator.next();
	// ContentBody cBody = new FileBody(filesMap.get(fileName));
	// mEntity.addPart(fileName, cBody);
	// LogUtil.e("while-----iterator fileName:" + fileName + "  file:"
	// + filesMap.get(fileName));
	// }
	// httpRequest.setEntity(mEntity);
	// HttpResponse httpResponse = httpClient.execute(httpRequest);
	// HttpEntity resEntity = httpResponse.getEntity();
	// is = resEntity.getContent();
	// } catch (Exception e) {
	// return "error";
	// }
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// is, encode));
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// is.close();
	// tmp = sb.toString();
	// } catch (Exception e) {
	// return "error";
	// }
	// LogUtil.e(tmp);
	// return tmp;
	// }
}
