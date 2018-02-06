package com.example.fragmentdemo.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public final class UILUtils {

	private static DisplayImageOptions options;
	private static DisplayImageOptions optionsWithDefault;

	public static void displayImage(Context context, String imgurl, ImageView imageView) {
		try {
			initOptions();
			ImageLoader.getInstance().displayImage(imgurl, imageView, options, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					try {
						System.out.println("图片大小:" + loadedImage.getWidth() + "," + loadedImage.getHeight());
						((ImageView) view).setImageBitmap(MosaicProcessor.makeMosaic(loadedImage, null, 10));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void displayImage2(Context context, String imgurl, ImageView imageView) {
		try {
			initOptions();
			ImageLoader.getInstance().displayImage(imgurl, imageView, options, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub

				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void displayLocalImage(Context context, String imgurl, ImageView imageView) {
		try {
			initOptions();
			ImageLoader.getInstance().displayImage("file://" + imgurl, imageView, options, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void displayImageWithDefault(Context context, String imgurl, ImageView imageView) {
		try {
			initOptionsWithDefault();
			ImageLoader.getInstance().displayImage(imgurl, imageView, optionsWithDefault, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private static void initOptions() {
		if (options == null) {
			// options = new DisplayImageOptions.Builder()
			// .showImageOnLoading(R.drawable.load_logo)
			// .showImageForEmptyUri(R.drawable.load_logo)
			// .showImageOnFail(R.drawable.load_logo).cacheInMemory(true)
			// .cacheOnDisk(true).considerExifParams(true)
			// .displayer(new RoundedBitmapDisplayer(20)).build();
			options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					// 设置图片的解码类型
					.imageScaleType(ImageScaleType.EXACTLY).delayBeforeLoading(100).build();
		}
	}

	private static void initOptionsWithDefault() {
		if (optionsWithDefault == null) {
			optionsWithDefault = new DisplayImageOptions.Builder()
					// .showStubImage(R.drawable.da)
					// .showImageForEmptyUri(R.drawable.loading_error)
					// .showImageOnFail(R.drawable.loading_error)
					.cacheInMemory(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
					.delayBeforeLoading(100).bitmapConfig(Bitmap.Config.RGB_565) // 设置图片的解码类型
					.build();
		}
	}
}
