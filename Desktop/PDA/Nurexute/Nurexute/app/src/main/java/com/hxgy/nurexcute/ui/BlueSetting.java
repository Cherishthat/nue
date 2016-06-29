package com.hxgy.nurexcute.ui;

import org.w3c.dom.Text;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.UIHelper;
import com.smartshell.bluetooth.CommonUtils;
import com.smartshell.bluetooth.DeviceListActivity;

public class BlueSetting extends SherlockActivity{
	  Button btnSetting,btnOk,btnClose;
	  TextView txtbtadd;
	    private static final String TAG = "smartshell";
	    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	    private static final int REQUEST_ENABLE_BT = 3; 
	  private BluetoothAdapter mBluetoothAdapter = null;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bluesetting);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        this.setTitle(R.string.Setting_SmartShell);
	        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        if (mBluetoothAdapter == null) {
	        	UIHelper.ToastMessage(this, "设备不正确");
	            finish();
	            return;
	        }
	        init();
	        
	    }
	 
	 private void init(){
		 btnSetting= (Button) findViewById(R.id.bluesetting_btnSetting);
		 btnOk= (Button) findViewById(R.id.bluesetting_btnOk);
		 btnClose= (Button) findViewById(R.id.bluesetting_btnclose);
		 txtbtadd=(TextView) findViewById(R.id.bluesetting_txtbtaddress);
		 SettingClick click= new SettingClick();
		 btnSetting.setOnClickListener(click);
		 btnClose.setOnClickListener(click);
		 btnOk.setOnClickListener(click);
	 }
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				break;
			}
	        return true;
	    }
	  @Override
	    protected void onResume() {
	    	super.onResume();

			SharedPreferences sharedata = getSharedPreferences("data", 0);  
			String address = sharedata.getString("btaddress", null);  	
			 
		if(address!=null)
		  {
				txtbtadd.setText(address);
		  } 
	  }
	  
	  public void onStart() {
	        super.onStart();
	        
	        // If BT is not on, request that it be enabled.
	        // setupChat() will then be called during onActivityResult
	        if (!mBluetoothAdapter.isEnabled()) {
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
	        // Otherwise, setup the chat session
	        } 
	    }

	  
	class  SettingClick implements OnClickListener{

		@Override
		public void onClick(View v) {
		
			if (R.id.bluesetting_btnOk == v.getId()) {
				 CommonUtils.smartshellinit(App.appContext);
				 CommonUtils.resumebtservice(App.appContext);
				 finish();
				
			} else if (v.getId() == R.id.bluesetting_btnSetting) {
	        	Intent serverIntent = new Intent(App.appContext, com.smartshell.bluetooth.DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	            
			}else {
				CommonUtils.pausebtservice(App.appContext);
				finish();
			}
			
		}
		
	}
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch (requestCode) {
	        case REQUEST_CONNECT_DEVICE_SECURE:
	            if (resultCode == Activity.RESULT_OK) {           	
	                connectDevice(data, true);
	            }
	            break;
	 
	        } 
	    }
	 
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();  
		sharedata.putString("btaddress",address);  
		sharedata.commit();

    }
}
