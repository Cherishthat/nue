package com.hxgy.nurexcute.ui;

import java.util.List;

import com.example.comm.jason.RunServerMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BedAdapter;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.widget.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class PatientList  extends Activity {
	Gson gson ;
	private View oldItemView;
	
	private OnClickListener radioClickListener;
	
	private PullToRefreshListView pRpatientList;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_list);
//        initListenner();
        init();
	}
	
//	private void initListenner() {
//		radioClickListener = new OnClickListener() {
//			
//			public void onClick(View v) {
//				switch (v.getId()) {
//				case R.id.patient_list_patientInfo:
//					 PatientDTO p=(PatientDTO)pRpatientList.getItemAtPosition(selectIndex);
//					 UIHelper.showPatientInfo(PatientList.this, p.getPatId());
//					break;
//
//				default:
//					break;
//				}
//			}
//		};
//		
//	}

	private void initBedList(List<PatientDTO> p) {
		BedAdapter bedAdapter = new BedAdapter(PatientList.this, p,"");
		
		pRpatientList.setAdapter(bedAdapter);
	}
	
	private void init() {
		
//		RadioButton rd=(RadioButton) findViewById(R.id.patient_list_patientInfo);
		
//		rd.setOnClickListener(radioClickListener);
		
		
		
		
		gson=new Gson();
		
		pRpatientList = (PullToRefreshListView)findViewById(R.id.lv_bed_list);
		pRpatientList.setOnItemClickListener(new OnItemClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
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
				
//				TextView tx=(TextView)arg1.findViewById(R.id.lb_bed_item_patno);
//				tx.setTextColor(Color.RED);   
//				PatientDTO p=(PatientDTO)pRpatientList.getItemAtPosition(arg2);
//				Toast.makeText(PatientList.this, p.getAge(), Toast.LENGTH_LONG).show(); 
			} 
			
		});
		
		GetBed();
		pRpatientList.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
				
				@Override
				public void onRefresh() {
					
					GetBed();
				}
			});
	}
	

private void GetBed(){
	RunServerMethod.GetRunQuery(new AsyncHttpResponseHandler(){

		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			
			
			List<PatientDTO> p = gson.fromJson(result,new TypeToken<List<PatientDTO>>(){}.getType() );
			initBedList(p);
			pRpatientList.onRefreshComplete();
		}
		
	}, "App.Nure.API", "QueryPatientList",CurUser.userDepartmentID,""); 
}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void itemClick(View convertView,int color){
		TextView patNo=(TextView) convertView.findViewById(R.id.lb_bed_item_patno);
		TextView patName=(TextView) convertView.findViewById(R.id.lb_bed_item_name);
		TextView bedNo=(TextView) convertView.findViewById(R.id.lb_bed_item_bedno);
		TextView dia=(TextView) convertView.findViewById(R.id.lb_bed_item_dia);
		patNo.setTextColor(color);
		patName.setTextColor(color);
		bedNo.setTextColor(color);
		dia.setTextColor(color);
	}

}
