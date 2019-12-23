package com.example.fragmentdemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ExitButton extends View {
	protected Context mContext;
	/** 中间大黑点画笔 **/
	private Paint bigPointPaint;
	/** 外围圆环画笔 **/
	private Paint ringCirclePaint;
	// view的宽度和高度
	private int viewWidth, viewHeight;
	private int strokeWidth = 7;
	private int duration = 200;
	private int px = 0;

	public ExitButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExitButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExitButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExitButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		this.mContext = context;
		bigPointPaint = new Paint();
		bigPointPaint.setColor(Color.BLACK);
		bigPointPaint.setAntiAlias(true);// 开启抗锯齿
		//
		ringCirclePaint = new Paint();
		ringCirclePaint.setColor(Color.BLACK);
		ringCirclePaint.setStyle(Paint.Style.STROKE);// 空心圆
		ringCirclePaint.setStrokeWidth(strokeWidth);
		ringCirclePaint.setAntiAlias(true);// 开启抗锯齿
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// android:layout_width和android:layout_height设置固定值
		viewWidth = getMeasuredWidth();
		viewHeight = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		System.out.println("-----" + getWidth() + "," + getHeight());
		canvas.drawCircle(viewWidth / 2 + px, viewHeight / 2, viewWidth / 2 - strokeWidth * 3, bigPointPaint);
		canvas.drawCircle(viewWidth / 2 + px, viewHeight / 2, viewWidth / 2 - strokeWidth, ringCirclePaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			bigPointPaint.setColor(Color.BLUE);
			ringCirclePaint.setColor(Color.BLUE);
			invalidate();
			change();
			break;
		case MotionEvent.ACTION_UP:
			bigPointPaint.setColor(Color.BLACK);
			ringCirclePaint.setColor(Color.BLACK);
			invalidate();
			unchange();
			break;
		default:
			break;
		}
		return true;
	}

	public void change() {
		// animate().translationX(strokeWidth);
		ObjectAnimator handScaleAnim = ObjectAnimator.ofInt(this, "px", 0, strokeWidth);
		handScaleAnim.setDuration(duration);
		handScaleAnim.start();
	}

	public void unchange() {
		// animate().translationX(-strokeWidth);
		ObjectAnimator handScaleAnim = ObjectAnimator.ofInt(this, "px", 0, -strokeWidth);
		handScaleAnim.setDuration(duration);
		handScaleAnim.start();
	}

	public void setPx(int px) {
		this.px = px;
		invalidate();
	}

}
