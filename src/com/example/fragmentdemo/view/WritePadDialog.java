package com.example.fragmentdemo.view;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.fragmentdemo.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WritePadDialog extends Dialog {

	Context context;
	LayoutParams p;
	DialogListener dialogListener;

	public WritePadDialog(Context context, DialogListener dialogListener) {
		// super(context);
		super(context, R.style.custom_dialog_theme);
		this.context = context;
		this.dialogListener = dialogListener;
	}

	static final int BACKGROUND_COLOR = Color.WHITE;

	static final int BRUSH_COLOR = Color.BLACK;// 画笔颜色

	PaintView mView;

	/** The index of the current color to use. */
	int mColorIndex;
	TextView tip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.write_pad);
		//
		p = getWindow().getAttributes(); // 获取对话框当前的参数值
		// p.height = 320;//(int) (d.getHeight() * 0.4); //高度设置为屏幕的0.4
		// p.width = 480;//(int) (d.getWidth() * 0.6); //宽度设置为屏幕的0.6
		// getWindow().setAttributes(p); //设置生效
		p.width = (int) (getWindow().getWindowManager().getDefaultDisplay()
				.getWidth() * 0.8);
		p.height = (int) (getWindow().getWindowManager().getDefaultDisplay()
				.getHeight() * 0.8);

		RelativeLayout frameLayout = (RelativeLayout) findViewById(R.id.tablet_view);

		mView = new PaintView(context);
		android.widget.RelativeLayout.LayoutParams lp = new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
				android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT, frameLayout.getId());
		mView.setLayoutParams(lp);
		// p.width = frameLayout.getMeasuredWidth();
		// p.height = frameLayout.getMeasuredHeight();
		frameLayout.addView(mView);
		tip = new TextView(context);
		tip.setText("签名区");
		tip.setTextSize(40);
		// tip.setTextColor(context.getResources().getColor(R.color.wh))
		android.widget.RelativeLayout.LayoutParams l = new android.widget.RelativeLayout.LayoutParams(
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT,
				android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
		l.addRule(RelativeLayout.CENTER_IN_PARENT, frameLayout.getId());
		tip.setLayoutParams(l);
		frameLayout.addView(tip);
		mView.requestFocus();
		Button btnClear = (Button) findViewById(R.id.tablet_clear);
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.clear();
				tip.setVisibility(View.VISIBLE);
			}
		});

		Button btnOk = (Button) findViewById(R.id.tablet_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					dialogListener.getSignImagePath(createFile(mView
							.getCachebBitmap()));
					WritePadDialog.this.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button btnCancel = (Button) findViewById(R.id.tablet_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WritePadDialog.this.dismiss();
			}
		});
	}

	/**
	 * This view implements the drawing canvas.
	 * 
	 * It handles all of the input events and drawing functions.
	 */
	class PaintView extends View {
		private Paint paint;
		private Canvas cacheCanvas;
		private Bitmap cachebBitmap;
		private Path path;

		public Bitmap getCachebBitmap() {
			return cachebBitmap;
		}

		public PaintView(Context context) {
			super(context);
			init();
		}

		private void init() {
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(10);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(BRUSH_COLOR);
			path = new Path();
			// 高度剪掉100是因为除了签名的地方，write_pad布局中顶部和底部设置死了各50dp，取得时候要去掉，要不然图片下部会有一段空白的地方
			cachebBitmap = Bitmap.createBitmap((int) (p.width),
					(int) (p.height) - dip2px(context, 100), Config.RGB_565);
			cacheCanvas = new Canvas(cachebBitmap);
			cacheCanvas.drawColor(BACKGROUND_COLOR);
			// cacheCanvas.drawColor(Color.TRANSPARENT);
		}

		public void clear() {
			if (cacheCanvas != null) {
				paint.setColor(BACKGROUND_COLOR);
				cacheCanvas.drawPaint(paint);// 设置背景用画笔画一次
				paint.setColor(BRUSH_COLOR);// 再次设置画笔颜色
				cacheCanvas.drawColor(BACKGROUND_COLOR);
				// cachebBitmap.eraseColor(Color.argb(0,0,0,0));//设置内部的Bitmap为透明色
				invalidate();
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// canvas.drawColor(BRUSH_COLOR);
			canvas.drawBitmap(cachebBitmap, 0, 0, null);
			canvas.drawPath(path, paint);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {

			int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
			int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
			if (curW >= w && curH >= h) {
				return;
			}

			if (curW < w)
				curW = w;
			if (curH < h)
				curH = h;

			Bitmap newBitmap = Bitmap.createBitmap(curW, curH,
					Bitmap.Config.RGB_565);
			Canvas newCanvas = new Canvas();
			newCanvas.setBitmap(newBitmap);
			if (cachebBitmap != null) {
				newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
			}
			cachebBitmap = newBitmap;
			cacheCanvas = newCanvas;
		}

		private float cur_x, cur_y;

		@Override
		public boolean onTouchEvent(MotionEvent event) {

			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				tip.setVisibility(View.GONE);
				cur_x = x;
				cur_y = y;
				path.moveTo(cur_x, cur_y);
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				path.quadTo(cur_x, cur_y, x, y);
				cur_x = x;
				cur_y = y;
				break;
			}

			case MotionEvent.ACTION_UP: {
				cacheCanvas.drawPath(path, paint);
				path.reset();
				break;
			}
			}

			invalidate();

			return true;
		}
	}

	private String createFile(Bitmap bitmap) {
		ByteArrayOutputStream baos = null;
		String _path = null;
		try {
			// String sign_dir = Environment.getExternalStorageDirectory()
			// .getPath() + File.separator + "FZ_JMRJ/sign/";
			String sign_dir = Environment.getExternalStorageDirectory()
					+ "/Android/data/" + context.getPackageName() + "/sign/";
			if (!new File(sign_dir).exists()) {
				new File(sign_dir).mkdirs();
			}
			String picNames = System.currentTimeMillis() + ".png";
			_path = sign_dir + picNames;
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] photoBytes = bitmap.toString().getBytes();
			if (photoBytes != null) {
				byte[] bs = null;
				try {
					File file = new File(sign_dir, picNames);
					FileOutputStream fos = new FileOutputStream(file);
					// 将Bitmap转换成byte[]
					bs = baos.toByteArray();
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					bos.write(bs);
					bos.flush();
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return _path;
	}

	// =
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public interface DialogListener {

		public void getSignImagePath(String path);

	}

}
