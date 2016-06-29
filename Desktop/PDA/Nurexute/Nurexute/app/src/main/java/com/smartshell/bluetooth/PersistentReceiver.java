package com.smartshell.bluetooth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.smartshell.bluetooth.BluetoothConnectService;
import com.smartshell.bluetooth.BluetoothService;

public class PersistentReceiver extends BroadcastReceiver {

	public static final String ACTION_BROADCAST_PERSISTENT_BROADCAST = "action.broadcast.persistent.smartshell";
	private static final String TAG = "PersistentReceiver";

	private static final long reconnectInterval = 1 * 8 * 1000; //1��������һ��
 
//	private static AlarmManager alarmManager = null;
	//private static AlarmManager alarmManager = (AlarmManager) App.appContext.getSystemService(Context.ALARM_SERVICE);
	private static PendingIntent pIntent;

	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive----persistent isRunning:");
		
		if (intent == null) {
			return;
		}

				
		String action = intent.getAction();
		if (TextUtils.isEmpty(action)) {
			return;
		}
		
		Log.i(TAG, "onReceive----persistent action=" + action);
		if (ACTION_BROADCAST_PERSISTENT_BROADCAST.equals(action)) {

			//����33���Ӻ�����Ӧ�ã��յ��㲥������ʱ3���Ӽ���
			Intent persistentIntent = new Intent(ACTION_BROADCAST_PERSISTENT_BROADCAST);
			pIntent = PendingIntent.getBroadcast(context, 0, persistentIntent,
					PendingIntent.FLAG_CANCEL_CURRENT);

//			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + reconnectInterval,
//					pIntent);


			Log.d(TAG, "CHECK_CONNECTED-" + action);
			//��ʱ���
			
			Log.i(TAG,"App.btService.getState()="+App.btService.getState());
			
			if(App.btService.getState()==3) return;
			
			//���߼�����������ǲ�������״̬
			if (App.btService == null) {
				Log.i(TAG,"App.isbtService == null");
				//if (App.btService.getState() != BluetoothConnectService.STATE_CONNECTED) {
					//������״̬
					//�������ӷ���
					//String address = "ss";//������ӳɹ��󱣴����ӵ�ַ���´���Ҫ���ӵ�ʱ��ֱ�Ӵ������ַ����

					SharedPreferences sharedata = context.getSharedPreferences("data", 0);  
					String address = sharedata.getString("btaddress", null);  
					Log.i(TAG,"btaddress="+address);	
					
					if(!address.isEmpty())
				  {
					Intent connIntent = new Intent("com.smartshell.bluetooth.service");
				     connIntent.putExtra(BluetoothService.EXTRA_DEVICE_ADDRESS, address);
                     context.startService(connIntent);
				  }
				//}
			}
			else
			{
				Log.i(TAG,"App.isbtService != null");
				if (App.btService.getState() == BluetoothConnectService.STATE_DISCONN || App.btService.getState() == BluetoothConnectService.STATE_NONE) {
					SharedPreferences sharedata = context.getSharedPreferences("data", 0);  
					String address = sharedata.getString("btaddress", null);  
					Log.i(TAG,"btaddress="+address);	
					
				if(!address.isEmpty())
				  {
					Intent connIntent = new Intent("com.smartshell.bluetooth.service");
				     connIntent.putExtra(BluetoothService.EXTRA_DEVICE_ADDRESS, address);
                     context.startService(connIntent);
				  }
				}				
			}
			
		}  
	}

	//�ֶ����˵㿪���Ӻ���ã�ͣ��3����ѭ����ʱ
	public static void cancelPersistentObserver() {
		
//		if (pIntent != null && alarmManager != null) {
//			alarmManager.cancel(pIntent);
//		}
	}
	
	
	//���ӳɹ������־û������������Ƕ�ʱ����3���ӷ�һ���㲥�������յ��㲥��������������ӣ�Ȼ�����ǲ������ӣ�δ���������ӣ���������˵�����������ȴ��¸�3���ӹ㲥�ĵ���
	public static void startPersistentObserver() {
		Log.d(TAG, "startPersistentObserver");
		cancelPersistentObserver();
		Intent intent = new Intent(ACTION_BROADCAST_PERSISTENT_BROADCAST);
		App.appContext.sendBroadcast(intent);
	}
	
}
