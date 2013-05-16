package com.alacamli_secim.bluebirdapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import com.alacamli_secim.bluebirdapp.R;
import com.alacamli_secim.bluebirdapp.async.UTimelineTask;
import com.alacamli_secim.bluebirdapp.interfaces.UTlineListener;
import com.alacamli_secim.bluebirdapp.model.TweetModel;
import com.alacamli_secim.bluebirdapp.views.UserTimelineView;

public class UserTimelineActivity extends MainActivity {

	private SharedPreferences mSharedPreferences;
	private UserTimelineView mView;
	private ArrayAdapter arrayAdapter;
	private	TweetModel mModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Get the model
        mModel = new TweetModel();
        //Get the SharedPreferences
		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);	
		//Crate and execute AsyncTask
		UTimelineTask mTask = new UTimelineTask(mSharedPreferences, mModel);
		mTask.setEventHandler(mUsrTimelineListener);
		mTask.execute();
		//Get the view and set it
        mView = (UserTimelineView) View.inflate(this.getBaseContext(), R.layout.usertimeline, null);
        setContentView(mView);

	}
	
	private UTlineListener mUsrTimelineListener = new UTlineListener() {
		
		@Override
		public void OnUpdateStatusStarted() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void OnUpdateStatusComplete() {
			//Array adapter for ListView
			arrayAdapter = new ArrayAdapter(UserTimelineActivity.this.getBaseContext(), android.R.layout.simple_list_item_1, mModel.getUserTimelineTweets());
			mView.setAdapterForUserTweets(arrayAdapter);
			
		}
	};
	
	
}


