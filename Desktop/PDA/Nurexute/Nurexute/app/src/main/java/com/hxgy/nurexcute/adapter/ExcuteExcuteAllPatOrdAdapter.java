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

public class ExcuteExcuteAllPatOrdAdapter  extends BaseAdapter {

	Context context;
	List<AllPatOrdDTO> allPatOrdList;
	HashMap<String, String> map = new HashMap<String, String>();


	public ExcuteExcuteAllPatOrdAdapter(Context context,List<AllPatOrdDTO> allPatOrdList){
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
		return allPatOrdList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return allPatOrdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_excute_header_d, null);
		TextView bedno=(TextView) convertView.findViewById(R.id.excute_excute_head4_bedno);
		TextView seqno=(TextView) convertView.findViewById(R.id.excute_excute_head4_seqno);
		TextView arcimdesc=(TextView) convertView.findViewById(R.id.excute_excute_head4_arcimdes);
		TextView excutnur=(TextView) convertView.findViewById(R.id.excute_excute_head4_excutnur);
		TextView excutdt=(TextView) convertView.findViewById(R.id.excute_excute_head4_excutdt);
		TextView dose=(TextView) convertView.findViewById(R.id.excute_excute_head4_dose);
		TextView meth=(TextView) convertView.findViewById(R.id.excute_excute_head4_meth);
		TextView phrreq=(TextView) convertView.findViewById(R.id.excute_excute_head4_phrreq);
		TextView priority=(TextView) convertView.findViewById(R.id.excute_excute_head4_priority);
		
		AllPatOrdDTO allPatOrdDTO = allPatOrdList.get(position);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(allPatOrdDTO.getDisposeStatCode())));
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bedno.setText(allPatOrdDTO.getBedNo());
		seqno.setText(allPatOrdDTO.getSeqNo());
		arcimdesc.setText(allPatOrdDTO.getArcimDes());
		excutnur.setText(allPatOrdDTO.getExecUser());
		excutdt.setText(allPatOrdDTO.getExecDatTim());
		dose.setText(allPatOrdDTO.getDose());
		meth.setText(allPatOrdDTO.getMeth());
		phrreq.setText(allPatOrdDTO.getPhrreq());
		priority.setText(allPatOrdDTO.getPriority());
		return convertView;
	}
}
