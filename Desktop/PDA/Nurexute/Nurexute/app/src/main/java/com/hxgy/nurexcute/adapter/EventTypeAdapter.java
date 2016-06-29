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
import android.widget.ImageView;
import android.widget.TextView;

public class EventTypeAdapter extends BaseAdapter {

	Context context;
	List<BaseDataDTO> baseDatalist;
	public EventTypeAdapter(Context contex,List<BaseDataDTO> baseDatalist){
		this.context=contex;
		this.baseDatalist=baseDatalist;
	}
	
	@Override
	public int getCount() {
		return baseDatalist.size();
	}

	@Override
	public Object getItem(int position) {
		return baseDatalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
		TextView desc = (TextView) convertView.findViewById(R.id.spinner_spdesc);
		BaseDataDTO dto = baseDatalist.get(position);
		desc.setText(dto.getDesc());
		convertView.setTag(dto);
		
		return convertView;
	}

}
