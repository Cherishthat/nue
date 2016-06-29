package com.smartshell.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

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
        Log.d("APP", "App.iselect oncreste");
	}

}
