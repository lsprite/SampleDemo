package com.example.fragmentdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fragmentdemo.sll.HttpManager;
import com.example.fragmentdemo.sll.HttpsUtil2;
import com.example.fragmentdemo.util.MosaicProcessor;
import com.example.fragmentdemo.view.AudioRecorderButton;
import com.example.fragmentdemo.view.AudioRecorderButton.StateChangeListener;
import com.example.fragmentdemo.view.WritePadDialog;
import com.example.fragmentdemo.view.WritePadDialog.DialogListener;

public class Fragment_A extends BaseFragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_a, container, false);
		initView();
		// SMS4.test();
		// getInfo();
		// System.out.println(PhoneUtil.getPsdnIp());
		// doSU();
		return view;
	}

	private void doSU() {
		try {
			Process process = Runtime.getRuntime().exec("su");// (这里执行是系统已经开放了root权限，而不是说通过执行这句来获得root权限)
			DataOutputStream os = new DataOutputStream(
					process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			// 如果已经root，但是用户选择拒绝授权,e.getMessage() = write failed: EPIPE (Broken
			// pipe)
			// 如果没有root，,e.getMessage()= Error running exec(). Command: [su]
			// Working Directory: null Environment: null
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		ImageView img = (ImageView) view.findViewById(R.id.img);
		// UILUtils.displayImage(
		// getActivity(),
		// "http://10.jpg",
		// img);
		try {
			img.setImageBitmap(MosaicProcessor.makeMosaic(null, null, 10));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//
		Button btn = (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), WebActivity.class);
				// intent.putExtra("url",
				// "file:///android_asset/html/honhbao.html");
				intent.putExtra(
						"url",
						"https://jeromeetienne.github.io/AR.js/three.js/examples/mobile-performance.html");
				startActivity(intent);
			}
		});
		Button btn1 = (Button) view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WritePadDialog writeTabletDialog = new WritePadDialog(
						getActivity(), new DialogListener() {
							@Override
							public void getSignImagePath(String path) {
								System.out.println("----签名文件:" + path);
							}
						});
				writeTabletDialog.show();
			}
		});
		Button btn3 = (Button) view.findViewById(R.id.btn3);
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CalendarActivity.class);
				startActivity(intent);
			}
		});
		AudioRecorderButton btn2 = (AudioRecorderButton) view
				.findViewById(R.id.btn2);
		btn2.setStateChangeListener(new StateChangeListener() {

			@Override
			public void start() {
				// TODO Auto-generated method stub
				System.out.println("---start");
			}

			@Override
			public void end() {
				// TODO Auto-generated method stub
				System.out.println("---end");
			}
		});
	}

	private void getInfo() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("++++0");
					HttpsURLConnection urlConnection = HttpsUtil2
							.getHttpsURLConnection(
									getActivity(),
									"https://222.76.241.234:13801/api/authentications?account=43d4ac3a271827081010f9c34588a912&password=a1c6616bb350ab3a20422ff1f7512173",
									"POST");
					// Setup connection
					urlConnection.connect();
					// Get content, contentType and encoding
					System.out.println("++++1");
					InputStream is = urlConnection.getInputStream();
					System.out.println("++++2");
					StringBuffer sb = new StringBuffer();
					String readLine;
					BufferedReader responseReader;
					// 处理响应流，必须与服务器响应流输出的编码一致
					responseReader = new BufferedReader(new InputStreamReader(
							is, "UTF-8"));
					while ((readLine = responseReader.readLine()) != null) {
						sb.append(readLine).append("\n");
					}
					responseReader.close();
					System.out.println("++++3");
					System.out.println(sb.toString());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		thread.start();
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("++++0");
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					String retPost = HttpManager
							.posturl(
									nameValuePairs,
									"https://222.76.241.234:13801/api/authentications?account=43d4ac3a271827081010f9c34588a912&password=a1c6616bb350ab3a20422ff1f7512173",
									"utf-8");
					System.out.println("++++1");
					System.out.println(retPost);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		thread2.start();
	}
}
