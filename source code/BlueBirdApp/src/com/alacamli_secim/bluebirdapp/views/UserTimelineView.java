package com.alacamli_secim.bluebirdapp.views;

import com.alacamli_secim.bluebirdapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserTimelineView extends LinearLayout{
	
	TextView uTimelineTweetsText;
	ListView uTweetsList;

	public UserTimelineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setAdapterForUserTweets(ArrayAdapter arAdptr){
		//Set the adapter for ListView
		uTweetsList.setAdapter(arAdptr);
		
	}

	protected void onFinishInflate(){
		super.onFinishInflate();
		//Find the ListView
		
		uTweetsList = (ListView) findViewById(R.id.userTweetsList);
		
	}
}
