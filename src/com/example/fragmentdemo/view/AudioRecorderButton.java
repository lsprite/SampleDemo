package com.example.fragmentdemo.view;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioRecorderButton extends Button {
	private static final int STATE_NORMAL = 1;// 默认的状态
	private static final int STATE_RECORDING = 2;// 正在录音

	private int mCurrentState = STATE_NORMAL; // 当前的状态
	private boolean isRecording = false;// 已经开始录音

	private float mTime;
	// 是否触发longClick
	private boolean mReady;
	private StateChangeListener listener = null;

	/**
	 * 以下2个方法是构造方法
	 */
	public AudioRecorderButton(final Context context, AttributeSet attrs) {
		super(context, attrs);
		// String dir = "/storage/sdcard0/my_weixin";

		// 由于这个类是button所以在构造方法中添加监听事件
		setOnLongClickListener(new OnLongClickListener() {

			public boolean onLongClick(View v) {
				boolean sdCardExist = Environment.getExternalStorageState()
						.equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
				if (sdCardExist) {
					mReady = true;
				} else {
					Toast.makeText(context, "SD卡未存在,暂时无法使用语音功能",
							Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
	}

	public AudioRecorderButton(Context context) {
		this(context, null);
	}

	/**
	 * 屏幕的触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		int x = (int) event.getX();// 获得x轴坐标
		int y = (int) event.getY();// 获得y轴坐标

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			changeState(STATE_RECORDING);
			if (listener != null) {
				listener.start();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecording) {
				// 如果想要取消，根据x,y的坐标看是否需要取消
				changeState(STATE_RECORDING);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (!mReady) {
				reset();
				return super.onTouchEvent(event);
			}
			if (!isRecording || mTime < 0.6f) {// 时间小于0.6秒
				if (listener != null) {
					listener.end();
				}
			} else if (mCurrentState == STATE_RECORDING) { // 正在录音的时候，结束
				if (listener != null) {
					listener.end();
				}

			}
			reset();
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 恢复状态及标志位
	 */
	private void reset() {
		isRecording = false;
		mTime = 0;
		mReady = false;
		changeState(STATE_NORMAL);
	}

	/**
	 * 改变
	 */
	private void changeState(int state) {
		if (mCurrentState != state) {
			mCurrentState = state;
			switch (state) {
			case STATE_NORMAL:
				break;
			case STATE_RECORDING:
				break;
			}
		}
	}

	public void setStateChangeListener(StateChangeListener listener) {
		this.listener = listener;
	}

	public interface StateChangeListener {

		public void start();

		public void end();
	}

}
