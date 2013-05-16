package com.alacamli_secim.bluebirdapp.async;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import com.alacamli_secim.bluebirdapp.Util.TwitterConstants;
import com.alacamli_secim.bluebirdapp.interfaces.HTlineListener;
import com.alacamli_secim.bluebirdapp.model.TweetModel;

import android.content.SharedPreferences;
import android.os.AsyncTask;

public class HTimelineTask extends AsyncTask<String, String, String> {

	private HTlineListener mEventHandler;
    private SharedPreferences mSharedPreferences;
    private TweetModel mModel;
	
    public HTimelineTask(SharedPreferences shrdPrfnc, TweetModel model){
		mSharedPreferences = shrdPrfnc;
		mModel = model;
    }
    
    public void setEventHandler(HTlineListener mListener){
    	mEventHandler = mListener;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mEventHandler!=null){
			mEventHandler.OnUpdateStatusStarted();
		}
    }
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
			
        try {
        	//Get Twitter credentials
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TwitterConstants.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TwitterConstants.TWITTER_CONSUMER_SECRET);
             
            // Access Token 
            String access_token = mSharedPreferences.getString(TwitterConstants.PREF_KEY_OAUTH_TOKEN, "");
            // Access Token Secret
            String access_token_secret = mSharedPreferences.getString(TwitterConstants.PREF_KEY_OAUTH_SECRET, "");
             
            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
            //Get the home timeline from Twitter
            List<twitter4j.Status> statuses = twitter.getHomeTimeline();
            //Set status and send it to model
            mModel.setHomeStatus(statuses);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
        }

		return null;
	}
	
	
   protected void onPostExecute(String file_url) {
	   
    	if(mEventHandler!=null){
				mEventHandler.OnUpdateStatusComplete();
			}
	    
   }
	

}
