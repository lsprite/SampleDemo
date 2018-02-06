package com.example.fragmentdemo.view;

import com.example.fragmentdemo.MainActivity;
import com.example.fragmentdemo.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

public class FlashDialog {
	private Context context;
	// 对话框布局对象
	private View view;
	// 这个是dialog里面的textview控件，到时候需要给成textpage的，因为需要触摸
	// 关闭按钮
	private Button guanbiBtn;
	// 对话框对象
	private AlertDialog flashDialog;
	private WebView webView;
	private LinearLayout webViewLinearLayout;
	private ProgressDialog mProgressDialog;
	// 做测试使用这个位置是固定好的。由于程序是每次从数据库取出的是byte[]，都是写到本地然后进行播放的。
	// private String mFlashFilename = "file://" + OtherUtils.getSDPath() +
	// "/temp.swf";
	private String mFlashFilename = "http://192.168.1.129:8081/DWXX_SERVER/123.swf";

	public FlashDialog(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 弹出自动关闭的模态对话框对象
	 */
	public void init() {
		// 动态加载布局,将布局文件实例化成一个views
		view = LayoutInflater.from(context).inflate(R.layout.playflash, null);
		if (flashDialog != null && flashDialog.isShowing()) {
			flashDialog.dismiss();
		}
		flashDialog = new AlertDialog.Builder(context).create();
		try {
			flashDialog.show();
		} catch (Exception e) {
		}
		WindowManager m = ((MainActivity) context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		// 以下代码设置对话框 的布局参数
		WindowManager.LayoutParams params = flashDialog.getWindow().getAttributes();
		params.gravity = Gravity.CENTER;
		// 设置dialog的宽和高
		params.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.7
		params.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.75
		// 设置对话框的布局参数为居中
		flashDialog.getWindow().setAttributes(params);
		// 参数true指出此对话框类似于非模态对话框
		flashDialog.setCancelable(false);
		// 设置对话框对象所使用的布局
		flashDialog.getWindow().setContentView(view);
		webView = (WebView) view.findViewById(R.id.webView1);
		webViewLinearLayout = (LinearLayout) view.findViewById(R.id.webviewLinearLayout);
		WebSettings settings = webView.getSettings();
		// 不使用缓存：
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setAllowFileAccess(true);
		settings.setJavaScriptEnabled(true);
		settings.setPluginState(PluginState.ON);
		settings.setLoadWithOverviewMode(true);
		try {
			Thread.sleep(500);// 主线程暂停下，否则容易白屏，原因未知
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mProgressDialog = ProgressDialog.show(context, "请稍等...", "加载flash中...", false, true);
		webView.setWebViewClient(new WebViewClient());

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				System.out.println("newProgress:" + String.valueOf(newProgress));
				if (newProgress == 100) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							mProgressDialog.dismiss();
						}
					}, 500);
				}
			}
		});
		webView.loadUrl(mFlashFilename);
		// 点击了对话框上的关闭按钮
		guanbiBtn = (Button) view.findViewById(R.id.button1);
		guanbiBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShutAutoDialog();
			}
		});
	}

	/**
	 * 此方法用于关闭自动对话框,不论是人为的关闭还是自动关闭都请调用此方法
	 */
	private void ShutAutoDialog() {
		if (flashDialog != null) {
			flashDialog.cancel();
		}
		if (webView != null) {
			// 解除webView与父控件的依赖关系
			webViewLinearLayout.removeView(webView);
			webView.removeAllViews();
			webView.destroy();
		}
	}
}