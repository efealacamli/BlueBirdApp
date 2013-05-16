package com.alacamli_secim.bluebirdapp.views;

import com.alacamli_secim.bluebirdapp.R;
import com.alacamli_secim.bluebirdapp.interfaces.SelectionListener;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import android.widget.LinearLayout;


public class SelectionView extends LinearLayout  {
	public static final String TAG = "NameMeSomethingUnique";
	
	TextView txtUName;
	TextView txtUsername;
	TextView txtDescription;
	Button btnTweet;
	Button btnUsertimeline;
	Button btnHometimeline;
	Button btnLogoutTwitter;
	
	private SelectionListener mEventHandler;

	public SelectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setEventHandler(SelectionListener listener){
		mEventHandler = listener;
	}
	
	public void setName(String name){
		txtUName.setText(name);
	}
	
	public void setUsername(String username){
		txtUsername.setText("@" + username);
	}
	
	public void setUserDescription(String desc){
		txtDescription.setText(desc);
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
   	 
        @Override
        public void onClick(View v) {
           
            if (mEventHandler==null) {
				return;
			}
            switch (v.getId()){ 
				case R.id.tweet_button:
					mEventHandler.showTweetClicked();
					break;
					
				case R.id.usertimeline_button:
					mEventHandler.showUserTimelineClicked();
					break;
					
				case R.id.hometimeline_button:
					mEventHandler.showHomeTimelineClicked();
					break;
					
				case R.id.btnLogoutTwitter:
	 				mEventHandler.showLogoutClicked();
	 				break;
			}
        }
    };
    
    @Override
	protected void onFinishInflate(){
		super.onFinishInflate();
		
		// All UI elements
        try{
        	
        //Find the textfield for name
        txtUName = (TextView) findViewById(R.id.username);
        //Find the textfield for username
        txtUsername = (TextView) findViewById(R.id.userscreenname);
        //Find the textfield for profile description
        txtDescription = (TextView) findViewById(R.id.userdescription);
        	
        //Find the tweet button
		btnTweet = (Button) findViewById(R.id.tweet_button);
		btnTweet.setOnClickListener(mOnClickListener);
        
		//Find the usertimeline button
		btnUsertimeline = (Button) findViewById(R.id.usertimeline_button);
		btnUsertimeline.setOnClickListener(mOnClickListener);
        
		//Find the hometimeline button
		btnHometimeline = (Button) findViewById(R.id.hometimeline_button);
		btnHometimeline.setOnClickListener(mOnClickListener);
		
		//Find the logout button
		btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        btnLogoutTwitter.setOnClickListener(mOnClickListener);
        
        }
        catch (Throwable t) {
            Log.e(TAG, t.getMessage(), t);
        }
	}

}
