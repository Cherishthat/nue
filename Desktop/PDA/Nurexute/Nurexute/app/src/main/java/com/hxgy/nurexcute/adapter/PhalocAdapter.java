package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.BaseDataDTO;
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

public class PhalocAdapter extends BaseAdapter {

	Context context;
	List<BaseDataDTO> locList;
	public PhalocAdapter(Context contex,List<BaseDataDTO> locList){
		this.context=contex;
		this.locList=locList;
	}
	
	@Override
	public int getCount() {
		return locList.size();
	}

	@Override
	public Object getItem(int position) {
		return locList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.phaloc_item, null);
		TextView test = (TextView) convertView.findViewById(R.id.phaloc_spdesc);
		BaseDataDTO dto = locList.get(position);
		convertView.setTag(dto);
		test.setText(dto.getDesc());
		return convertView;
	}

}
