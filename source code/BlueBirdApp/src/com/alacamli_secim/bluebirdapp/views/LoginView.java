package com.alacamli_secim.bluebirdapp.views;

import com.alacamli_secim.bluebirdapp.interfaces.LoginListener;
import com.alacamli_secim.bluebirdapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class LoginView extends LinearLayout {
	// Login button
    private Button btnLoginTwitter;
    private LoginListener mEventHandler;

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setEventHandler(LoginListener listener){
		mEventHandler = listener;
	}
	
	
	@Override
	protected void onFinishInflate(){
		super.onFinishInflate();
		
		//Find the login button
		btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
		btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
	
		
		@Override
		public void onClick(View arg0) {
	
			if (mEventHandler!=null) {
				mEventHandler.showLoginClicked();
				}
			}
	});
	}

}
