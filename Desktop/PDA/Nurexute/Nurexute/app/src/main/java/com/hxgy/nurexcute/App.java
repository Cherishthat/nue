package com.hxgy.nurexcute;

import com.smartshell.bluetooth.BluetoothConnectService;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class App extends Application {
	public static Context appContext;
	public static BluetoothConnectService btService;
	public static boolean iselect;
    public static BluetoothAdapter mBluetoothAdapter = null;
	@Override
	public void onCreate() {
		super.onCreate();
		
		appContext = getApplicationContext();
		btService = new BluetoothConnectService(this);
		iselect=false;
	}
	
	
	
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
		
	}
	
//	public void changeFont(ViewGroup v){
//		for (int i = 0; i < v.getChildCount(); i++) {
//			View item=v.getChildAt(i);  
//			if ( item instanceof ViewGroup){  
//				changeFont((ViewGroup)item);
//			}
//			if(item instanceof TextView){
//				((TextView) item).setTypeface(tf);
//			}
//			if(item instanceof EditText){
//				((EditText) item).setTypeface(tf);
//			}
//		}
//	}

}
