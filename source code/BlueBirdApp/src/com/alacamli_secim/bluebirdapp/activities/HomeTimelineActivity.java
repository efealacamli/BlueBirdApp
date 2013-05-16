package com.alacamli_secim.bluebirdapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.alacamli_secim.bluebirdapp.R;
import com.alacamli_secim.bluebirdapp.async.HTimelineTask;
import com.alacamli_secim.bluebirdapp.interfaces.HTlineListener;
import com.alacamli_secim.bluebirdapp.model.TweetModel;
import com.alacamli_secim.bluebirdapp.views.HomeTimelineView;


public class HomeTimelineActivity extends MainActivity{
	private SharedPreferences mSharedPreferences;
	private HomeTimelineView mView;
	private ArrayAdapter arrayAdapter;
	private TweetModel mModel;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Get the model
        mModel = new TweetModel();
        //Get the SharedPreferences
		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
		//Crate and execute AsyncTask
		HTimelineTask mTask = new HTimelineTask(mSharedPreferences, mModel);
		mTask.setEventHandler(mHmTimelineListener);
		mTask.execute();
		//Get the view and set it
        mView = (HomeTimelineView) View.inflate(this.getBaseContext(), R.layout.hometimeline, null);
        setContentView(mView);
		
	}
	
	private HTlineListener mHmTimelineListener = new HTlineListener() {
		
		@Override
		public void OnUpdateStatusStarted() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void OnUpdateStatusComplete() {
			// TODO Auto-generated method stub
			//Array adapter for ListView
			arrayAdapter = new ArrayAdapter(HomeTimelineActivity.this.getBaseContext(), android.R.layout.simple_list_item_1, mModel.getHomeTimelineTweets());
			mView.setAdapterForHomeTweets(arrayAdapter);
		}
	};
	
		
}
