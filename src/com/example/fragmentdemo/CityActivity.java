package com.example.fragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CityActivity extends Activity {
	private Spinner provinceSpinner = null; // 省级（省、直辖市）
	private Spinner citySpinner = null; // 地级市
	private Spinner countySpinner = null; // 县级（区、县、县级市）
	ArrayAdapter<String> provinceAdapter = null; // 省级适配器
	ArrayAdapter<String> cityAdapter = null; // 地级适配器
	ArrayAdapter<String> countyAdapter = null; // 县级适配器
	static int provincePosition = 3;

	// 省级选项值
	private String[] province = new String[] { "快速补点", "小区线路整治" };
	// 地级选项值
	private String[][] city = new String[][] { { "分光器扩容", "分纤箱扩容", "onu扩容", "电源整改", "线路整治", "其他装项" },
			{ "整治", "光缆整改" } };

	// 县级选项值
	private String[][][] county = new String[][][] {
			{ //
					{ "一级分光器扩容", "二级分光器扩容" }, { "扩容延伸", "隐患整治", "更换" }, { "" }, { "更换电表", "电源线整治", "更换空开" },
					{ "铁吊线", "网络线", "光缆" }, { "" } },
			{ //
					{ "" }, { "" } } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		setSpinner();
	}

	/*
	 * 设置下拉框
	 */
	private void setSpinner() {
		provinceSpinner = (Spinner) findViewById(R.id.spin_province);
		citySpinner = (Spinner) findViewById(R.id.spin_city);
		countySpinner = (Spinner) findViewById(R.id.spin_county);

		// 绑定适配器和值
		provinceAdapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_spinner_item, province);
		provinceSpinner.setAdapter(provinceAdapter);

		cityAdapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_spinner_item, city[0]);
		citySpinner.setAdapter(cityAdapter);

		countyAdapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_spinner_item, county[0][0]);
		countySpinner.setAdapter(countyAdapter);

		// 省级下拉框监听
		provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			// 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// position为当前省级选中的值的序号

				// 将地级适配器的值改变为city[position]中的值
				cityAdapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_spinner_item,
						city[position]);
				// 设置二级下拉列表的选项内容适配器
				citySpinner.setAdapter(cityAdapter);
				provincePosition = position; // 记录当前省级序号，留给下面修改县级适配器时用
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		// 地级下拉监听
		citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
				countyAdapter = new ArrayAdapter<String>(CityActivity.this, android.R.layout.simple_spinner_item,
						county[provincePosition][position]);
				countySpinner.setAdapter(countyAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}
}
