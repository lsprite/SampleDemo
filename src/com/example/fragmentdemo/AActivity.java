package com.example.fragmentdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;

import com.example.fragmentdemo.sll.HttpManager;
import com.example.fragmentdemo.sll.HttpsUtil2;
import com.example.fragmentdemo.util.UILUtils;
import com.example.fragmentdemo.view.AudioRecorderButton;
import com.example.fragmentdemo.view.AudioRecorderButton.StateChangeListener;
import com.example.fragmentdemo.view.WritePadDialog;
import com.example.fragmentdemo.view.WritePadDialog.DialogListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_a);
		initView();
	}

	private void doSU() {
		try {
			Process process = Runtime.getRuntime().exec("su");// (这里执行是系统已经开放了root权限，而不是说通过执行这句来获得root权限)
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
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

	ImageView img;
	LinearLayout lll;

	private void initView() {
		// TODO Auto-generated method stub
		lll = (LinearLayout) findViewById(R.id.lll);
		//
		img = (ImageView) findViewById(R.id.img);
		UILUtils.displayImage(AActivity.this,
				"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", img);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 数字后面必须全部加f，否则报错
				AnimationSet set = new AnimationSet(true);
				ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f, Animation.RELATIVE_TO_SELF,
						0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				// scaleAnimation.setStartOffset(500);
				scaleAnimation.setDuration(1000);
				set.addAnimation(scaleAnimation);
				//
				// TranslateAnimation translateAnimation = new
				// TranslateAnimation(0.0f, 300.0f, 0.0f, 300.0f);
				// translateAnimation.setDuration(1000);
				// set.addAnimation(translateAnimation);
				//
				lll.startAnimation(set);
			}
		});
		// try {
		// img.setImageBitmap(MosaicProcessor.makeMosaic(null, null, 10));
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		//
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AActivity.this, WebActivity.class);
				// intent.putExtra("url",
				// "file:///android_asset/html/honhbao.html");
				intent.putExtra("url",
						"https://jeromeetienne.github.io/AR.js/three.js/examples/mobile-performance.html");
				startActivity(intent);
			}
		});
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WritePadDialog writeTabletDialog = new WritePadDialog(AActivity.this, new DialogListener() {
					@Override
					public void getSignImagePath(String path) {
						System.out.println("----签名文件:" + path);
					}
				});
				writeTabletDialog.show();
			}
		});
		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(getActivity(),
				// CalendarActivity.class);
				// startActivity(intent);
				//
				// Uri packageURI = Uri.parse("package:" + "qzmcc.itc.moa");
				// // 创建Intent意图
				// Intent intent = new Intent(Intent.ACTION_DELETE);
				// intent.setData(packageURI);
				// // 执行卸载程序
				// startActivity(intent);
				// Intent intent = new Intent(
				// "com.ctrlosft.qzmcc_netassiant.LoginAuthorize");
				// startActivity(intent);
				// Intent intent = new Intent();
				// intent.setClassName("com.ctrlsoft.xm_pwjkxj",
				// "com.ctrlsoft.xm_pwjkxj.FirstActivity");
				// intent.putExtra("param",
				// "{\"result\":true,\"code\":20000,\"content\":\"2051301949b1c94ddf86ef1a802c8d91647b959f2522a776422bca44958b1df963f6769c0fc2033f40920125af43732b56677e2404ebe50f150d0960638846b3938ad87c9f0b71358f0f904c152b0b25673d1460a5dd2e54baa3aa8bb382dfcd394699a3f710eeb303fc63aeedefc953a6dc37f84f5b3f0b958476c8897e2a0739b9651b7a83a7e72a9ebfd65df89c4a80009162de9fdb1edbe456519a4805c0198ef3c1b844beaa9e52d90e130f08c004010914856d70802ff9e7cb0e16d39f5ab0b3f4ae5a90e9fbe3999a9a9485114edbffa6342e4d6f4d1c0a10531d5fc1e87fabc8d68854a27b52842ed09d08ea017d337902adb1fc2e3b02d127dff6d7ebd33cac5cc1ff04148c5f4d0066948969ed3d892ac19f49de3fedb7a23e25f5\",\"apps\":{\"privilege\":[{\"PACKAGE_NAME\":\"com.cnksi.sjjc\",\"APP_NAME\":\"变电运维\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.cld.nv.electric\",\"APP_NAME\":\"电力导航\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol\",\"APP_NAME\":\"输电巡检\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol.gjcj\",\"APP_NAME\":\"路线采集\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.ctrlsoft.xm_pwjkxj\",\"APP_NAME\":\"配电巡检\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.yjzy.im\",\"APP_NAME\":\"即时通信\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"}],\"myapps\":[{\"PACKAGE_NAME\":\"com.epgis.yjydzy.im\"},{\"PACKAGE_NAME\":\"com.cld.nv.electric\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol.gjcj\"},{\"PACKAGE_NAME\":\"com.cnksi.sjjc\"},{\"PACKAGE_NAME\":\"com.ctrlsoft.xm_pwjkxj\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol\"}]}}");
				// startActivity(intent);
				// FlashDialog flashDialog = new FlashDialog(getActivity());
				// flashDialog.init();
				Intent intent = new Intent(AActivity.this, ShowBigPictrue.class);
				intent.putExtra("url",
						"http://dl2.iteye.com/upload/attachment/0074/8459/3db78772-fce6-3617-89d4-5c38190accbe.jpg");
				startActivity(intent);

			}
		});
		AudioRecorderButton btn2 = (AudioRecorderButton) findViewById(R.id.btn2);
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
					HttpsURLConnection urlConnection = HttpsUtil2.getHttpsURLConnection(AActivity.this,
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
					responseReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
					String retPost = HttpManager.posturl(nameValuePairs,
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
