package com.alacamli_secim.bluebirdapp.async;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.alacamli_secim.bluebirdapp.Util.TwitterConstants;
import com.alacamli_secim.bluebirdapp.interfaces.OnUpdateStatusListener;



public class TwitterStatusTask extends AsyncTask<String, String, String> {
	
    private SharedPreferences mSharedPreferences;
    
	
	private OnUpdateStatusListener mEventHandler;
	private String mStatus;
	
	public TwitterStatusTask(String status,SharedPreferences SharedPreferences) {
		mStatus = status;
		mSharedPreferences = SharedPreferences;
	}

	public void setEventHandler(OnUpdateStatusListener listener){
		mEventHandler = listener;
	}
	
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        if(mEventHandler!=null){
			mEventHandler.OnUpdateStatusStarted();
		}
       
    }

    /**
     * getting Places JSON
     * */
    protected String doInBackground(String... args) {
        
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TwitterConstants.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TwitterConstants.TWITTER_CONSUMER_SECRET);
             
            // Access Token 
            String access_token = mSharedPreferences.getString(TwitterConstants.PREF_KEY_OAUTH_TOKEN, "");
            // Access Token Secret
            String access_token_secret = mSharedPreferences.getString(TwitterConstants.PREF_KEY_OAUTH_SECRET, "");
             
            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
             
            // Update status
            twitter4j.Status response = twitter.updateStatus(mStatus);
             
            Log.d("Status", "> " + response.getText());
        } catch (TwitterException e) {
            // Error in updating status
            Log.d("Twitter Update Error", e.getMessage());
        }
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog and show
     * the data in UI Always use runOnUiThread(new Runnable()) to update UI
     * from background thread, otherwise you will get error
     * **/
    protected void onPostExecute(String file_url) {
    	
    	if(mEventHandler!=null){
			mEventHandler.OnUpdateStatusComplete();
			
		}
        
    }

}
