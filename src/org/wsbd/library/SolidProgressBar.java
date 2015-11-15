package org.wsbd.library;


import com.alex.hotcourts.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SolidProgressBar extends View implements OnTouchListener{
		
	private int		 		mValue = 0;
	private int				mMaxValue = 100;
	
	private int				mBarResourceId;
	private int				mThumbResourceId;
	
	private Bitmap			mBarImage;
	private Bitmap			mThumbImage;
	
	private OnChangeListener 	mOnEventListener;
	
	public interface OnChangeListener{
		void onChange(SolidProgressBar view, float value);
	}

	public void setOnChangeListener(OnChangeListener listener){
		mOnEventListener = listener;
	}
	
	public void setThumbBitmap(Bitmap bmp){
		mThumbImage = bmp;
	}

	public SolidProgressBar(Context context) {
		super(context);
		setOnTouchListener(this);
		mBarResourceId = R.drawable.slider_bar;
		mThumbResourceId = R.drawable.slider_thumb;
		
		mBarImage = BitmapFactory.decodeResource(getContext().getResources(), mBarResourceId);
		mThumbImage = BitmapFactory.decodeResource(getContext().getResources(), mThumbResourceId);
	}
	
	public SolidProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(this);
		
		mBarResourceId = R.drawable.slider_bar;
		mThumbResourceId = R.drawable.slider_thumb;
		
		mBarImage = BitmapFactory.decodeResource(getContext().getResources(), mBarResourceId);
		mThumbImage = BitmapFactory.decodeResource(getContext().getResources(), mThumbResourceId);
	}
	
	public void setMaxValue(int maxValue){
		mMaxValue = maxValue;
	}
	
	public void setResourceIds(int barResourceId, int thumbResourceId) {
		mBarResourceId = barResourceId;
		mThumbResourceId = thumbResourceId;
		
		mBarImage = BitmapFactory.decodeResource(getContext().getResources(), mBarResourceId);
		mThumbImage = BitmapFactory.decodeResource(getContext().getResources(), mThumbResourceId);
	}
	
	public int getValue() {
		return mValue;
	}
	
	public void setValue(int value) {
		mValue = value;
		invalidate();
	}
	
	public void setEditable(boolean editable){
		
	}
	
	public void setBackgroundColor(int backColor) {
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		
		float thumbWidth = getHeight() * mThumbImage.getWidth() / mThumbImage.getHeight();
		
		float rate = 0.25f; 
		
		if (x < thumbWidth * rate) 
			x = thumbWidth * rate;
		
		
		if (x > getWidth() - thumbWidth * rate) {
			x = getWidth() - thumbWidth * rate;
		}
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			
			mValue = (int) ((x - thumbWidth * rate) / (getWidth() - thumbWidth * 2 * rate) * mMaxValue);
			 
			if (mOnEventListener != null)
				mOnEventListener.onChange(this, mValue);
			
			invalidate();
			
			return true;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_MOVE:
				mValue = (int) ((x - thumbWidth * rate) / (getWidth() - thumbWidth * 2 * rate) * mMaxValue);
				if (mOnEventListener != null)
					mOnEventListener.onChange(this, mValue);
				invalidate();
			return true;
		case MotionEvent.ACTION_UP:
			break;
		}
		
		return true;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		float thumbWidth = getHeight() * mThumbImage.getWidth() / mThumbImage.getHeight();
		float rate = 0.25f;
		
		canvas.drawBitmap(mBarImage, new Rect(0, 0, mBarImage.getWidth(), mBarImage.getHeight()), new Rect(0, 0, getWidth(), getHeight()), new Paint());
		
		canvas.drawBitmap(mThumbImage, new Rect(0, 0, mThumbImage.getWidth(), mThumbImage.getHeight()), 
				new Rect((int) (mValue / (float)mMaxValue * (getWidth() - thumbWidth * rate * 2) - thumbWidth * rate) , 0, (int) (mValue / (float)mMaxValue * (getWidth() - thumbWidth * rate * 2) - thumbWidth * rate) + (int)(thumbWidth), getHeight()), new Paint());
	}
}
