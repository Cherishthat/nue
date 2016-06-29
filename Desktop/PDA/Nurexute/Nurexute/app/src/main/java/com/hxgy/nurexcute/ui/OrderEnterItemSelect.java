package com.hxgy.nurexcute.ui;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.LookUpItemAdapter;
import com.hxgy.nurexcute.adapter.OrderEnterSpanAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ArcItemDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

public class OrderEnterItemSelect extends SherlockActivity {
	public static final int ARCITEM=1; //医嘱
	ListView lv;
	private String title;
	private PatientDTO patient;
	private ProgressBar pbar;
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderenter_itmselect);
		
		init();
	}
	
	private void init() {
		
		setTitle("选择医嘱");
		lv = (ListView) findViewById(R.id.orderenter_select_lv);
		Intent intent = this.getIntent();
		String inputData=intent.getStringExtra("inputData");
		pbar=(ProgressBar) findViewById(R.id.orderenter_select_prossbar);
		int inputType=intent.getIntExtra("inputType",2);
		patient = (PatientDTO) intent.getSerializableExtra("patient");
		lv.setOnItemClickListener(new OnItemClickListener(){
		    
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				 Intent data=new Intent();  
				 data.putExtra("arcitmid", v.getTag().toString()); 
				 setResult(200, data);  
			     finish();  
			}
			 
		 });

		
		switch (inputType) {
		case ARCITEM:
			pbar.setVisibility(View.VISIBLE);
			ApiClient.LookUpItem(inputData, CurUser.userGroupID, patient.getAdm(),CurUser.userId, new LookUpItemHander(this));
			break;

		default:
			break;
		}
		
	}
	class LookUpItemHander extends AsyncHttpResponseHandler {
		Context context =null;
	    public LookUpItemHander(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<ArcItemDTO> p = gson.fromJson(result,new TypeToken<List<ArcItemDTO>>(){}.getType() );
			lv.setAdapter(new LookUpItemAdapter(context,p));
			}catch(Exception ex) {
				UIHelper.ToastMessage(context, ex.getMessage());
			}
			
		}

		@Override
		public void onFailure(Throwable throwable, String arg1) {
			super.onFailure(throwable, arg1);
			UIHelper.ToastFailMessage( this.context, throwable);
			
		}
		@Override
		public void onFinish() {
			pbar.setVisibility(View.GONE);
		}
	}
}
