package com.example.fragmentdemo;

import com.example.fragmentdemo.view.AdvancedWebView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends Activity {
	AdvancedWebView webView;
	boolean isOnPause = false;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		Toast.makeText(this, "WebActivity", Toast.LENGTH_SHORT).show();
		initView();
		setWebView();
		// webView.loadUrl("http://192.168.1.27/xm_pwjk/Login.aspx");
		// webView.loadUrl("http://192.168.1.110/jzglService/test.docx");
		// webView.loadUrl("http://192.168.1.116/PTKDWebservice/html/3_pptx/3.html");
		webView.loadUrl("http://47.97.201.255:17003/spa/#/login");
		// webView.loadUrl(getIntent().getStringExtra("url"));
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				webView.loadUrl("javascript: initdata('" + "img" + "','" + "李四" + "','" + "恭喜发财" + "')");
			}
		}, 1000);
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			if (isOnPause) {
				if (webView != null) {
					webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
				}
				isOnPause = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if (webView != null) {
				webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
				isOnPause = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// webView.reload();
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("---WebActivity.onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		webView.setVisibility(View.GONE);
		super.onDestroy();
	}

	private void initView() {
		// TODO Auto-generated method stub
		webView = (AdvancedWebView) findViewById(R.id.web_webview);
	}

	private void setWebView() {
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// 设置WebView属性，能够执行Javascript脚本
		WebSettings settings = webView.getSettings();
		// 支持缩放
		settings.setSupportZoom(true);
		// 启用内置缩放装置
		settings.setBuiltInZoomControls(true);
		// 启用JS脚本
		settings.setJavaScriptEnabled(true);
		// 支持Html5 Video播放
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);

		webView.setWebChromeClient(m_chromeClient);
		webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
					long contentLength) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
			}
		});
		// 网页控件显示
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				} else {
					view.loadUrl(url); // 加载新的url
				}
				return true; // 返回true,代表事件已处理,事件流到此终止
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
		// try {
		// webView.setWebViewClient(new
		// SslPinningWebViewClient(WebActivity.this) {
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// if (url.startsWith("tel:")) {
		// Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		// startActivity(intent);
		// }
		// view.loadUrl(url); // 加载新的url
		// return true; // 返回true,代表事件已处理,事件流到此终止
		// }
		//
		// @Override
		// public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// super.onPageStarted(view, url, favicon);
		//
		// }
		//
		// @Override
		// public void onPageFinished(WebView view, String url) {
		// super.onPageFinished(view, url);
		// }
		// });
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
	}

	private WebChromeClient m_chromeClient = new WebChromeClient() {
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
		}

		public void onPermissionRequest(android.webkit.PermissionRequest request) {
			request.grant(request.getResources());
		};
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(0, 0);
			return true;
		}
		return false;
	}
}
