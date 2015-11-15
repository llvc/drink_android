package com.alex.hotcourts;

import java.util.ArrayList;

import org.wsbd.library.LayoutController;
import org.wsbd.library.UIUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainBoardActivity extends Activity{
	
	public static final String TAG = "MainBoardActivity";
	
	private final static int PROFILE_ACTIVTY_CODE = 100;
	
	private final static int ST_TOTAL_SHEARCH_RESULT = 0;
	private final static int ST_MY_FAVORITES		 = 1;
	private final static int ST_MY_CHATS			 = 2;
	private final static int ST_FOLLOWERS			 = 3;
	private final static int ST_CHATTING			 = 4;
	private final static int ST_MY_CHAT_EDITTING	 = 5;
	private final static int ST_GROUPS				 = 6;
	private final static int ST_TALK_BOARD			 = 7;
	private final static int ST_FAVORITE_NEWS_FEED	 = 8;
	private final static int ST_NEWS_FEED			 = 9;
	
	private boolean mFocusProcessed = false;
	
	private GridviewAdapter 	mAdapter;  
    private ArrayList<String> 	listCountry;  
    private ArrayList<Integer> 	listFlag;

    private FrameLayout			mFrameLayoutMainContentContainer;
    private GridView 			mGridView;
    private LinearLayout		mLinearLayout;
    private ScrollView		mPostLayout;
    private ScrollView			mScrollView;
    
    private final int 			mCatButtonsIds[] = {R.id.ImageButtonCatFav, R.id.ImageButtonCatChats, R.id.ImageButtonCatProfile};
    private int					mCurrentSelectedCatItemNumber = -1;
    
    private int					mCurrentShowState;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board);
        mFrameLayoutMainContentContainer = (FrameLayout)findViewById(R.id.FrameLayoutMainContentConainer);
        mGridView = (GridView) findViewById(R.id.gridview);
        mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayoutChatList);
        mPostLayout = (ScrollView) findViewById(R.id.ScrollViewPostBoard);
        mScrollView = (ScrollView) findViewById(R.id.ScrollViewChatList);
        
        deselectAll();
        setClickListeners();
        setVisibles();
        
        mCurrentShowState = ST_TOTAL_SHEARCH_RESULT;
    }
	
	 public void prepareList()  
	 {  
          listCountry = new ArrayList<String>();  
          listFlag = new ArrayList<Integer>();
          
          for (int i = 0; i < 30; i++) {
        	  listCountry.add("James");
        	  listFlag.add(R.drawable.main_board_photo_sample);
          }
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
    	initGrid();
    	
    	initChatBoard();
    	
    	FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
		int screenWidth = container.getWidth();
		
		float rate = screenWidth / 648.0f * UIUtils.convertPixelsToDp(1, this);
		LayoutController.fitToScreen(this, container, rate);
		
		FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
		container.setLayoutParams(param);
    }
    
    private void initGrid(){
    	 prepareList();
    	 
         mAdapter = new GridviewAdapter(this,listCountry, listFlag);
         
         FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
 		 int screenWidth = container.getWidth();
 		
         float rate = screenWidth / 648.0f;
         
         mAdapter.setRate(rate);
         
 
         mGridView = (GridView) findViewById(R.id.gridview);
         
         mGridView.setAdapter(mAdapter); 
         
         mGridView.setColumnWidth((int)(122 * rate));
         mGridView.setHorizontalSpacing((int)(5 * rate));
         mGridView.setVerticalSpacing((int)(2 * rate));
         
         mGridView.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
 					long arg3) {
 				// TODO Auto-generated method stub
 				showProfileBoard();
 			}
 		});
    }
    
    private void initChatBoard(){
    	
    }
    
    private void setClickListeners(){
    	((ImageButton) findViewById(R.id.ImageButtonNewSearch)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}
    	});
    	
    	for (int i = 0; i < mCatButtonsIds.length; i++) {
    		final int number = i;
    		((ImageButton) findViewById(mCatButtonsIds[i])).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					if (mCurrentSelectedCatItemNumber != number) {
						catProcess(number);
						mCurrentSelectedCatItemNumber = number;
					}
				}
    		});
    	}
    	
    	((ImageButton)findViewById(R.id.ImageButtonGroups)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showGroups();
			}
		});
    	
    	((ImageButton)findViewById(R.id.ImageButtonTalkBoard)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTalkBoard();
			}
		});
    	
    	((ImageButton)findViewById(R.id.ImageButtonLeft)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processLeftButton();
			}
		});
    	
    	((ImageButton)findViewById(R.id.ImageButtonRight)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processRightButton();
			}
		});
    }
    
    private void setVisibles(){
    	mFrameLayoutMainContentContainer.removeView(mGridView);
    	mFrameLayoutMainContentContainer.removeView(mLinearLayout);
    	mFrameLayoutMainContentContainer.removeView(mPostLayout);
    	mFrameLayoutMainContentContainer.addView(mGridView);
    }
    
    private void catProcess(int number){
    	switch(number) {
    	case 0:
    		showFavoriteList();
    		break;
    	case 1:
    		showChatsList();
    		break;
    	case 2:
    		showProfileBoard();
    		break;
    	}
    	deselectAll();
    	((ImageButton)findViewById(mCatButtonsIds[number])).setSelected(true);
    }
    
    private void deselectAll(){
    	for (int i = 0; i < mCatButtonsIds.length; i++) {
        	((ImageButton)findViewById(mCatButtonsIds[i])).setSelected(false);
    	}
    	mCurrentSelectedCatItemNumber = -1;
    }
    
    private void showFavoriteList(){
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mGridView);
         listCountry = new ArrayList<String>();
         listFlag = new ArrayList<Integer>();
         
         for (int i = 0; i < 5; i++) {
       	  	listCountry.add("James");
       	  	listFlag.add(R.drawable.main_board_photo_sample);
         }
         
         mAdapter = new GridviewAdapter(this,listCountry, listFlag);
         FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
 		 int screenWidth = container.getWidth();
         float rate = screenWidth / 648.0f;
         mAdapter.setRate(rate);
         mGridView = (GridView) findViewById(R.id.gridview);
         mGridView.setAdapter(mAdapter);
         
         mGridView.setColumnWidth((int)(122 * rate));
         mGridView.setHorizontalSpacing((int)(5 * rate));
         mGridView.setVerticalSpacing((int)(2 * rate));
         
         mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				showProfileBoard();
			}
		});
         
         ((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Favorite");
         
         mCurrentShowState = ST_MY_FAVORITES;
         
         ((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.VISIBLE);
         ((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
         
         ((ImageButton) findViewById(R.id.ImageButtonLeft)).setBackgroundResource(R.drawable.btn_selector_edit_button);
         ((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_news_button);
    }
    
    private void showChatsList(){
    	
    	mLinearLayout.removeAllViews();
    	
    	for (int i = 0; i < 50; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.chat_list_row, null);
			mLinearLayout.addView(content);
			
			(content.findViewById(R.id.ImageViewProfilePhoto)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(MainBoardActivity.this, ProfileBoardActivity.class), PROFILE_ACTIVTY_CODE);
				}
			});
			
			content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showRealChatList();
				}
			});
    	}
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mScrollView);
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.GONE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_edit_button);
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Chats");
    	
    	mCurrentShowState = ST_MY_CHATS;
    }
    
    private void showProfileBoard(){
    	Intent intent = new Intent(this, EditProfileActivity.class);
    	startActivity(intent);
    }
    
    private void showGroups(){
    	deselectAll();
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mGridView);
         listCountry = new ArrayList<String>();  
         listFlag = new ArrayList<Integer>();
         
         for (int i = 0; i < 30; i++) {
       	  	listCountry.add("James");
       	  	listFlag.add(R.drawable.main_board_photo_sample);
         }
         
         GridviewAdapterGroup adapter = new GridviewAdapterGroup(this,listCountry, listFlag);
         FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
 		 int screenWidth = container.getWidth();
         float rate = screenWidth / 648.0f;
         adapter.setRate(rate);
         mGridView = (GridView) findViewById(R.id.gridview);
         mGridView.setAdapter(adapter);
         
         mGridView.setColumnWidth((int)(140 * rate));
         mGridView.setHorizontalSpacing((int)(20 * rate));
         mGridView.setVerticalSpacing((int)(20 * rate));
         
         mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				showGroupProfile();
			}
		 });
         
         ((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Tennis Groups");
         
         mCurrentShowState = ST_GROUPS;
         
         ((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.VISIBLE);
         ((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
         
         ((ImageButton) findViewById(R.id.ImageButtonLeft)).setBackgroundResource(R.drawable.btn_selector_refresh_button);
         ((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_news_button);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == PROFILE_ACTIVTY_CODE) {
    		Log.v(TAG, "start activity result from the profile board activity");
    		String result = data.getExtras().getString("returnKey");
    		
    		if (result.equals("Chats")) {
    			deselectAll();
    			((ImageButton)findViewById(mCatButtonsIds[1])).setSelected(true);
    			showChatsList();
    		} else if (result.equals("followers")) {
    			showFollowersList();
    		}
    	}
    }
    
    private void showFollowersList(){
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mGridView);
         listCountry = new ArrayList<String>();
         listFlag = new ArrayList<Integer>();
         
         for (int i = 0; i < 30; i++) {
       	  	listCountry.add("James");
       	  	listFlag.add(R.drawable.main_board_photo_sample);
         }
         
         mAdapter = new GridviewAdapter(this,listCountry, listFlag);
         FrameLayout container = (FrameLayout) findViewById(R.id.FrameLayoutContainer);
 		 int screenWidth = container.getWidth();
         float rate = screenWidth / 648.0f;
         mAdapter.setRate(rate);
         mGridView = (GridView) findViewById(R.id.gridview);
         mGridView.setAdapter(mAdapter);
         
         mGridView.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
 				showProfileBoard();
 			}
 		 });
         
         ((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Followers");
         
         deselectAll();
         
         mCurrentShowState = ST_FOLLOWERS;
    }
    
    private void showRealChatList(){
    	
    	deselectAll();
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Chatting with Soah");

    	mLinearLayout.removeAllViews();
    	
    	for (int i = 0; i < 50; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.chat_room_row, null);
			mLinearLayout.addView(content);
			
			(content.findViewById(R.id.ImageViewProfilePhoto)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(MainBoardActivity.this, ProfileBoardActivity.class), PROFILE_ACTIVTY_CODE);
				}
			});
    	}
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mScrollView);
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.GONE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.GONE);
    	
    	mCurrentShowState = ST_CHATTING; 
    }
    
    private void showTalkBoard(){
    	
    	mLinearLayout.removeAllViews();
    	
    	for (int i = 0; i < 50; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.talk_board_row, null);
			mLinearLayout.addView(content);
			
			(content.findViewById(R.id.ImageViewProfilePhoto)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(MainBoardActivity.this, ProfileBoardActivity.class), PROFILE_ACTIVTY_CODE);
				}
			});
			
			content.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showRealChatList();
				}
			});
    	}
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mScrollView);
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setBackgroundResource(R.drawable.btn_selector_refresh_button);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_news_button);
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Talk Board");
    	
    	mCurrentShowState = ST_TALK_BOARD;
    }
    
    private void processLeftButton(){
    	switch (mCurrentShowState) {
    	case ST_MY_FAVORITES:
    		break;
    	}
    }
    
    private void processRightButton(){
    	switch (mCurrentShowState) {
    	case ST_MY_FAVORITES:
    		showFavoriteNewsFeed();
    		break;
    	case ST_FAVORITE_NEWS_FEED:
    		showPostBoard();
    		break;
    	case ST_TOTAL_SHEARCH_RESULT:
    		shoTotalNewsFeed();
    		break;
    	case ST_GROUPS:
    		createNewGroup();
    		break;
    	}
    }
    
    private void showFavoriteNewsFeed() {
    	mLinearLayout.removeAllViews();
    	
    	for (int i = 0; i < 50; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.news_feed_row, null);
			mLinearLayout.addView(content);
			
			(content.findViewById(R.id.ImageViewProfilePhoto)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(MainBoardActivity.this, ProfileBoardActivity.class), PROFILE_ACTIVTY_CODE);
				}
			});
			
			content.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showRealChatList();
				}
			});
    	}
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mScrollView);
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setBackgroundResource(R.drawable.btn_selector_refresh_button);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_post_button);
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Favorite's News Feed");
    	
    	mCurrentShowState = ST_FAVORITE_NEWS_FEED;
    }
    
    private void shoTotalNewsFeed() {
    	mLinearLayout.removeAllViews();
    	
    	for (int i = 0; i < 50; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.news_feed_row, null);
			mLinearLayout.addView(content);
			
			(content.findViewById(R.id.ImageViewProfilePhoto)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(MainBoardActivity.this, ProfileBoardActivity.class), PROFILE_ACTIVTY_CODE);
				}
			});
			
			content.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showRealChatList();
				}
			});
    	}
    	
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mScrollView);
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.VISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setBackgroundResource(R.drawable.btn_selector_refresh_button);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setBackgroundResource(R.drawable.btn_selector_post_button);
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("Total News Feed");
    	
    	mCurrentShowState = ST_FAVORITE_NEWS_FEED;
    }
    
    private void showGroupProfile(){
    	startActivity(new Intent(this, GroupProfileBoardActivity.class));
    }
    
    private void showPostBoard(){
    	mFrameLayoutMainContentContainer.removeAllViews();
    	mFrameLayoutMainContentContainer.addView(mPostLayout);
    	
    	LinearLayout postLayout = (LinearLayout) findViewById(R.id.LinearLayoutPostList);
    	
    	postLayout.removeAllViews();
    	
    	for (int i = 0; i < 6; i++) {
    		LayoutInflater inflator = getLayoutInflater();
			LinearLayout content = (LinearLayout)inflator.inflate(R.layout.news_feed_list_row, null);
			postLayout.addView(content);
    	}
    	
    	mCurrentShowState = ST_NEWS_FEED;
    	
    	((ImageButton) findViewById(R.id.ImageButtonLeft)).setVisibility(View.INVISIBLE);
    	((ImageButton) findViewById(R.id.ImageButtonRight)).setVisibility(View.INVISIBLE);
    	
    	((TextView) findViewById(R.id.TextViewTitleBarLabel)).setText("My News Feed");
    	
    	deselectAll();
    }
    
    private void createNewGroup(){
    	startActivity(new Intent(this, CreateGroupActivity.class));
    }
}
