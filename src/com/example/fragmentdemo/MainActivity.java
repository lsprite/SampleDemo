package com.example.fragmentdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

/**
 * 我的
 */
public class MainActivity extends FragmentActivity {
	private Fragment_A fragment_A;
	private Fragment_B fragment_B;
	FragmentManager fm;
	private Button btn_a, btn_b;
	private int selected = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 避免第一次打开造成启动的activity按home后重新实例化
		if (!MainApplication.getInstance().isStartApp()) {
			finish();
		}
		try {
			MainApplication.getInstance().getApplicationContext();
		} catch (Exception e) {
			// TODO: handle exception
		}

		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		initView();
		showFragment(selected);
		// AppInfoUtil.getAppInfo(this);
		// ConnectivityManager connectivityManager = (ConnectivityManager)
		// getSystemService(Context.CONNECTIVITY_SERVICE);//
		// int phoneConstants = connectivityManager.startUsingNetworkFeature(
		// ConnectivityManager.TYPE_MOBILE, "enableMMS");
		// System.out.println("---phoneConstants:" + phoneConstants);
		// NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
		// String apn = ni.getExtraInfo();// 获取网络接入点，这里一般为cmwap和cmnet
		// System.out.println("---apn:" + apn);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		// Log.v("LH", "onSaveInstanceState"+outState);
		// super.onSaveInstanceState(outState);
		// 将这一行注释掉，阻止activity保存fragment的状态
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("---MainActivity.onStop");
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_a = (Button) findViewById(R.id.btn_a);
		btn_b = (Button) findViewById(R.id.btn_b);
	}

	public void myClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_a://
			if (selected != 1) {
				//
				showFragment(1);
				selected = 1;
			}
			break;
		case R.id.btn_b://
			if (selected != 2) {
				//
				showFragment(2);
				selected = 2;
			}
			break;
		default:
			break;
		}
	}

	public void showFragment(int index) {
		FragmentTransaction ft = fm.beginTransaction();
		// 想要显示�?个fragment,先隐藏所有fragment，防止重�?
		hideFragments(ft);

		switch (index) {
		case 1:
			// 如果fragment1已经存在则将其显示出�?
			if (fragment_A != null)
				ft.show(fragment_A);
			// 否则是第�?次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
			else {
				fragment_A = new Fragment_A();
				ft.add(R.id.fragment, fragment_A);
			}
			break;
		case 2:
			if (fragment_B != null)
				ft.show(fragment_B);
			else {
				fragment_B = new Fragment_B();
				ft.add(R.id.fragment, fragment_B);
			}
			break;

		}
		ft.commitAllowingStateLoss();
	}

	// 当fragment已被实例化，就隐藏起�?
	public void hideFragments(FragmentTransaction ft) {
		if (fragment_A != null)
			ft.hide(fragment_A);
		if (fragment_B != null)
			ft.hide(fragment_B);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(0, 0);
			return true;
		}
		return false;
	}

	// APN列表资源
	protected static Uri APN_LIST_URI = Uri
			.parse("content://telephony/carriers");
	protected static Uri CURRENT_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");

	public int updateCurrentAPN(ContentResolver resolver, String newAPN) {
		Cursor cursor = null;
		try {
			// get new apn id from list
			cursor = resolver.query(APN_LIST_URI, null,
					" apn = ? and current = 1",
					new String[] { newAPN.toLowerCase() }, null);
			String apnId = null;
			if (cursor != null && cursor.moveToFirst()) {
				apnId = cursor.getString(cursor.getColumnIndex("_id"));
			}
			cursor.close();
			System.out.println("----apnId:" + apnId);
			// set new apn id as chosen one
			if (apnId != null) {
				ContentValues values = new ContentValues();
				values.put("apn_id", apnId);
				resolver.update(CURRENT_APN_URI, values, null, null);
				//
				// // 通知apn已经更改
				// IntentFilter upIntentFilter = new IntentFilter(
				// ConnectivityManager.CONNECTIVITY_ACTION);
				// registerReceiver(new NetworkChangeReceiver(),
				// upIntentFilter);

			} else {
				// apn id not found, return 0.
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		// update success
		return 1;
	}
}
