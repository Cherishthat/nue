package com.hxgy.nurexcute.ui;


import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.EventTypeAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.EventDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseItemDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.ui.EventMain.DeleteEventHandle;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class EventInput extends SherlockActivity {
	private PatientDTO patient;
	private TextView name; 
	private TextView patno;
	private TextView bedno;
	private TextView txdate;
	private TextView txtime;
	private Spinner type;
	private EventDTO event;
	 private Calendar cal = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_input);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		init();
	}
	private void init(){
		name=(TextView) findViewById(R.id.event_input_tvname);
		patno=(TextView) findViewById(R.id.event_input_tvpatno);
		bedno=(TextView) findViewById(R.id.event_input_tvbed);
		type=(Spinner) findViewById(R.id.event_input_type);
		txdate=(TextView) findViewById(R.id.event_input_date);
		txdate.setKeyListener(null);
		txdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(EventInput.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						txdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
						
					}
		            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
			}
			
		});
		txtime=(TextView) findViewById(R.id.event_input_time);
		txtime.setKeyListener(null);
		txtime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(EventInput.this,

						 new TimePickerDialog.OnTimeSetListener() {
						         @Override
						         public void onTimeSet(TimePicker view,
						                 int hourOfDay, int minute) {
//						                 c.setTimeInMillis(System.currentTimeMillis());
//						                 c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//						                 c.set(Calendar.MINUTE, minute);
//						                 c.set(Calendar.SECOND, 0); // 设为 0
//						                 c.set(Calendar.MILLISECOND, 0); // 设为 0
						        	 txtime.setText(hourOfDay+":"+minute+":00");
						         }

						 }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();

				
			}
			
		});
		patient = (PatientDTO) getIntent().getSerializableExtra("patient");
		event=(EventDTO) getIntent().getSerializableExtra("event");
		if(event==null) {
			setTitle("添加事件 ");
		}else {
			setTitle("修改事件 ");
		}
		if (patient!=null) {
			name.setText(patient.getName());
			patno.setText(patient.getPatNo());
			bedno.setText(patient.getBedNo());
		}
		setSupportProgressBarVisibility(true);
		ApiClient.QTRECTYP(new QTRECTYPHandle(this));
	
	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getSupportMenuInflater().inflate(R.menu.eventinputmenu, menu);
	        return true;
	    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_event_input_ok:
			addEvent();
			break;
		}
        return true;
    }
    
    private void addEvent(){
    	DialogTool.createConfirmDialog(EventInput.this, "提示", "事件增加", "确定", "取消", new android.content.DialogInterface.OnClickListener(){

    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			BaseDataDTO typeObj=(BaseDataDTO)type.getSelectedItem();
    			setSupportProgressBarIndeterminateVisibility(true);
    			ApiClient.ExeEvent(patient.getAdm(),CurUser.userId,typeObj.getRowid(),txdate.getText().toString(),txtime.getText().toString(),new ExeEventHandle(EventInput.this));
    		}
    		
    	}, new android.content.DialogInterface.OnClickListener(){

    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			
    		}
    		
    	}, DialogTool.NO_ICON).show();
    }
    class QTRECTYPHandle extends AsyncHttpResponseHandler {
    	Context context =null;
        public QTRECTYPHandle(Context context) {
        	this.context =context;
        }
    	@Override
    	public void onSuccess(String result) {
    		super.onSuccess(result);
    		try {
    		Gson gson = new Gson();
    		List<BaseDataDTO> p = gson.fromJson(result,new TypeToken<List<BaseDataDTO>>(){}.getType() );
    		type.setAdapter(new EventTypeAdapter(context,p));
    		
    		}catch(Exception ex){
    			UIHelper.ToastMessage(context, ex.toString());
    		}
    	}

    	@Override
    	public void onFailure(Throwable throwable, String arg1) {
    		super.onFailure(throwable, arg1);
    		UIHelper.ToastFailMessage( this.context, throwable);
    		
    	}
    	@Override
    	public void onFinish() {
    		setSupportProgressBarIndeterminateVisibility(false);
    	}
    }
   
    class ExeEventHandle extends AsyncHttpResponseHandler {
    	Context context =null;
        public ExeEventHandle(Context context) {
        	this.context =context;
        }
    	@Override
    	public void onSuccess(String result) {
    		super.onSuccess(result);
    		try {
    		Gson gson = new Gson();
    		List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
    		ResultMessDTO o =p.get(0);
    		if (o.getCode().equals("1")){
    			UIHelper.ToastMessage(context, R.string.event_input_add_ok);
    			setResult(200);  
    		    finish();  
    		}else {
    			UIHelper.ToastMessage(context, R.string.event_input_add_false);
    		}
    		
    		}catch(Exception ex){
    			UIHelper.ToastMessage(context, ex.toString());
    		}
    	}

    	@Override
    	public void onFailure(Throwable throwable, String arg1) {
    		super.onFailure(throwable, arg1);
    		UIHelper.ToastFailMessage( this.context, throwable);
    		
    	}
    	@Override
    	public void onFinish() {
    		setSupportProgressBarIndeterminateVisibility(false);
    	}
    }
}
