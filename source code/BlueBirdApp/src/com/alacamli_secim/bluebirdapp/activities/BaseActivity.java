package com.alacamli_secim.bluebirdapp.activities;

import com.alacamli_secim.bluebirdapp.interfaces.IBaseListener;

import android.app.Activity;
import android.os.Bundle;



public class BaseActivity extends Activity {

	protected IBaseListener mExampleListener = new IBaseListener() {
		@Override
		public void exitExample() {
			BaseActivity.this.finish();
		}
	};
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Log.i("onCreate()", "called");
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//Log.i("onStart()", "called");
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		
		
		//Log.i("onResume()", "called");
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Another activity is taking focus (this activity is
		// about to be "paused").
		
		
		//Log.i("onPause", "called");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The activity is about to be
		// destroyed.
		//Log.i("onDestroy", "called");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// The activity is no longer
		// visible (it is now "stopped")
		//Log.i("onStop", "called");
	}
}


