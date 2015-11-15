package com.alex.hotcourts;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.wsbd.library.LayoutController;
import org.wsbd.library.UIUtils;
import org.wsbd.library.ViewAnimations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

public class StartActivity extends Activity {

	/** Called when the activity is first created. */
	
	public static final String TAG = "StartActivity";
	
	private final static int WHEEL_SHOWING_TIME = 300;
	
	private boolean mFocusProcessed = false;
	
	private final int mButtonResIds[] = {R.id.FrameLayoutButtonSports, R.id.FrameLayoutButtonHobbies, R.id.FrameLayoutButtonCommunity, R.id.FrameLayoutGpsButton};
	private boolean mWheelDisplayed = false;
	private boolean mWheelShowing = false;
	
	private int mCurrentSelectedValues[] = new int[4];
	
	private int mCurrentSelectedButtonsNumber;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setEvents();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	
    	if (hasFocus && !mFocusProcessed) {
    		mFocusProcessed = true;
    		initLayouts();
    	}
    }
    
    private void initLayouts(){
    	FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
		int screenWidth = container.getWidth();
		
		float rate = screenWidth / 648.0f * UIUtils.convertPixelsToDp(1, this);
		LayoutController.fitToScreen(this, container, rate);
		
		FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, (int)(1008 * rate / UIUtils.convertPixelsToDp(1, this)), Gravity.LEFT|Gravity.TOP);
		container.setLayoutParams(param);
		
		((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)).setVisibility(View.GONE);
		((FrameLayout)findViewById(R.id.FrameLayoutSemiLayer)).setVisibility(View.GONE);
    }
    
    private void setEvents(){
    	
    	for (int i = 0; i < mButtonResIds.length; i++) {
    		
    		final int number = i;
    		
			((Button)findViewById(mButtonResIds[i])).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (mWheelShowing) return;
					// TODO Auto-generated method stub
					if (mCurrentSelectedButtonsNumber != number + 1) {
						deselectAllButtons();
						
						if (mCurrentSelectedButtonsNumber != 0) {
							hideScroll(mCurrentSelectedButtonsNumber);
						}
						
						showScroll(number + 1);
						
						((Button)findViewById(mButtonResIds[number])).setSelected(true);
						mCurrentSelectedButtonsNumber = number + 1;
						
					} else {
						deselectAllButtons();
						
						hideScroll(mCurrentSelectedButtonsNumber);
						
						mCurrentSelectedButtonsNumber = 0;
					}
   				}
			});
    	}
    	
    	((Button) findViewById(R.id.ButtonWheelDoneButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mWheelShowing) return;
				if (mWheelDisplayed) {
					hideScroll(mCurrentSelectedButtonsNumber);
				}
			}
		});
    	
    	((FrameLayout) findViewById(R.id.FrameLayoutSemiLayer)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mWheelShowing) return;
				if (mWheelDisplayed) {
					hideScroll(mCurrentSelectedButtonsNumber);
				}
			}
		});
    	
    	((ImageButton) findViewById(R.id.FrameLayoutSearchButton)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StartActivity.this, MainBoardActivity.class);
				startActivity(intent);
			}
		});
    	
    	((WheelView) findViewById(R.id.WheelViewCategories)).addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				if (!mWheelShowing && mWheelDisplayed) {
					
					String[] 	catArray = null;
					Button		setButton = null;
					
					switch(mCurrentSelectedButtonsNumber - 1) {
			    	case 0:
			    		catArray = getResources().getStringArray(R.array.sports_cat_items);
			    		setButton = (Button) findViewById(R.id.FrameLayoutButtonSports);
			    		break;
			    	case 1:
			    		catArray = getResources().getStringArray(R.array.hobbies_cat_items);
			    		setButton = (Button) findViewById(R.id.FrameLayoutButtonHobbies);
			    		break;
			    	case 2:
			    		catArray = getResources().getStringArray(R.array.community_cat_items);
			    		setButton = (Button) findViewById(R.id.FrameLayoutButtonCommunity);
			    		break;
			    	case 3:
			    		catArray = getResources().getStringArray(R.array.location_cat_items);
			    		setButton = (Button) findViewById(R.id.FrameLayoutGpsButton);
			    		break;
			    	}
					
					mCurrentSelectedValues[mCurrentSelectedButtonsNumber - 1] = newValue;
					setButton.setText(catArray[newValue]);
				}
			}
		});
    }
    
    private void deselectAllButtons(){
    	for (int i = 0; i < mButtonResIds.length; i++) {
    		((Button)findViewById(mButtonResIds[i])).setSelected(false);
    	}
    }
    
    private void hideScroll(int number){
    	
    	if (number == 0) return;
    	
    	mWheelShowing = true;
    	
    	ViewAnimations.translate(((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)), 0, 0, 0, ((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)).getHeight(), WHEEL_SHOWING_TIME, 0, false, new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)).setVisibility(View.GONE);
				((FrameLayout)findViewById(R.id.FrameLayoutSemiLayer)).setVisibility(View.GONE);
				((WheelView)findViewById(R.id.WheelViewCategories)).setVisibility(View.GONE);
				mWheelDisplayed = false;
				mCurrentSelectedButtonsNumber = 0;
				mWheelShowing = false;
				deselectAllButtons();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
    	});
    }
    
    private void showScroll(int number) {
    	if (number == 0) return;
    	
    	setWheelList(number - 1);
    	
    	((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)).setVisibility(View.VISIBLE);
    	((WheelView)findViewById(R.id.WheelViewCategories)).setVisibility(View.VISIBLE);
    	
    	mWheelShowing = true;
		
    	ViewAnimations.translate(((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)), 0, 0, ((FrameLayout)findViewById(R.id.FrameLayoutWheelLayout)).getHeight(), 0, WHEEL_SHOWING_TIME, 0, false, new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				((FrameLayout)findViewById(R.id.FrameLayoutSemiLayer)).setVisibility(View.VISIBLE);
				mWheelDisplayed = true;
				mWheelShowing = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
    	});
    	
    	
    }
        
    private void setWheelList(int type) {
    	String catArray[] = null;
    	WheelView wheelView = (WheelView) findViewById(R.id.WheelViewCategories);
    	switch(type) {
    	case 0:
    		((TextView) findViewById(R.id.TextViewWheelTitle)).setText("Sports");
    		catArray = getResources().getStringArray(R.array.sports_cat_items);
    		break;
    	case 1:
    		((TextView) findViewById(R.id.TextViewWheelTitle)).setText("Hobbies");
    		catArray = getResources().getStringArray(R.array.hobbies_cat_items);
    		break;
    	case 2:
    		((TextView) findViewById(R.id.TextViewWheelTitle)).setText("Community");
    		catArray = getResources().getStringArray(R.array.community_cat_items);
    		break;
    	case 3:
    		((TextView) findViewById(R.id.TextViewWheelTitle)).setText("Location mode");
    		catArray = getResources().getStringArray(R.array.location_cat_items);
    		break;
    	}
    	wheelView.setViewAdapter(new ArrayWheelAdapter<String>(this, catArray));
    	wheelView.setCurrentItem(mCurrentSelectedValues[type]);
    }
}