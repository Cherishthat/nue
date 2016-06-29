package com.smartshell.bluetooth;

import java.lang.reflect.Field;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class CommonUtils {
	private static final String TAG = "CommonUtils";
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_ENABLE_BT = 3;
 //   public  static boolean isselectadd=false;  //�Ƿ���б���ѡ�����ַ
    
    public static final void smartshellinit(Context ctx)
    {
		App.appContext =ctx;
		App.btService = new BluetoothConnectService(ctx);
		App.iselect=false;
        App.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (App.mBluetoothAdapter == null) {
            Toast.makeText(ctx, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            //finish();
           // return;
        }   		
    }
    public static final boolean btexist()
    {
        if (App.mBluetoothAdapter == null)
        	return false;
        else
        	return true;
    }
    public static final boolean btisenabled()
    {
    	if(App.mBluetoothAdapter.isEnabled())
    		return true;
    	else
    		return false;
    }
    public static final void pausebtservice(Context ctx)
    {
		Log.i(TAG, "pausebtservice" );
		
		
    	if (App.btService != null) {
    		App.btService.stop();
    		
    	}
    	//�ر����Ӽ��
    	
    	 if (App.appContext!=null) {
    		 PersistentReceiver.cancelPersistentObserver();
    	 }
    	
        Intent connIntent = new Intent("com.smartshell.bluetooth.service");
        ctx.stopService(connIntent);	
    }
    
    public static final void resumebtservice(Context ctx)
    {
		Log.i(TAG, "resumebtservice" );
    //	if(!isselectadd)
    	{
		SharedPreferences sharedata = ctx.getSharedPreferences("data", 0);  
		String address = sharedata.getString("btaddress", null);  	
		 
	//	if(!address.isEmpty())
		if(address!=null)
	  {
			Log.i(TAG, "resumebtservice add+"+address );
		Intent connIntent = new Intent("com.smartshell.bluetooth.service");
	     connIntent.putExtra(BluetoothService.EXTRA_DEVICE_ADDRESS, address);
         ctx.startService(connIntent);
 		PersistentReceiver.startPersistentObserver();
	  } 
    	}
    //	else
     //   	isselectadd=false;
	     	
    }
    private static final void connectDevice(Context ctx,Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        Intent connIntent = new Intent("com.smartshell.bluetooth.service");
        connIntent.putExtra(BluetoothService.EXTRA_DEVICE_ADDRESS, address);      
        ctx.startService(connIntent);

    }     
   public static final void connectbtservice(Context ctx,Intent data)
   {
        connectDevice(ctx,data, true);
		PersistentReceiver.startPersistentObserver();	   
   }

	public static final int getResId(Context ctx, String key) {
		Log.i(TAG, "key=" + key);
		int resId = 0;
		if (ctx == null || TextUtils.isEmpty(key)) {
			Log.i(TAG, "argument is null");
			return resId;
		}
		String packageName = ctx.getApplicationContext().getPackageName();
		Log.i(TAG, "packageName=" + packageName);
		String resType = null;
		String resName = key.substring(key.lastIndexOf(".") + 1, key.length());
		Log.i(TAG, "resName=" + resName);
		if (key.startsWith("attr.")) {
			resType = "attr";
		} else if (key.startsWith("drawable.")) {
			resType = "drawable";
		} else if (key.startsWith("id.")) {
			resType = "id";
		} else if (key.startsWith("layout.")) {
			resType = "layout";
		} else if (key.startsWith("menu.")) {
			resType = "menu";
		} else if (key.startsWith("string.")) {
			resType = "string";
		}

		if (TextUtils.isEmpty(resType)) {
			return resId;
		}

		try {
			String classString = packageName + ".R$" + resType;
			@SuppressWarnings("rawtypes")
			Class cls = Class.forName(classString);
			Field resField = cls.getDeclaredField(resName);
			try {
				resField.setAccessible(true);
				resId = resField.getInt(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		Log.i(TAG, "resId=" + resId);
		return resId;
	}
}
