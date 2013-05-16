package com.alacamli_secim.bluebirdapp.views;

import com.alacamli_secim.bluebirdapp.R;



import com.alacamli_secim.bluebirdapp.interfaces.TweetListener;



import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TweetView extends LinearLayout {
	
    Button btnUpdateStatus;
    EditText txtUpdate;
    TextView lblUpdate;
    private TweetListener mEventHandler;
    
   
 
    public void setUpdate(String Update) {
		txtUpdate.setText(Update);
	}

	public TweetView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setEventHandler(TweetListener listener){
		mEventHandler = listener;
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
    	 
         @Override
         public void onClick(View v) {
             // Call update status function
             // Get the status from EditText
             String status = txtUpdate.getText().toString();

             if (mEventHandler==null) {
 				return;
             }
             switch (v.getId()){
	             case R.id.btnUpdateStatus:
	 				mEventHandler.showUpdateStatusClicked(status);
	 				break;
 			}
         }
     };
     
    
	
	@Override
	protected void onFinishInflate(){
		super.onFinishInflate();
        
		//Find update button
        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        btnUpdateStatus.setOnClickListener(mOnClickListener);
        
        txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        lblUpdate = (TextView) findViewById(R.id.lblUpdate);
        
        
 
	}

}
