package com.hxgy.nurexcute.ui;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.EventMainAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.EventDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class EventMain extends SherlockActivity {
	
	TextView txtbed;
	TextView txtpatno;
	TextView txtname;
	TextView txtresult;
	ListView lv;
	PatientDTO patient=null;
	EventDTO event=null;
	List<EventDTO> eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.event_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.eventmenu, menu);
        return true;
    }
    
    private void delete(){
    	DialogTool.createConfirmDialog(EventMain.this, "提示", "事件删除", "确定", "取消", new android.content.DialogInterface.OnClickListener(){

    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			String eventIds="";
    	        for (EventDTO o : eventList) {
    				if(o.getCheck()){
    					eventIds=eventIds+","+o.getRowid();
    				}
    			}
    	    	ApiClient.DeleteEvent(eventIds, new DeleteEventHandle(EventMain.this));
    		}
    		
    	}, new android.content.DialogInterface.OnClickListener(){

    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			
    		}
    		
    	}, DialogTool.NO_ICON).show();
    	
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		case R.id.menu_event_add :
			UIHelper.showEventEdit(this, patient, event);
		    break;
		case R.id.menu_event_delete:
			delete();
		    break;
		}
    	
 
        return true;
    }
    private void init(){
	    this.setTitle(R.string.menu_right_bar_event);
	    patient= (PatientDTO)getIntent().getSerializableExtra("patient");
	    txtbed = (TextView) this.findViewById(R.id.event_main_tvbed);
	    txtpatno = (TextView) this.findViewById(R.id.event_main_tvpatno);
	    txtname = (TextView) this.findViewById(R.id.event_main_tvname);
	    lv=(ListView) this.findViewById(R.id.event_main_list);
	    txtbed.setText(patient.getBedNo());
	    txtpatno.setText(patient.getPatNo());
	    txtname.setText(patient.getName());
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				CheckBox ck=(CheckBox) v.findViewById(R.id.event_main_item_desc);
				if(ck.isChecked()) {
					ck.setChecked(false);
				}else {
					ck.setChecked(true);
				}
				EventDTO o=(EventDTO)v.getTag();
				for (EventDTO iterable_element : eventList) {
					if(iterable_element.getRowid().equals(o.getRowid())){
						iterable_element.setCheck(ck.isChecked());
					}
				}
			}
		});
	    showData();
 }


   private void showData(){
	    setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.PatAdmEvent(patient.getAdm(),new PatAdmEventHandle(this));
   }

class DeleteEventHandle extends AsyncHttpResponseHandler {
	Context context =null;
    public DeleteEventHandle(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		try {
		Gson gson = new Gson();
		List<ResultMessDTO> p  = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO o =p.get(0);
		if (o.getCode().equals("1")){
			UIHelper.ToastMessage(context, R.string.event_input_delete_ok);
			showData();
		}else {
			UIHelper.ToastMessage(context, R.string.event_input_delete_false);
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

class PatAdmEventHandle extends AsyncHttpResponseHandler {
	Context context =null;
    public PatAdmEventHandle(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		try {
		Gson gson = new Gson();
		eventList = gson.fromJson(result,new TypeToken<List<EventDTO>>(){}.getType() );
		lv.setAdapter(new EventMainAdapter(context,eventList));
		
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
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	if(200==resultCode){
		showData();
	}
	
	super.onActivityResult(requestCode, resultCode, data);
}
    
}