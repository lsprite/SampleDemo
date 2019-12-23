package com.example.fragmentdemo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
	/** 之前显示的内容 */
	private static String oldMsg;
	/** Toast对象 */
	private static Toast toast = null;
	/** 第一次时间 */
	private static long oneTime = 0;
	/** 第二次时间 */
	private static long twoTime = 0;
	private static Handler mHandler = new Handler(Looper.getMainLooper());

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showToast(Context context, String message) {
		if (toast == null) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (message.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = message;
				toast.setText(message);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

//	public synchronized static void showToast(Context context, String message, int duration) {
//		if (toast == null) {
//			toast = Toast.makeText(context, message, duration);
//		} else {
//			toast.cancel();
//			toast = Toast.makeText(context, message, duration);
//		}
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				toast.show(); // 会发现延迟之后就显示出来了
//			}
//		}, 200); // 这个时间是自己拍脑袋写的，不影响体验就好，试过使用post也不行
//	}
}
