package com.example.fragmentdemo;

import java.util.Calendar;
import java.util.Date;

import com.example.fragmentdemo.view.calendar.CalendarUtil;
import com.example.fragmentdemo.view.calendar.CalendarView;
import com.example.fragmentdemo.view.calendar.CalendarView.OnCalendarViewListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CalendarActivity extends Activity {
	private CalendarView calendar;
	private Button btn;
	private TextView tv;
	private Handler handler = new Handler() {
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		//
		SharedPreferences preferences = MainApplication.getInstance()
				.getApplicationContext()
				.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("type", 0);
		editor.commit();
		// 删除之前保存的地址
		MainApplication.getInstance().getApplicationContext()
				.getSharedPreferences("pushFlag", Activity.MODE_MULTI_PROCESS)
				.edit().putBoolean("flag", false).commit();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						"com.ctrlsoft.dwxx.finishallactivity");
				MainApplication.getInstance().getApplicationContext()
						.sendBroadcast(intent);
			}
		}, 15000);
		//
		calendar = (CalendarView) findViewById(R.id.calendar);
		calendar.setOnCalendarViewListener(new OnCalendarViewListener() {

			@Override
			public void onCalendarItemClick(CalendarView view, Date date) {
				// TODO Auto-generated method stub
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				tv.setText(CalendarUtil.getCurrentDay(cal));
			}
		});
		tv = (TextView) findViewById(R.id.tv);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				calendar.toToday();
				Calendar cal = Calendar.getInstance();
				tv.setText(CalendarUtil.getCurrentDay(cal));
			}
		});
	}
}
