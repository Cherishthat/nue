package com.hxgy.nurexcute.adapter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;

public class ExcuteExcuteAdapterA  extends BaseAdapter {

	Context context;
	List<ExcuteOrderDTO> excuteOrderList;
	HashMap<String, String> map = new HashMap<String, String>();


	public List<ExcuteOrderDTO> getExcuteOrderList() {
		return excuteOrderList;
	}

	public void setExcuteOrderList(List<ExcuteOrderDTO> excuteOrderList) {
		this.excuteOrderList = excuteOrderList;
	}

	public ExcuteExcuteAdapterA(Context contex,List<ExcuteOrderDTO> excuteOrderList){
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
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_excute_header_a, null);
		
		TextView arcimdesc=(TextView) convertView.findViewById(R.id.exam_arcimdesc);
		TextView excutedt=(TextView) convertView.findViewById(R.id.excute_excute_head1_excutedt);
		TextView excutenur=(TextView) convertView.findViewById(R.id.excute_excute_head1_excutenur);
		TextView dose=(TextView) convertView.findViewById(R.id.excute_excute_head1_dose);
		TextView meth=(TextView) convertView.findViewById(R.id.excute_excute_head1_meth);
		TextView seqno=(TextView) convertView.findViewById(R.id.excute_excute_head1_seqno);
		TextView extim=(TextView) convertView.findViewById(R.id.excute_excute_head1_extim);

		
//		arcimdesc.setTypeface(App.tf);
//		excutedt.setTypeface(App.tf);
//		excutenur.setTypeface(App.tf);
//		dose.setTypeface(App.tf);
//		meth.setTypeface(App.tf);
//		seqno.setTypeface(App.tf);
//		extim.setTypeface(App.tf);
		ExcuteOrderDTO excuteOrder = excuteOrderList.get(position);
		Field field=null;
		try {
//			 field=R.color.class.getField(excuteOrder.getDisposeStatCode());
//			 int i= field.getInt(new R.color());
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(excuteOrder.getDisposeStatCode())));
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		serNo.setText(excuteOrder.getSerNo());
//		orderName.setText(excuteOrder.getOrderName());
//		orderBefore.setText(excuteOrder.getOrderBefore());
//		uom.setText(excuteOrder.getUom());
//		orderDate.setText(excuteOrder.getOrderDate());
//		convertView.setTag(excuteOrder);
		return convertView;
	}
}
