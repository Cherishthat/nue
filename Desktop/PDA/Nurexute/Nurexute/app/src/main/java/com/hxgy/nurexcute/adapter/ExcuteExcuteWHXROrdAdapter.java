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
import com.hxgy.nurexcute.dto.PhaOrdDTO;
import com.hxgy.nurexcute.dto.LabNoDTO;
import com.hxgy.nurexcute.dto.WHXROrdDTO;
/*
 * 查出病人当日雾化吸入
 */
public class ExcuteExcuteWHXROrdAdapter  extends BaseAdapter {

	Context context;
	List<WHXROrdDTO> whxrOrdList;
	HashMap<String, String> map = new HashMap<String, String>();

	public ExcuteExcuteWHXROrdAdapter(Context context,List<WHXROrdDTO> whxrOrdList){
		this.context=context;
		this.whxrOrdList=whxrOrdList;
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
		return whxrOrdList.size();
	}

	@Override
	public Object getItem(int position) {
		return whxrOrdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_excute_header_c, null);
		
		TextView seqno=(TextView) convertView.findViewById(R.id.orderenter_select_subcate);
		TextView arcimdes=(TextView) convertView.findViewById(R.id.excute_excute_head3_arcimdes);
		TextView execuser=(TextView) convertView.findViewById(R.id.orderenter_selectuom);
		TextView labdate=(TextView) convertView.findViewById(R.id.orderenter_select_price);
		TextView dose=(TextView) convertView.findViewById(R.id.orderenter_select_stockqty);
		TextView meth=(TextView) convertView.findViewById(R.id.orderenter_select_genericname);
		TextView phfreq=(TextView) convertView.findViewById(R.id.orderenter_select_insu);
		TextView priority=(TextView) convertView.findViewById(R.id.excute_excute_head3_priority);
		
		WHXROrdDTO whxrOrdDTO = whxrOrdList.get(position);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(whxrOrdDTO.getDisposeStatCode())));
		} catch (Exception  e) {
			e.printStackTrace();
		}
		seqno.setText(whxrOrdDTO.getSeqNo());
		arcimdes.setText(whxrOrdDTO.getArcimDes());
		execuser.setText(whxrOrdDTO.getExecUser());
		labdate.setText(whxrOrdDTO.getExecDatTim());
		dose.setText(whxrOrdDTO.getDose());
		meth.setText(whxrOrdDTO.getMeth());
		phfreq.setText(whxrOrdDTO.getPhfreq());
		priority.setText(whxrOrdDTO.getPriority());
		
		return convertView;
	}
}
