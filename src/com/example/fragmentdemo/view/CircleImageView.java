package com.example.fragmentdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {
	private int borderWidth = 4;
	private int size;
	private Bitmap image;
	private Paint paint;
	private Paint paintBorder;
	private BitmapShader shader;

	public CircleImageView(Context context) {
		super(context);
		setup();
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
	}

	private void setup() {
		// init paint
		paint = new Paint();
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		setBorderColor(Color.WHITE);
		paintBorder.setAntiAlias(true);
		// this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
		paintBorder.setStyle(Paint.Style.STROKE);
		paintBorder.setStrokeWidth(borderWidth);// 外边框的大小
		paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.DKGRAY);
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		this.invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null)
			paintBorder.setColor(borderColor);

		this.invalidate();
	}

	private void loadBitmap() {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

		if (bitmapDrawable != null)
			image = bitmapDrawable.getBitmap();
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		// load the bitmap
		loadBitmap();

		// init shader
		if (image != null) {
			shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvas.getWidth(), canvas.getHeight(), true),
					Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			paint.setShader(shader);
			int circleCenter = size / 2;

			// circleCenter is the x or y of the view's center
			// radius is the radius in pixels of the cirle to be drawn
			// paint contains the shader that will texture the shape
			canvas.drawCircle(circleCenter, circleCenter, circleCenter - borderWidth / 2, paint);
			canvas.drawCircle(circleCenter, circleCenter, circleCenter - borderWidth, paintBorder);

		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 由于是圆形，宽高应保持一致
		size = Math.min(getMeasuredWidth(), getMeasuredHeight());
		setMeasuredDimension(size, size);
	}

}
