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
import com.hxgy.nurexcute.dto.AllPatOrdDTO;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;

public class ExcuteExcuteLabAdapter  extends BaseAdapter {

	Context context;
	List<RelationOrdDTO> relationOrdList;
	HashMap<String, String> map = new HashMap<String, String>();

	public ExcuteExcuteLabAdapter(Context context,List<RelationOrdDTO> relationOrdList){
		this.context=context;
		this.relationOrdList=relationOrdList;
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
		return relationOrdList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return relationOrdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_excute_header_lis, null);
		TextView arcimdesc=(TextView) convertView.findViewById(R.id.headlis_arcimdesc);
		TextView excutedt=(TextView) convertView.findViewById(R.id.excute_excute_lis_excutedt);
		TextView excutenur=(TextView) convertView.findViewById(R.id.excute_excute_lis_excutenur);
		TextView seqno=(TextView) convertView.findViewById(R.id.excute_excute_lis_seqno);
		TextView extim=(TextView) convertView.findViewById(R.id.excute_excute_lis_extim);
		TextView ordStatus=(TextView) convertView.findViewById(R.id.excute_excute_lis_orderstatus);
	

		RelationOrdDTO relationOrdDTO = relationOrdList.get(position);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(relationOrdDTO.getDisposeStatCode())));
		} catch (Exception  e) {
			e.printStackTrace();
		}
		arcimdesc.setText(relationOrdDTO.getArcimDes());
		excutedt.setText(relationOrdDTO.getExTime());
		excutenur.setText(relationOrdDTO.getExcutNur());
		seqno.setText(relationOrdDTO.getSeqNo());
		extim.setText(relationOrdDTO.getExcutDateTime());
		ordStatus.setText(relationOrdDTO.getOrderStatus());
		return convertView;
	}
}
