package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.ArcItemDoseQtyDTO;
import com.hxgy.nurexcute.dto.ArcItemRevLocDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseItemDTO;
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

public class OrderRevLocSpanAdapter extends BaseAdapter {

	Context context;
	List<ArcItemRevLocDTO> list;
	public OrderRevLocSpanAdapter(Context contex,List<ArcItemRevLocDTO> list){
		this.context=contex;
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
		convertView=LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
		TextView text = (TextView) convertView.findViewById(R.id.spinner_spdesc);
		ArcItemRevLocDTO dto = list.get(position);
		convertView.setTag(dto);
		text.setText(dto.getDesc());
		return convertView;
	}

}
