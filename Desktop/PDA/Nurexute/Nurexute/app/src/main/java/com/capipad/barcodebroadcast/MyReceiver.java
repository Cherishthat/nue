/**
 *
 */
package com.capipad.barcodebroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";
	private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
	private static final String ACTION_F11_KEYDOWN = "com.android.action.F11_KEYDOWN";
	private static final String ACTION_F11_KEYUP = "com.android.action.F11_KEYUP";

	private static boolean mF11ScanFlag = true;

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent mScanIntent = new Intent(context, ScanService.class);

		Log.i(TAG, "receive broadcast:" + intent.getAction().toString());

		if (intent.getAction().equals(ACTION_F11_KEYDOWN)) {
			Log.i(TAG, "in keydown");
			if (mF11ScanFlag && ScanService.mCanClick) {
				Log.i(TAG, "the flag work");
				context.startService(mScanIntent);
				mF11ScanFlag = false;
			}
		}

		if (intent.getAction().equals(ACTION_F11_KEYUP)) {
			Log.i(TAG, "in keyup:");
			mF11ScanFlag = true;
		}

	}

}
