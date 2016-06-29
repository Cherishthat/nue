package com.smartshell.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
 
public class BluetoothService extends Service {
	private static final String TAG = "BluetoothService";
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private static final String ACTION_SERVER_OFF="com.smartshell.bluetooth.serveroff";
	
	/**
	 * ��Ҫ�����ӵ�������ַ
	 */
	public static final String EXTRA_DEVICE_ADDRESS = "extra_device_address";
	

	@Override
	public void onCreate() {
		super.onCreate();
        Log.d("APP", "App.iselect server oncreste");
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			return;
		}
		
///////////////////add for unlink bt 
		/*
	    	IntentFilter intentFilter = new IntentFilter();  
	    	intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
	       registerReceiver(mBtReceiver, intentFilter);	
	       */	
////////////////////////////////////
		
	}

	/*
	 private final BroadcastReceiver mBtReceiver = new BroadcastReceiver()
	 {
		
			public void onReceive(Context context, Intent intent) 
			{
				String action = intent.getAction();
				if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) 
				{
					int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
					if(state == BluetoothAdapter.STATE_OFF)
					{
	                    Log.e(TAG, "wwww bt is STATE_OFF!!");
	            		if (App.btService != null) {
	            			App.btService.connectionLost();
	            			App.btService.setState(4);	
	            		}
					}
						
				}
				else 
				{
					Log.e(TAG, "wwww bt other action!!");
				}
			}
		};		
	*/
	@Override
	public void onDestroy() {
		super.onDestroy();
		
///////////////////add for unlink bt 
      //  unregisterReceiver(mBtReceiver);	
////////////////////////
		
		
		//����ͣ������ͣ������ͣ������stopService
		if (App.btService != null) {
			App.btService.stop();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand:start");
		if (intent != null) {
			String address = intent.getStringExtra(EXTRA_DEVICE_ADDRESS);
			Log.i(TAG, "address:" + address);
			if (!TextUtils.isEmpty(address)) {
				connectDevice(address);
			}
		}
		else
		{
			SharedPreferences sharedata = this.getSharedPreferences("data", 0);  
			String address = sharedata.getString("btaddress", null);  
			Log.i(TAG,"btaddress="+address);
			if (!TextUtils.isEmpty(address)) {
				connectDevice(address);
			}			
		}
		return super.onStartCommand(intent, flags, startId);
		//return START_REDELIVER_INTENT;
	}
	
	private void connectDevice(String address) {
		// Get the BLuetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		
		//�����û���������ַ,��persistentreceiver���õ�
		SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();  
		sharedata.putString("btaddress",address);  
		sharedata.commit();
		
		if(mBluetoothAdapter.isEnabled())
		{
		// Attempt to connect to the device
			if(App.btService!=null) {
				App.btService.connect(device, true);
			}
		}
		else
		{
			App.btService.connectionLost();			
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
}
