package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;

import com.hxgy.nurexcute.dto.WardMessageDTO;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WardMessageAdapter extends BaseAdapter {

	Context context;
	List<WardMessageDTO> messageList;
	public WardMessageAdapter(Context contex,List<WardMessageDTO> messageList){
		this.context=contex;
		this.messageList=messageList;
	}
	
	@Override
	public int getCount() {
		return messageList.size();
	}

	@Override
	public Object getItem(int position) {
		return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.ward_message_item, null);
		TextView content = (TextView) convertView.findViewById(R.id.ward_message_item_content);
		TextView username = (TextView) convertView.findViewById(R.id.ward_message_item_username);
		TextView datetime = (TextView) convertView.findViewById(R.id.ward_message_item_datetime);
		WardMessageDTO dto = messageList.get(position);
		convertView.setTag(dto);
		content.setText(dto.getContent());
		username.setText(dto.getUserName());
		datetime.setText(dto.getDateTime());
		return convertView;
	}

}
