package com.hxgy.nurexcute.adapter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
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

public class ExcuteExcuteGetOrdAdapter  extends BaseAdapter {

	Context context;
	List<AllPatOrdDTO> allPatOrdList;
	HashMap<String, String> map = new HashMap<String, String>();

	public ExcuteExcuteGetOrdAdapter(Context context,List<AllPatOrdDTO> allPatOrdList){
		this.context=context;
		this.allPatOrdList=allPatOrdList;
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
		System.out.println("getCount");
		return allPatOrdList.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("getItem");
		// TODO Auto-generated method stub
		return allPatOrdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		System.out.println("getItemId");
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
		
		AllPatOrdDTO allPatOrdDTO = allPatOrdList.get(position);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(allPatOrdDTO.getDisposeStatCode())));
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seqno.setText(allPatOrdDTO.getSeqNo());
		arcimdes.setText(allPatOrdDTO.getArcimDes());
		execuser.setText(allPatOrdDTO.getExecUser());
		labdate.setText(allPatOrdDTO.getExecDatTim());
		dose.setText(allPatOrdDTO.getDose());
		meth.setText(allPatOrdDTO.getMeth());
		phfreq.setText(allPatOrdDTO.getPhrreq());
		priority.setText(allPatOrdDTO.getPriority());
		return convertView;
	}
}
