package com.example.fragmentdemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainApplication extends Application {
	private static MainApplication instance;
	// 避免http://blog.csdn.net/love100628/article/details/43238135提到的错误,在启动的activity里设置
	private boolean isStartApp = true;

	//
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("MyApplication.onCreate()");
		initImageLoader(getApplicationContext());
		instance = this;
	}

	public static MainApplication getInstance() {
		if (instance == null) {
			instance = new MainApplication();
		}
		return instance;
	}

	public boolean isStartApp() {
		return isStartApp;
	}

	public void setStartApp(boolean isStartApp) {
		this.isStartApp = isStartApp;
	}

	// 使用imageloader加載圖片
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		System.out.println("=========初始化ImageLoader");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 4)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}