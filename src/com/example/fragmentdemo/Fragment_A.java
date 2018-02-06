package com.example.fragmentdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;

import com.example.fragmentdemo.sll.HttpManager;
import com.example.fragmentdemo.sll.HttpsUtil2;
import com.example.fragmentdemo.util.HttpUtil;
import com.example.fragmentdemo.util.MD5Util;
import com.example.fragmentdemo.util.SMS4;
import com.example.fragmentdemo.util.UILUtils;
import com.example.fragmentdemo.util.WpsModel;
import com.example.fragmentdemo.util.WpsModel.ClassName;
import com.example.fragmentdemo.util.WpsModel.OpenMode;
import com.example.fragmentdemo.util.WpsModel.PackageName;
import com.example.fragmentdemo.view.AudioRecorderButton;
import com.example.fragmentdemo.view.AudioRecorderButton.StateChangeListener;
import com.example.fragmentdemo.view.OWLoadingView;
import com.example.fragmentdemo.view.WritePadDialog;
import com.example.fragmentdemo.view.WritePadDialog.DialogListener;
import com.stericson.RootTools.RootTools;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class Fragment_A extends BaseFragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_a, container, false);
		initView();
		System.out.println("0---" + SMS4.encodeSMS4toString("哎哟不错哦"));
		System.out.println("1---" + SMS4.encodeSMS4toString("123456"));
		System.out.println("2---" + SMS4.decodeSMS4toString("d0db05049297723e5779c397f1fc02da"));
		System.out.println("3---" + SMS4.decodeSMS4toString("65fa2c5ad28eb7fe17a7917213ef7e85"));
		// SMS4.test();
		// getInfo();
		// System.out.println(PhoneUtil.getPsdnIp());
		// doSU();
		boolean isRootAvailable = RootTools.isRootAvailable();// 判断是否root
		boolean isAccessGiven = RootTools.isAccessGiven();// 返回true那么手机已经root并且app也被授予root权限。
		System.out.println("isRootAvailable:" + isRootAvailable);
		System.out.println("isAccessGiven:" + isAccessGiven);
		System.out.println("---" + MD5Util.encrypt("哟哟不错哦"));
		System.out.println("---" + MD5Util.encryption(
				"secretapp_id100000000001formatxmlsign_methodmd5test_flag0time_stamp20170814152633version1.0secret"));
		test();
		return view;
	}

	private void doSU() {
		try {
			Process process = Runtime.getRuntime().exec("su");// (这里执行是系统已经开放了root权限，而不是说通过执行这句来获得root权限)
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			// 如果已经root，但是用户选择拒绝授权,e.getMessage() = write failed: EPIPE (Broken
			// pipe)
			// 如果没有root，e.getMessage()= Error running exec(). Command: [su]
			// Working Directory: null Environment: null
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	ImageView img;
	OWLoadingView owloading;
	boolean isLoading = true;

	private void initView() {
		// TODO Auto-generated method stub
		owloading = (OWLoadingView) view.findViewById(R.id.owloading);
		owloading.setAutoStartAnim(true);// 设置自动开启动画
		owloading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isLoading) {
					owloading.stopAnim();
				} else {
					owloading.startAnim();
				}
				isLoading = !isLoading;
			}
		});
		//
		img = (ImageView) view.findViewById(R.id.img);
		UILUtils.displayImage(getActivity(),
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
				view.startAnimation(set);
			}
		});
		// try {
		// img.setImageBitmap(MosaicProcessor.makeMosaic(null, null, 10));
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
		//
		Button btn = (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), WebActivity.class);
				// intent.putExtra("url",
				// "file:///android_asset/html/honhbao.html");
				intent.putExtra("url",
						"https://jeromeetienne.github.io/AR.js/three.js/examples/mobile-performance.html");
				startActivity(intent);
			}
		});
		Button btn1 = (Button) view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WritePadDialog writeTabletDialog = new WritePadDialog(getActivity(), new DialogListener() {
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
				//
				// Intent intent = new Intent();
				// intent.setClassName("com.ctrlsoft.xm_pwjkxj",
				// "com.ctrlsoft.xm_pwjkxj.FirstActivity");
				// // intent.putExtra("param",
				// //
				// "{\"result\":true,\"code\":20000,\"content\":\"2051301949b1c94ddf86ef1a802c8d91647b959f2522a776422bca44958b1df963f6769c0fc2033f40920125af43732b56677e2404ebe50f150d0960638846b3938ad87c9f0b71358f0f904c152b0b25673d1460a5dd2e54baa3aa8bb382dfcd394699a3f710eeb303fc63aeedefc953a6dc37f84f5b3f0b958476c8897e2a0739b9651b7a83a7e72a9ebfd65df89c4a80009162de9fdb1edbe456519a4805c0198ef3c1b844beaa9e52d90e130f08c004010914856d70802ff9e7cb0e16d39f5ab0b3f4ae5a90e9fbe3999a9a9485114edbffa6342e4d6f4d1c0a10531d5fc1e87fabc8d68854a27b52842ed09d08ea017d337902adb1fc2e3b02d127dff6d7ebd33cac5cc1ff04148c5f4d0066948969ed3d892ac19f49de3fedb7a23e25f5\",\"apps\":{\"privilege\":[{\"PACKAGE_NAME\":\"com.cnksi.sjjc\",\"APP_NAME\":\"变电运维\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.cld.nv.electric\",\"APP_NAME\":\"电力导航\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol\",\"APP_NAME\":\"输电巡检\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol.gjcj\",\"APP_NAME\":\"路线采集\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.ctrlsoft.xm_pwjkxj\",\"APP_NAME\":\"配电巡检\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"},{\"PACKAGE_NAME\":\"com.epgis.yjzy.im\",\"APP_NAME\":\"即时通信\",\"DESCRIPTION\":\"无\",\"VERSION\":\"V1\"}],\"myapps\":[{\"PACKAGE_NAME\":\"com.epgis.yjydzy.im\"},{\"PACKAGE_NAME\":\"com.cld.nv.electric\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol.gjcj\"},{\"PACKAGE_NAME\":\"com.cnksi.sjjc\"},{\"PACKAGE_NAME\":\"com.ctrlsoft.xm_pwjkxj\"},{\"PACKAGE_NAME\":\"com.epgis.epmsapp.apps.ptpatrol\"}]}}");
				// intent.putExtra("param",
				// "{\"result\":
				// true,\"pdaddress\":\"8d3903b1679b4dda758b83c3311379345954d29dbe06afda5727b2fe8ffcdf1410b6eda7dacc6670e723d9f481bc24171758f46c36189b80c135b7bcfb693c30ec30d60e2765ab86ca275d7f061034c2\"}");
				// startActivity(intent);
				//
				// FlashDialog flashDialog = new FlashDialog(getActivity());
				// flashDialog.init();
				// Intent intent = new Intent(getActivity(),
				// ShowBigPictrue.class);
				// intent.putExtra("url",
				// "http://dl2.iteye.com/upload/attachment/0074/8459/3db78772-fce6-3617-89d4-5c38190accbe.jpg");
				// startActivity(intent);
				//
				Intent intent = new Intent(getActivity(), WebActivity.class);
				startActivity(intent);
				//
				// String stringPath =
				// Environment.getExternalStorageDirectory().getAbsolutePath() +
				// "/test.docx";
				// System.out.println("StringPath:"+stringPath);
				// boolean flag = openFile(stringPath);
				// if (flag == true) {
				//
				// Toast.makeText(getActivity(), " 打开文件成功", 2000).show();
				// } else {
				// Toast.makeText(getActivity(), "打开文件失败", 2000).show();
				// }
			}
		});
		AudioRecorderButton btn2 = (AudioRecorderButton) view.findViewById(R.id.btn2);
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

	boolean openFile(String path) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(WpsModel.OPEN_MODE, OpenMode.NORMAL); // 打开模式
		bundle.putBoolean(WpsModel.SEND_CLOSE_BROAD, true); // 关闭时是否发送广播
		bundle.putString(WpsModel.THIRD_PACKAGE, getActivity().getPackageName()); // 第三方应用的包名，用于对改应用合法性的验证
		bundle.putBoolean(WpsModel.CLEAR_TRACE, true);// 清除打开记录
		// bundle.putBoolean(CLEAR_FILE, true); //关闭后删除打开文件
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setClassName(PackageName.NORMAL, ClassName.NORMAL);

		File file = new File(path);
		if (file == null || !file.exists()) {
			System.out.println("文件为空或者不存在");
			return false;
		}

		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		intent.putExtras(bundle);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			System.out.println("打开wps异常：" + e.toString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void getInfo() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("++++0");
					HttpsURLConnection urlConnection = HttpsUtil2.getHttpsURLConnection(getActivity(),
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

	public void test() {
		Thread thread2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					// System.out.println("++++0");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
					String time = df.format(new Date());//
					String retPost = HttpUtil.posturl(
							"<?xml version=\"1.0\" encoding=\"UTF-8\">" + "<operation_in>"
									+ "<serverName>sms_send<rverName>" + "<operatorId>0237612996</operatorId>"
									+ "<requestTime>" + time + "</requestTime>" + "<mobiles>13763892253</mobiles>"
									+ "<smsContent>云mas测试短信</smsContent>" + "</operation_in>",
							"http://183.252.56.132:80/newCop/admin/cloudmas/sendmsg.jhtml?app_id=6853261419&sign_method=md5&format=xml&time_stamp=20180102114100&test_flag=1&version=1.0&sign=5B6A9C63F170674E3214520ACEE16E41ECE4C0CC68980BCCD30DE565DBFA9587E8F845F5DEB846319ED9CCBCD91A0D57378E48AFD138D152E704FDF9401918F8",
							"utf-8");
					System.out.println(retPost);
					// SimpleDateFormat df = new
					// SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
					// String time = df.format(new Date());//
					// String content = "<?xml version=\"1.0\"
					// encoding=\"UTF-8\">" + "<operation_in>"
					// + "<serverName>sms_send<rverName>" +
					// "<operatorId>0237612996</operatorId>" + "<requestTime>"
					// + time + "</requestTime>" +
					// "<mobiles>13763892253</mobiles>"
					// + "<smsContent>云mas测试短信</smsContent>" +
					// "</operation_in>";
					// MediaType XML =
					// MediaType.parse("text/xml;charset=iso-8859-1");
					// final Request.Builder builder = new
					// Request.Builder().url(
					// "http://183.252.56.132:80/newCop/admin/cloudmas/sendmsg.jhtml?app_id=6853261419&sign_method=md5&format=xml&time_stamp=20180102114100&test_flag=1&version=1.0&sign=5B6A9C63F170674E3214520ACEE16E41ECE4C0CC68980BCCD30DE565DBFA9587E8F845F5DEB846319ED9CCBCD91A0D57378E48AFD138D152E704FDF9401918F8");
					// // 将请求头以键值对形式添加，可添加多个请求头
					// RequestBody requestBody = RequestBody.create(XML,
					// content);
					// final Request request =
					// builder.post(requestBody).build();
					// final OkHttpClient client = new
					// OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
					// .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(60,
					// TimeUnit.SECONDS).build(); // 设置各种超时时间
					// final Call call = client.newCall(request);
					// Response response = call.execute();
					// System.out.println("---" + response.body().string());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		thread2.start();
	}
}
