package com.example.fragmentdemo;

import com.example.fragmentdemo.util.UILUtils;
import com.zzn.utils.ScaleView;

import android.app.Activity;
import android.os.Bundle;

public class ShowBigPictrue extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scale_pic_item);
		ScaleView imageView = (ScaleView) findViewById(R.id.scale_pic_item);
		String url = getIntent().getStringExtra("url");
		UILUtils.displayImage2(this, url, imageView);
		
	}
}
