package com.example.fragmentdemo.sll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.example.fragmentdemo.util.JSHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mennomorsink on 06/05/15.
 */
public class SslPinningWebViewClient extends WebViewClient {

	private Context context;

	public SslPinningWebViewClient(Context context) throws IOException {
		this.context = context;
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(final WebView view,
			String url) {
		return processRequest(Uri.parse(url));
	}

	@Override
	@TargetApi(21)
	public WebResourceResponse shouldInterceptRequest(final WebView view,
			WebResourceRequest interceptedRequest) {
		return processRequest(interceptedRequest.getUrl());
	}

	private WebResourceResponse processRequest(Uri uri) {
		try {

			if (JSHelper.getReallyUrl(uri.toString()).endsWith(".js")) {
				URL url = new URL(uri.toString());
				URLConnection urlConnection = url.openConnection();
				urlConnection.connect();
				InputStream is = urlConnection.getInputStream();
				//
				if (JSHelper.isURLDownValid(context, uri.toString(), is)) {
					getWebResourceResponse(uri.toString(), "text/javascript");
				} else {
					return new WebResourceResponse("text/javascript", "UTF-8",
							is);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// Return empty response for this request
		return null;
	}

	private WebResourceResponse getWebResourceResponse(String url, String mime) {
		WebResourceResponse response = null;
		try {
			System.out.println("使用本地保存的js---" + url);
			response = new WebResourceResponse(mime, "UTF-8",
					new FileInputStream(new File(JSHelper.getLocalJsPath(
							context, url))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

}