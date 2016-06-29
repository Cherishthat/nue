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

public class ExcuteMainLVAdapter extends BaseAdapter {

	Context context;
	List<ExcuteOrderDTO> excuteOrderList;
	HashMap<String, String> map = new HashMap<String, String>();

	public List<ExcuteOrderDTO> getExcuteOrderList() {
		return excuteOrderList;
	}

	public void setExcuteOrderList(List<ExcuteOrderDTO> excuteOrderList) {
		this.excuteOrderList = excuteOrderList;
	}

	public ExcuteMainLVAdapter(Context contex,List<ExcuteOrderDTO> excuteOrderList){
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
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_main_lv_item, null);
		ExcuteOrderDTO excuteOrder=excuteOrderList.get(position);
			
		
		TextView serNo=(TextView) convertView.findViewById(R.id.excute_main_head_SerNo);
		CheckBox orderName=(CheckBox) convertView.findViewById(R.id.excute_main_head_OrderName);
		orderName.setOnCheckedChangeListener(new MyCheckedChangeListener(excuteOrder));
		
		TextView orderBefore=(TextView) convertView.findViewById(R.id.excute_main_head_OrderBefore);
		TextView uom=(TextView) convertView.findViewById(R.id.excute_main_head_Uom);
		TextView orderDate=(TextView) convertView.findViewById(R.id.excute_main_head_OrderDate);
		TextView orderExeDate=(TextView) convertView.findViewById(R.id.excute_main_head_OrderExeDate);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(excuteOrder.getDisposeStatCode())));
		} catch (Exception  e) {
			e.printStackTrace();
		}
		
		serNo.setText(excuteOrder.getSerNo());
		orderName.setText(excuteOrder.getOrderName());
		orderName.setChecked(excuteOrder.isChecked());
//		orderName.setBackgroundResource(R.color.white);
		orderBefore.setText(excuteOrder.getOrderBefore());
		uom.setText(excuteOrder.getUom());
		orderDate.setText(excuteOrder.getOrderDate());
		orderExeDate.setText(excuteOrder.getOrderExeDate());
		convertView.setTag(excuteOrder);
		return convertView;
	}
	class MyCheckedChangeListener implements OnCheckedChangeListener{
		ExcuteOrderDTO dto;
		public MyCheckedChangeListener(ExcuteOrderDTO dto){
			this.dto=dto;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			dto.setChecked(isChecked);
			
		}
		
	}


}
