package com.alacamli_secim.bluebirdapp.views;

import com.alacamli_secim.bluebirdapp.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HomeTimelineView extends LinearLayout{
	
	TextView hTimelineTweetsText;
	ListView hTweetsList;
	
	public HomeTimelineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setAdapterForHomeTweets(ArrayAdapter arAdptr){
		//Set the adapter for ListView
		hTweetsList.setAdapter(arAdptr);
	}
	
	protected void onFinishInflate(){
		super.onFinishInflate();
		//Find the ListView
		hTweetsList = (ListView) findViewById(R.id.homeTweetsList);
	}
}
