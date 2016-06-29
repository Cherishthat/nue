package com.hxgy.nurexcute;


import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BarReceiver extends BroadcastReceiver{
	
	public static final String ACTION_BROADCASTRECEIVER ="action.broadcast.smartshell.data";	
	IBarInterface ibar=null;
	 private String barCode;
     
	public String getBarCode() {
			return barCode;
	}

	public void setBarCode(String barCode) {
			this.barCode = barCode;
	}
	
	public BarReceiver(IBarInterface ibar){
		this.ibar = ibar;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);  
	     
	    if (!mKeyguardManager.inKeyguardRestrictedInputMode()) { 
			if (ACTION_BROADCASTRECEIVER.equals(action)){ 
				barCode=intent.getStringExtra("smartshell_data");
				 if (barCode.substring(0, 2) == "SJ" || ((barCode.substring(0, 1) == "0") && (barCode.length() == 10))){
					 ibar.getPatient(barCode);
				 }
				
				
			}			
	    }
	}

}
