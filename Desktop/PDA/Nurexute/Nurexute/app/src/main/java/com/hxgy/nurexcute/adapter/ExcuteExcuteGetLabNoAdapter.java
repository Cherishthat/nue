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
import com.hxgy.nurexcute.dto.LabNoDTO;
/*
 * 查出病人有几管血
 */
public class ExcuteExcuteGetLabNoAdapter  extends BaseAdapter {

	Context context;
	List<LabNoDTO> labNoDTOList;
	HashMap<String, String> map = new HashMap<String, String>();


	public ExcuteExcuteGetLabNoAdapter(Context context,List<LabNoDTO> labNoDTOList){
		this.context=context;
		this.labNoDTOList=labNoDTOList;
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
		return labNoDTOList.size();
	}

	@Override
	public Object getItem(int position) {
		return labNoDTOList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.excute_excute_header_b, null);
		
		TextView labepisodeno=(TextView) convertView.findViewById(R.id.excute_excute_head2_labepisodeno);
		TextView arcimdesc=(TextView) convertView.findViewById(R.id.excute_excute_head2_arcimdesc);
		TextView labcpt=(TextView) convertView.findViewById(R.id.applyrt_ordqty);
		TextView labdate=(TextView) convertView.findViewById(R.id.applyrt_reqqty);
		TextView stdatetime=(TextView) convertView.findViewById(R.id.applyrt_unit);
		TextView code=(TextView) convertView.findViewById(R.id.applyrt_status);
		TextView ordStatus=(TextView) convertView.findViewById(R.id.applyrt_orderstatus);
		
		LabNoDTO labNoDTO = labNoDTOList.get(position);
		try {
			 
			 convertView.setBackgroundColor(Color.parseColor(map.get(labNoDTO.getDisposeStatCode())));
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arcimdesc.setText(labNoDTO.getArcIMDes());
		labepisodeno.setText(labNoDTO.getLabEpisodeNo());
		labcpt.setText(labNoDTO.getLabCpt());
		labdate.setText(labNoDTO.getLabDate()+" "+labNoDTO.getLabTime());
		stdatetime.setText(labNoDTO.getStdateTime());
		code.setText(labNoDTO.getCode());
		ordStatus.setText(labNoDTO.getOrderStatus());
		
		return convertView;
	}
}
