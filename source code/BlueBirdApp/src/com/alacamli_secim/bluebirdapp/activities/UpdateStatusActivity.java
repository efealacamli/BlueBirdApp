package com.alacamli_secim.bluebirdapp.activities;


import android.app.ProgressDialog;

import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.alacamli_secim.bluebirdapp.R;

import com.alacamli_secim.bluebirdapp.async.TwitterStatusTask;
import com.alacamli_secim.bluebirdapp.interfaces.OnUpdateStatusListener;
import com.alacamli_secim.bluebirdapp.interfaces.TweetListener;

import com.alacamli_secim.bluebirdapp.views.TweetView;

public class UpdateStatusActivity extends MainActivity {
	ProgressDialog pDialog;
	private TweetView mView;
	private SharedPreferences mSharedPreferences;
	

private OnUpdateStatusListener mAuthListener = new OnUpdateStatusListener(){
		
		@Override
		public void OnUpdateStatusStarted(){
		 	//ProgressDialog while sending tweet
			pDialog = new ProgressDialog(UpdateStatusActivity.this);
	        pDialog.setMessage("Updating to twitter...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
		}
		
		@Override
		public void OnUpdateStatusComplete(){
			//Close the dialog when it is completed
	        pDialog.dismiss();
	        runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	                Toast.makeText(getApplicationContext(), "Status tweeted successfully", Toast.LENGTH_SHORT).show();
	                //Clear the EditText field
	                mView.setUpdate("");
	            }
	        });
		}
	};
	
	
	 private TweetListener mEventHandler = new TweetListener(){
	        @Override	
	        public void showUpdateStatusClicked(String status) {
	        	//Check if the input text is blank
	            if (status.trim().length() > 0) {
	                //Update Twitter status
	            	TwitterStatusTask task = new TwitterStatusTask(status, mSharedPreferences ) ;
	            	task.setEventHandler(mAuthListener);
	            	task.execute();
	            } else {
	                //Show toast when input text is blank
	                Toast.makeText(getApplicationContext(),
	                        "Please enter status message", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }		
	    };
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mView = (TweetView) View.inflate(this.getBaseContext(), R.layout.post_tweet, null);
            mView.setEventHandler(mEventHandler);
            mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);        
            setContentView(mView);

	    }
}
