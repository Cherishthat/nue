/*
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hxgy.nurexcute.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteMainLVAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.TipHelper;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.widget.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WaitExcuteList extends SherlockActivity {
	 private ExcuteMainLVAdapter lvAdapter;
	 private PullToRefreshListView excutelv;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.waitexcute, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_waitexcute_btn:
			excute();
			break;
		
		}
        return true;
    }


private void excute(){
	
	DialogTool.createConfirmDialog(this, "提示", "医嘱执行", "执行", "取消", new android.content.DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			setSupportProgressBarIndeterminateVisibility(true);
			String ordIDStr="";
			String disposeStatCodeStr="";
			for (ExcuteOrderDTO o : lvAdapter.getExcuteOrderList()) {
				if(o.getOrderName().contains("____")){
					continue;
				}
				ordIDStr=ordIDStr+"^"+o.getOrdID();
				disposeStatCodeStr=disposeStatCodeStr+"^"+o.getDisposeStatCode();
			}
			
			
			ApiClient.excuteOrder(ordIDStr, "E", CurUser.userId, CurUser.userDepartmentID, disposeStatCodeStr, "", new ExcuteHander(WaitExcuteList.this));
			
		}
		
	}, new android.content.DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
		
	}, DialogTool.NO_ICON).show();
	
	
	
}

class ExcuteHander extends AsyncHttpResponseHandler{

	Context context;
	public ExcuteHander(Context context){
		this.context=context;
	}
	@Override
	public void onFailure(Throwable throwable, String arg1) {
		super.onFailure(throwable, arg1);
		UIHelper.ToastFailMessage(this.context, throwable);
		
	}

	@Override
	public void onFinish() {
		super.onFinish();
		setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		
		Gson gson = new Gson();
		try{
		List<ResultMessDTO> list = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO o = list.get(0); 
		if (!o.getCode().equals("0")){
			UIHelper.ToastMessage(this.context, o.getMessage());
//			 Intent data=new Intent();  
//			 data.putExtra(); 
		
			
		}else{
			UIHelper.ToastMessage(this.context, R.string.WaitExcuteList_sucess);
			 setResult(201);  
		     finish();  
		}
		}catch(Exception ex){
			UIHelper.ToastMessage(this.context, ex.getMessage());
		}
	}
	
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excute_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    private void init(){
	    this.setTitle(R.string.WaitExcuteList_title);
		excutelv = (PullToRefreshListView) findViewById(R.id.excute_main_excutelv);
		View listHeard= LayoutInflater.from(this).inflate(R.layout.excute_main_lv_head, null);
		excutelv.addHeaderView(listHeard);
		excutelv.setOnItemLongClickListener(new LVLongClickListener(this));
		getExcuteOrder();
		excutelv.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
				@Override
				public void onRefresh() {
					
					getExcuteOrder();
					excutelv.onRefreshComplete();
				}
			});
		
 }
    private void getExcuteOrder(){
		 final ArrayList<ExcuteOrderDTO>  list= (ArrayList<ExcuteOrderDTO>) getIntent().getSerializableExtra("waitexcute");  
		 lvAdapter = new ExcuteMainLVAdapter(this, list);
		 excutelv.setAdapter(lvAdapter);
	 }
	 class LVLongClickListener implements OnItemLongClickListener{
			Context context=null;
			public LVLongClickListener(Context context){
				this.context=context;
			}
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View v,
					int position, long id) {
				TipHelper.Vibrate(WaitExcuteList.this, 100);
				if(position<2){
					return false;
				}
				lvAdapter.getExcuteOrderList().remove(position-2);
				lvAdapter.notifyDataSetChanged();
				return false;
			}
			
		}
    
}
