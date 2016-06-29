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

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.OrderTypeDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ExcuteSearch extends SherlockActivity {
	 private ListView ordtypeList;
	 private Button excutesearchbgdatebtn;
	 private Button excutesearcheddatebtn;
	 private EditText excuteseachbgdate;
	 private EditText excuteseacheddate;
	 private View oldItemView;
	 private static int BGYEAR=0;
	 private static int BGMOTH=0;
	 private static int BGDAY=0;
	 private static int EDYEAR=0;
	 private static int EDMOTH=0;
	 private static int EDDAY=0;
	 private Calendar cal = Calendar.getInstance();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.excute_seach, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_excute_search_btn:
			excute();
			break;
		
		}
        return true;
    }


private void excute(){
	 Intent data=new Intent();  
	 if(oldItemView==null){
        return;
	 }
	 data.putExtra("ordertype", oldItemView.getTag().toString()); 
     data.putExtra("bgDate", excuteseachbgdate.getText().toString());  
     data.putExtra("edDate", excuteseacheddate.getText().toString());  
     setResult(200, data);  
     finish();  

}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.excute_search);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        init();
    }
    private void init(){
	    this.setTitle(R.string.ExcuteSearch_title);
	    if(ExcuteSearch.BGYEAR==0){
	      ExcuteSearch.BGYEAR=cal.get(Calendar.YEAR);
	      ExcuteSearch.BGMOTH=cal.get(Calendar.MONTH);
	      ExcuteSearch.BGDAY=cal.get(Calendar.DAY_OF_MONTH);
	    }
	    if(ExcuteSearch.EDYEAR==0){
		      ExcuteSearch.EDYEAR=cal.get(Calendar.YEAR);
		      ExcuteSearch.EDMOTH=cal.get(Calendar.MONTH);
		      ExcuteSearch.EDDAY=cal.get(Calendar.DAY_OF_MONTH);
		    }
	    excuteseachbgdate = (EditText) findViewById(R.id.exam_bgdate);
	    excuteseacheddate = (EditText) findViewById(R.id.excute_search_eddate);
	    excuteseacheddate.setText(EDYEAR + "-" + (EDMOTH + 1) + "-" + EDDAY);
	    excuteseachbgdate.setText(BGYEAR + "-" + (BGMOTH + 1) + "-" + BGDAY);
	    excutesearchbgdatebtn = (Button)findViewById(R.id.excute_search_bgdate_btn);
	    excutesearcheddatebtn = (Button)findViewById(R.id.excute_search_eddate_btn);
	    ordtypeList = (ListView) findViewById(R.id.excute_search_ordtypelist);
	    ordtypeList.setOnItemClickListener(new OrdTypeItemClick()); 
	    getOrdType();
	    excuteseachbgdate.setKeyListener(null);
	    excuteseacheddate.setKeyListener(null);
	    excutesearchbgdatebtn.setOnClickListener(new DatebtnClick());
	    excutesearcheddatebtn.setOnClickListener(new DatebtnClick());
	    excuteseachbgdate.setOnClickListener(new DatebtnClick());
	    excuteseacheddate.setOnClickListener(new DatebtnClick());
		
 }
    private void itemClick(View convertView,int color){
		TextView name=(TextView) convertView.findViewById(R.id.excute_search_item_name);
		TextView code=(TextView) convertView.findViewById(R.id.excute_search_item_code);
		
		name.setTextColor(color);
		code.setTextColor(color);
		excute();
    }
    
    class OrdTypeItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2,
				long arg3) {
			if(oldItemView==null){
				oldItemView=v;
			}else{
				oldItemView.setBackgroundResource(R.color.white);
				itemClick(oldItemView,Color.BLACK);
				v.setAlpha(255);
			}
			v.setBackgroundResource(R.color.itemgreen);
			v.setAlpha(200);
			itemClick(v,Color.WHITE);
			oldItemView=v;
			
			
		}
    	
    }
    
    class DatebtnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.exam_bgdate:
			case R.id.excute_search_bgdate_btn :
				new DatePickerDialog(ExcuteSearch.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						excuteseachbgdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
						ExcuteSearch.BGYEAR=year;
						ExcuteSearch.BGMOTH=monthOfYear;
						ExcuteSearch.BGDAY=dayOfMonth;
						
					}
		            }, BGYEAR, BGMOTH, BGDAY).show();

				
				break;
			case R.id.excute_search_eddate:
			case R.id.excute_search_eddate_btn :
				new DatePickerDialog(ExcuteSearch.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						
						excuteseacheddate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
						ExcuteSearch.EDYEAR=year;
						ExcuteSearch.EDMOTH=monthOfYear;
						ExcuteSearch.EDDAY=dayOfMonth;
	                }
		            }, EDYEAR, EDMOTH, EDDAY).show();

				
				break;

			default:
				break;
			}
			 
			
		}
    	
    }
    
    private void getOrdType(){
    	setSupportProgressBarIndeterminateVisibility(true);
    	ApiClient.getordertypeList(CurUser.userGroupID, CurUser.userId, new OrdTypeHander(ExcuteSearch.this));
    }
    
    class OrdTypeHander extends  AsyncHttpResponseHandler{

    	Context context;
    	public OrdTypeHander(Context context){
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
    		List<OrderTypeDTO> list = gson.fromJson(result,new TypeToken<List<OrderTypeDTO>>(){}.getType() );
    		ordtypeList.setAdapter(new OrdTypeAdpter(ExcuteSearch.this,list));
    		}catch(Exception ex){
    			UIHelper.ToastMessage(this.context, ex.getMessage());
    		}
    	}
    	
    }
    
    class OrdTypeAdpter extends BaseAdapter{

    	Context context;
    	List<OrderTypeDTO> list;
    	public OrdTypeAdpter(Context context,List<OrderTypeDTO> list){
    		this.context = context;
    		this.list=list;
    	}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(context).inflate(R.layout.excute_search_item, null);
			TextView name=(TextView) convertView.findViewById(R.id.excute_search_item_name);
			TextView code=(TextView) convertView.findViewById(R.id.excute_search_item_code);
			OrderTypeDTO item = list.get(position);
			name.setText(item.getDesc());
			code.setText(item.getRw());
			convertView.setTag(item.getRw());
			return convertView;
		}
    	
    }
 

	 
    
}
