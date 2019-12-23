package com.example.fragmentdemo;

import com.example.fragmentdemo.view.ZoomScrollView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_B_2 extends BaseFragment {
	private View view;
	//
	private LinearLayout ll_head;
	private TextView tv_title;
	private ZoomScrollView zoomscrollview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_b_2, container, false);
		initView();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_head = (LinearLayout) view.findViewById(R.id.ll_head);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		zoomscrollview = (ZoomScrollView) view.findViewById(R.id.zoomscrollview);
		zoomscrollview.setHeaderView(ll_head, tv_title);
		//
		Button btn = (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}

}
