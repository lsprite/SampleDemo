package com.example.fragmentdemo.util;

import java.io.File;
import java.io.IOException;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.utils.L;

import android.content.Context;
import android.os.Environment;

public class ImageLoaderTools {
	public static String getDiscCachePathByUrl(Context context, String url) {

		Md5FileNameGenerator md5 = new Md5FileNameGenerator();
		return getExternalCacheDir(context) + "/" + md5.generate(url);
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(
				Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(
				new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				L.w("Unable to create external cache directory");
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				L.i("Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appCacheDir;
	}
}
