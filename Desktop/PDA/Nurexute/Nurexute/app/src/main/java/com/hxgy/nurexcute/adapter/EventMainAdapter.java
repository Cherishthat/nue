package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.EventDTO;
import com.hxgy.nurexcute.dto.PatientDTO;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class EventMainAdapter extends BaseAdapter {

	Context context;
	List<EventDTO> eventList;
	public EventMainAdapter(Context contex,List<EventDTO> eventList){
		this.context=contex;
		this.eventList=eventList;
	}
	
	@Override
	public int getCount() {
		return eventList.size();
	}

	@Override
	public Object getItem(int position) {
		return eventList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.event_main_item, null);
		CheckBox desc = (CheckBox) convertView.findViewById(R.id.event_main_item_desc);
		EventDTO dto = eventList.get(position);
		desc.setOnCheckedChangeListener(new MyCheckedChangeListener(dto));
		TextView date=(TextView) convertView.findViewById(R.id.event_main_item_date);
		TextView userName=(TextView) convertView.findViewById(R.id.event_main_item_username);
		
		convertView.setTag(dto);
		desc.setText(dto.getDesc());
		desc.setChecked(dto.getCheck());
		date.setText(dto.getDate()+" "+dto.getTime());
		userName.setText(dto.getUsername());
		
		return convertView;
	}
	class MyCheckedChangeListener implements OnCheckedChangeListener{
		EventDTO dto;
		public MyCheckedChangeListener(EventDTO dto){
			this.dto=dto;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			dto.setCheck(isChecked);
			
		}
		
	}

}
