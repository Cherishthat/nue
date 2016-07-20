package com.capipad.barcodebroadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.hxgy.nurexcute.R;

public class ScanService extends Service {
	private final String TAG = "ScanService";
	private static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
	private static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
	public static boolean mCanClick = true;
	private boolean initflag = true;
	private MediaPlayer mMediaPlayer;
	private WorkThread mWorkThread = new WorkThread();
	private MyHandler mHandler;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		mWorkThread.start();
		mMediaPlayer = MediaPlayer.create(this,R.raw.beep);
		IntentFilter mScreenONorOFF = new IntentFilter();
		mScreenONorOFF.addAction(Intent.ACTION_SCREEN_OFF);
		mScreenONorOFF.addAction(Intent.ACTION_SCREEN_ON);
		mScreenONorOFF.setPriority(Integer.MAX_VALUE);
		registerReceiver(mScreenReceiver, mScreenONorOFF);
	}

	@Override
	public void onDestroy() {
		mWorkThread.stopThread();
		mMediaPlayer.release();
		mMediaPlayer = null;
		this.unregisterReceiver(mScreenReceiver);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		funcInit();
		mWorkThread.doThread();
		android.util.Log.i(TAG, "in ScanService:");
	}

	protected int funcInit() {
		int ret = 0;
		if (initflag) {
			ret = barcode.BarCodeInit(1);
			initflag = false;
		}
		return ret;
	}

	protected String funcWork() {
		String msg = null;
		msg = barcode.BarCodeRead();
		return msg;
	}

	protected int funcDeinit() {
		int ret = 0;
		ret = barcode.BarCodeDeInit();
		initflag = true;
		return ret;
	}

	private void runBeep() {
		mMediaPlayer.start();
	}

	private BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_SCREEN_OFF)) {
				mCanClick = false;

			} else if (intent.getAction().equals(ACTION_SCREEN_ON)) {
				mCanClick = true;

			}

		}
	};

	private class MyHandler extends Handler {
		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// ������Ϣ
			Log.i(TAG, "get message:" + (String) msg.toString());
			// setClipboard((String) msg.obj);
			// sendTopaste();
			Intent intent = new Intent();
			intent.setAction("com.android.action.BARCODE_DATA");
			intent.putExtra("data", (String) msg.obj);
			sendBroadcast(intent);
			funcDeinit();
		}
	}

	private class WorkThread extends Thread {

		private volatile boolean bRunFlag = true;
		private volatile boolean bDoFlag = false;
		private String mReadMessage = null;

		public void stopThread() {
			bRunFlag = false;
		}

		public void doThread() {
			bDoFlag = true;
		}

		public void run() {
			Looper mainLooper = Looper.getMainLooper();
			String msg;
			mHandler = new MyHandler(mainLooper);
			mHandler.removeMessages(0);
			while (bRunFlag) {
				if (bDoFlag) {
					mReadMessage = funcWork();
					bDoFlag = false;
					if (!mReadMessage.isEmpty()) {
						runBeep();
						Log.i(TAG, "readmessage:" + mReadMessage);
						msg = mReadMessage;
						Message m = mHandler.obtainMessage(1, 1, 1, msg);
						mHandler.sendMessage(m);
					} else {
						Log.i(TAG, "no data");
						msg = mReadMessage;
						Message m = mHandler.obtainMessage(1, 1, 1, msg);
						mHandler.sendMessage(m);
					}
				} else {
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}

	}
}