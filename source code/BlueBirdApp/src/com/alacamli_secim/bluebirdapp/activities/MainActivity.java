package com.alacamli_secim.bluebirdapp.activities;

import java.io.FileOutputStream;
import com.alacamli_secim.bluebirdapp.R;
import com.alacamli_secim.bluebirdapp.Util.TwitterConstants;
import com.alacamli_secim.bluebirdapp.interfaces.LoginListener;
import com.alacamli_secim.bluebirdapp.interfaces.SelectionListener;
import com.alacamli_secim.bluebirdapp.model.TweetModel;
import com.alacamli_secim.bluebirdapp.views.LoginView;
import com.alacamli_secim.bluebirdapp.views.SelectionView;
import com.alacamli_secim.bluebirdapp.helpers.FileHelper;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
 
public class MainActivity extends BaseActivity {
	
	private LoginView mView;
	private TweetModel mModel;
	private SelectionView mView1;
	private SharedPreferences mSharedPreferences; 
    
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    FileOutputStream fileout;
    String filename = "userdetails";
    
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
        
    // Internet Connection detector
    private ConnectionDetector cd;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
        
    //Login to twitter function
    private LoginListener mEventHandler = new LoginListener(){
    @Override	
    
    public void showLoginClicked() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            //Attach the Consumer key and consumer secret key
            builder.setOAuthConsumerKey(TwitterConstants.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TwitterConstants.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
            	//handle user login
                requestToken = twitter.getOAuthRequestToken(TwitterConstants.TWITTER_CALLBACK_URL);
                MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
        	//If user already logged in, get the username-name-profile from the file
        	TweetModel mMdl = FileHelper.readFromFile(MainActivity.this, filename);
        	//If model is not null, skip the login screen
    		if(mMdl != null){
    			mModel = mMdl;
                mView1 = (SelectionView) View.inflate(MainActivity.this.getBaseContext(), R.layout.selection, null);
                mView1.setEventHandler(mEventHandler1);
                setContentView(mView1);
                mView1.setName(mModel.getName());
                mView1.setUsername(mModel.getUsername());
                mView1.setUserDescription(mModel.getDescription());
            	// user already logged into twitter
                Toast.makeText(getApplicationContext(), "Already Logged into twitter", Toast.LENGTH_LONG).show();
    		}
        }
    }
    };
    
    
    private SelectionListener mEventHandler1 = new SelectionListener(){
        @Override	
        
        public void showTweetClicked() {
        	 Intent u = new Intent(MainActivity.this.getBaseContext(), UpdateStatusActivity.class);
        	 startActivity(u);
        }

        public void showUserTimelineClicked() {
        	Intent u = new Intent(MainActivity.this.getBaseContext(), UserTimelineActivity.class);
        	startActivity(u);
 
        }
        
        public void showHomeTimelineClicked() {
        	Intent u = new Intent(MainActivity.this.getBaseContext(), HomeTimelineActivity.class);
        	startActivity(u);
 
        }
        
        public void showLogoutClicked() {
            
            // Clear the shared preferences
            Editor e = mSharedPreferences.edit();
            e.remove(TwitterConstants.PREF_KEY_OAUTH_TOKEN);
            e.remove(TwitterConstants.PREF_KEY_OAUTH_SECRET);
            e.remove(TwitterConstants.PREF_KEY_TWITTER_LOGIN);
            e.commit();
            Intent u = new Intent(MainActivity.this.getBaseContext(), MainActivity.class);
          	 startActivity(u);
     
        }

		
    };
        
    
    
    public class AlertDialogManager {
        /**
         * Function to display simple Alert Dialog
         * @param context - application context
         * @param title - alert dialog title
         * @param message - alert message
         * @param status - success/failure (used to set icon)
         *               - pass null if you don't want icon
         * */
        public void showAlertDialog(Context context, String title, String message,
                Boolean status) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
     
            // Setting Dialog Title
            alertDialog.setTitle(title);
     
            // Setting Dialog Message
            alertDialog.setMessage(message);
     
            if(status != null)
                // Setting alert dialog icon
                //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
     
            // Setting OK Button
            alertDialog.setPositiveButton("OK", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	dialog.dismiss();
                }
            });
     
            // Showing Alert Message
            alertDialog.create().show();
        }
    }
    
    public class ConnectionDetector {
    	  
        private Context _context;
      
        public ConnectionDetector(Context context){
            this._context = context;
        }
      
        /**
         * Checking for all possible internet providers
         * **/
        public boolean isConnectingToInternet(){
            ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
              if (connectivity != null)
              {
                  NetworkInfo[] info = connectivity.getAllNetworkInfo();
                  if (info != null)
                      for (int i = 0; i < info.length; i++)
                          if (info[i].getState() == NetworkInfo.State.CONNECTED)
                          {
                              return true;
                          }
      
              }
              return false;
        }
    }
    
    
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the model
        TweetModel mModel = new TweetModel();
        //Create the view
        mView = (LoginView) View.inflate(this.getBaseContext(), R.layout.activity_main, null);
        mView.setEventHandler(mEventHandler);
        setContentView(mView);
        
        //AsyncTask handling for different SDK
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        

        cd = new ConnectionDetector(getApplicationContext());
 
        //Check if Internet exist
        if (!cd.isConnectingToInternet()) {
            //Connection is not available
            alert.showAlertDialog(MainActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            return;
        }
         
        //Check if twitter keys are set
        if(TwitterConstants.TWITTER_CONSUMER_KEY.trim().length() == 0 || TwitterConstants.TWITTER_CONSUMER_SECRET.trim().length() == 0){
        	//Connection is not available
            alert.showAlertDialog(MainActivity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
            return;
        }
 
       
 
        // Getting Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
       
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TwitterConstants.TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri.getQueryParameter(TwitterConstants.URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(TwitterConstants.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(TwitterConstants.PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(TwitterConstants.PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    // Hide the view that has login button and bring the other view
                    mView1 = (SelectionView) View.inflate(this.getBaseContext(), R.layout.selection, null);
                    mView1.setEventHandler(mEventHandler1);
                    setContentView(mView1);
                    
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getScreenName();
                    String name = user.getName();
                    String description = user.getDescription();
                    
                    // Setting username-name-profile description in TweetModel
                    mModel.setUsername(username);
                    mModel.setName(name);
                    mModel.setDescription(description);
                    
                    //Set the TweetModel and write it to the file
    				FileHelper.writeToFile(MainActivity.this, mModel, filename);	
                    
    				//Set the username-name-profile description in view
                    mView1.setName(mModel.getName());
                    mView1.setUsername(mModel.getUsername());
                    mView1.setUserDescription(mModel.getDescription());

                   
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
        
        
        else{
        	//If user already logged in, get the username-name-profile from the file
        	TweetModel mMdl = FileHelper.readFromFile(MainActivity.this, filename);
    		if(mMdl == null){
    				//TODO DO SOMETHING when the file is empty
    		}
    		else{
    			//If user already logged in, get the model and set it view
    			mModel = mMdl;
                mView1 = (SelectionView) View.inflate(MainActivity.this.getBaseContext(), R.layout.selection, null);
                mView1.setEventHandler(mEventHandler1);
                setContentView(mView1);
                mView1.setName(mModel.getName());
                mView1.setUsername(mModel.getUsername());
                mView1.setUserDescription(mModel.getDescription());
            	// user already logged into twitter
                //Toast.makeText(getApplicationContext(), "Already Logged into twitter", Toast.LENGTH_LONG).show();
    		}  	
        }       
    }
 
   
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(TwitterConstants.PREF_KEY_TWITTER_LOGIN, false);
    }
 
    protected void onResume() {
        super.onResume();
    }
 
}