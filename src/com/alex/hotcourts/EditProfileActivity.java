package com.alex.hotcourts;


import org.wsbd.library.LayoutController;
import org.wsbd.library.UIUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class EditProfileActivity extends Activity{
	
	public static final String TAG = "EditProfile";
	
	private boolean mFocusProcessed = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        setClickListeners();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	super.onWindowFocusChanged(hasFocus);
    	
    	if (hasFocus && !mFocusProcessed) {
    		mFocusProcessed = true;
    		initLayouts();
    		
    		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    		imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.EditTextProfileName)).getWindowToken(), 0);
    	}
    }
    
    private void initLayouts(){
   	
    	FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
		int screenWidth = container.getWidth();
		
		float rate = screenWidth / 648.0f * UIUtils.convertPixelsToDp(1, this);
		LayoutController.fitToScreen(this, container, rate);
		
		FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
		container.setLayoutParams(param);
    }
    
    private void setClickListeners(){
    	((ImageButton) findViewById(R.id.ImageButtonCancel)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
}