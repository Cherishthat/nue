package com.hxgy.nurexcute.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BedGroupAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.BedGroupDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SettingBedGroup extends SherlockActivity{
	
	private ListView lv;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	        setContentView(R.layout.setting_bedgroup);
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        init();
	      
	    }
	 private void init(){
		 lv=(ListView) findViewById(R.id.setting_group_list);
		 View listHeard= LayoutInflater.from(this).inflate(R.layout.setting_bedgroup_item, null);
		 listHeard.setBackgroundResource(R.color.blue);
		 lv.addHeaderView(listHeard);
		 
		 lv.setOnItemClickListener(new OnItemClickListener(){
			    
				@Override
				public void onItemClick(AdapterView<?> arg0, View v, int arg2,
						long arg3) {
					SharedPreferences prefer = getSharedPreferences("APPSET", Activity.MODE_PRIVATE);
					SharedPreferences.Editor editor= prefer.edit();
					BedGroupDTO o=(BedGroupDTO)v.getTag();
					editor.putString(CurUser.userDepartmentID,o.getRowid());
					editor.apply();
				    finish();  
				}
				 
			 });
		 setSupportProgressBarIndeterminateVisibility(true);
		 ApiClient.GetBedGroup(CurUser.userDepartmentID, new BedGroupHander(this));
	 }
	 class BedGroupHander extends  AsyncHttpResponseHandler{
			Context context =null;
		    public BedGroupHander(Context context) {
		    	this.context =context;
		    }
			@Override
			public void onSuccess(String result) {
				super.onSuccess(result);
				Gson gson = new Gson();
				try{
				List<BedGroupDTO> p = gson.fromJson(result,new TypeToken<List<BedGroupDTO>>(){}.getType() );
				SharedPreferences prefer = getSharedPreferences("APPSET", Activity.MODE_PRIVATE);
				String selectIdx=prefer.getString(CurUser.userDepartmentID, "");
				lv.setAdapter(new BedGroupAdapter(context,p ,selectIdx));
				}catch (Exception ex) {
					UIHelper.ToastMessage(context, ex.getMessage());
				}
			}

			@Override
			public void onFailure(Throwable throwable, String arg1) {
				super.onFailure(throwable, arg1);
				UIHelper.ToastFailMessage( this.context, throwable);
				
					setSupportProgressBarIndeterminateVisibility(false);
				
				
			}
			@Override
			public void onFinish() {
				setSupportProgressBarIndeterminateVisibility(false);
			}
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
	 
}
