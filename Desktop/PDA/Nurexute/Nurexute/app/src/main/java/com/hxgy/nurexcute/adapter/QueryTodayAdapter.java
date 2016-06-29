package com.hxgy.nurexcute.adapter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.EventDTO;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class QueryTodayAdapter extends BaseAdapter {

	Context context;
	List<ExcuteOrderDTO> excuteOrderList;
	HashMap<String, String> map = new HashMap<String, String>();

	public List<ExcuteOrderDTO> getExcuteOrderList() {
		return excuteOrderList;
	}

	public void setExcuteOrderList(List<ExcuteOrderDTO> excuteOrderList) {
		this.excuteOrderList = excuteOrderList;
	}

	public QueryTodayAdapter(Context contex,List<ExcuteOrderDTO> excuteOrderList){
		this.context=contex;
		this.excuteOrderList=excuteOrderList;
		
		map.put("Immediate", "#98FB98");
		map.put("SkinTest", "#e00000");
		map.put("EpheDrin", "#FF8000");
		map.put("Discontinue", "#00BFFF");
		map.put("TempTest", "#b0ffb0");
		map.put("ExecDiscon", "#8080c0");
		map.put("Temp", "#ffffc0");
		map.put("LongNew", "#ffc0c0");
		map.put("Needless", "#ffffff");
		map.put("Exec", "#dfdfff");
		map.put("PreDiscon", "#a0a0a0");
		map.put("LongUnnew", "#ffd0ff");
		
	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return excuteOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return excuteOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.querytoday_item, null);
		ExcuteOrderDTO excuteOrder=excuteOrderList.get(position);
			
		
		TextView bedno=(TextView) convertView.findViewById(R.id.querytoday_head_bedno);
		TextView orderName=(TextView) convertView.findViewById(R.id.excute_main_head_OrderName);
	
		
		TextView orderBefore=(TextView) convertView.findViewById(R.id.excute_main_head_OrderBefore);
		TextView uom=(TextView) convertView.findViewById(R.id.excute_main_head_Uom);
		TextView orderstatus=(TextView) convertView.findViewById(R.id.querytoday_orderstatus);
		TextView orderExeDate=(TextView) convertView.findViewById(R.id.excute_main_head_OrderExeDate);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(excuteOrder.getDisposeStatCode())));
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		bedno.setText(excuteOrder.getBedno());
		orderName.setText(excuteOrder.getOrderName());
	
//		orderName.setBackgroundResource(R.color.white);
		orderBefore.setText(excuteOrder.getOrderBefore());
		uom.setText(excuteOrder.getUom());
		orderstatus.setText(excuteOrder.getOrderStatusDesc());
		String [] time=excuteOrder.getOrderExeDate().split(" ");
		if (time.length>1) {
			orderExeDate.setText(time[1]);
		}
		
		
		convertView.setTag(excuteOrder);
		return convertView;
	}
	
		
	}



