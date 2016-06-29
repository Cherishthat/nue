package com.hxgy.nurexcute.ui;

import java.util.List;
import java.util.zip.Inflater;

import com.example.comm.jason.RunServerMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BedAdapter;
import com.hxgy.nurexcute.adapter.ExcuteMainLVAdapter;
import com.hxgy.nurexcute.common.TipHelper;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.widget.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ExcuteMainActivity extends Activity {
	
	private PullToRefreshListView excutelv ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.excute_main);
		init();
		
	}
   
	
	private void initExcuteOrderList(List<ExcuteOrderDTO> p) {
		ExcuteMainLVAdapter bedAdapter = new ExcuteMainLVAdapter(ExcuteMainActivity.this, p);
		excutelv.setAdapter(bedAdapter);
	}
	
	
	private void init() {
		
		excutelv = (PullToRefreshListView)findViewById(R.id.excute_main_excutelv);
		excutelv.setHeaderDividersEnabled(true);
		excutelv.setHorizontalScrollBarEnabled(true);
		View listHeard= LayoutInflater.from(this).inflate(R.layout.excute_main_lv_head, null);
		excutelv.addHeaderView(listHeard);
		excutelv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				TipHelper.Vibrate(ExcuteMainActivity.this, 100);
				return false;
			}
			
		});
		excutelv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			} 
			
		});
		
		
		GetExcuteOrder();
		
		excutelv.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
				
				@Override
				public void onRefresh() {
					
					GetExcuteOrder();
				}
			});
		
	}
	

private void GetExcuteOrder(){
	RunServerMethod.GetRunQuery(new AsyncHttpResponseHandler(){

		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try{
			Gson gson = new Gson();
			
			List<ExcuteOrderDTO> p = gson.fromJson(result,new TypeToken<List<ExcuteOrderDTO>>(){}.getType() );
			initExcuteOrderList(p);
			}catch(Exception ex) {
				UIHelper.ToastMessage(ExcuteMainActivity.this, ex.toString());
			}
			excutelv.onRefreshComplete();
		}

		@Override
		public void onFailure(Throwable throwable, String arg1) {
			super.onFailure(throwable, arg1);
			excutelv.onRefreshComplete();
			UIHelper.ToastFailMessage(ExcuteMainActivity.this, throwable);
			
		}
		
		
	}, "App.Nure.API", "GetOrderInfoByPatNo","3108171","2010-04-11","2013-11-14","WCL","16","","19"); 
}

	

}
