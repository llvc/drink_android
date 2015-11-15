package com.alex.hotcourts;

import org.wsbd.library.LayoutController;
import org.wsbd.library.UIUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class GroupProfileBoardActivity extends Activity {

	/** Called when the activity is first created. */
	
	public static final String TAG = "GroupProfileBoardActivity";
	
	private boolean mFocusProcessed = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_profile_board);
        setClickListeners();
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
    }
    
    private void setClickListeners(){
    	((ImageButton) findViewById(R.id.ImageButtonProfileChats)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("returnKey", "Chats");
				setResult(RESULT_OK, data);
				finish();
			}
    	});
    	
    	((ImageButton) findViewById(R.id.ImageButtonFollowers)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent data = new Intent();
				data.putExtra("returnKey", "followers");
				setResult(RESULT_OK, data);
				finish();
			}
    	});
    }
    
    
}