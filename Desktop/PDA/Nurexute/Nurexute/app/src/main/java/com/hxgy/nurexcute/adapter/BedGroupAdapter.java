package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.BedGroupDTO;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BedGroupAdapter extends BaseAdapter {

	Context context;
	List<BedGroupDTO> bedGroupList;
	String selectIdx="";
	public BedGroupAdapter(Context contex,List<BedGroupDTO> bedGroupList ,String selectIdx){
		this.context=contex;
		this.bedGroupList=bedGroupList;
		this.selectIdx=selectIdx;
	}
	
	@Override
	public int getCount() {
		return bedGroupList.size();
	}

	@Override
	public Object getItem(int position) {
		return bedGroupList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.setting_bedgroup_item, null);
		TextView code=(TextView) convertView.findViewById(R.id.setting_group_code);
		TextView desc=(TextView) convertView.findViewById(R.id.setting_group_desc);
		BedGroupDTO o = bedGroupList.get(position);
		if(selectIdx.equals(o.getRowid())){
			    convertView.setBackgroundResource(R.color.itemgreen);
			    convertView.setAlpha(200);
			    code.setTextColor(Color.WHITE);
				desc.setTextColor(Color.WHITE);
				
		}
		code.setText(o.getCode());
		desc.setText(o.getDesc());
		convertView.setTag(o);
		return convertView;
	}

}
