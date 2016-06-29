package com.hxgy.nurexcute.ui;

import java.util.ArrayList;
import java.util.List;




import com.hxgy.nurexcute.R;


import com.hxgy.nurexcute.dto.BaseDataDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ApplyRtReasonDia extends Activity {

	ListView list;
	List<BaseDataDTO> baselist;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.applyrt_reason);
		
		 list = (ListView) findViewById(R.id.applyrt_reasonlist);
		 baselist= (List<BaseDataDTO>) this.getIntent().getSerializableExtra("reason");
		 list.setAdapter(new ReasonAddpter(this,this.baselist));
		 list.setOnItemClickListener(new OnItemClickListener(){
	    
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent data=new Intent();  
				 data.putExtra("checkReason", baselist.get(arg2)); 
				 setResult(200, data);  
			     finish();  
			}
			 
		 });
	}
	
	
	class ReasonAddpter extends BaseAdapter {

		Context context;
		List<BaseDataDTO> list;
		public ReasonAddpter(Context context,List<BaseDataDTO> list){
			this.context=context;
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
			convertView=LayoutInflater.from(context).inflate(R.layout.applyrt_reason_item, null);
			TextView reason=(TextView) convertView.findViewById(R.id.apply_reason_item);
			BaseDataDTO dto = baselist.get(position);
			reason.setText(dto.getDesc());
			
			return convertView;
		}
	}

}
