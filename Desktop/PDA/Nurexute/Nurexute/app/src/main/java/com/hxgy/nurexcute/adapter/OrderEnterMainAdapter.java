package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.ArcItemDoseQtyDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseItemDTO;
import com.hxgy.nurexcute.dto.OrderDocEnterDTO;
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

public class OrderEnterMainAdapter extends BaseAdapter {

	Context context;
	List<OrderDocEnterDTO> list;
	public OrderEnterMainAdapter(Context contex,List<OrderDocEnterDTO> list){
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
		convertView=LayoutInflater.from(context).inflate(R.layout.orderenter_main_item, null);
		TextView desc = (TextView) convertView.findViewById(R.id.orderenter_main_desc);
		TextView priorrowid = (TextView) convertView.findViewById(R.id.orderenter_main_priorrowid);
		
		TextView doseqty = (TextView) convertView.findViewById(R.id.orderenter_input_doseqty);
		TextView doseuom = (TextView) convertView.findViewById(R.id.orderenter_main_doseuom);
		TextView durz = (TextView) convertView.findViewById(R.id.orderenter_main_durz);
		TextView freq = (TextView) convertView.findViewById(R.id.orderenter_main_freq);
		TextView instr = (TextView) convertView.findViewById(R.id.orderenter_main_instr);
		TextView masterseqno = (TextView) convertView.findViewById(R.id.orderenter_main_masterseqno);
		TextView packqty = (TextView) convertView.findViewById(R.id.orderenter_main_packqty);
		TextView seqno = (TextView) convertView.findViewById(R.id.orderenter_main_seqno);
		TextView skintest = (TextView) convertView.findViewById(R.id.orderenter_main_skintest);
		TextView recdep = (TextView) convertView.findViewById(R.id.orderenter_main_recdep);
		TextView firstdaytimes = (TextView) convertView.findViewById(R.id.orderenter_main_firstdaytimes);
		
		OrderDocEnterDTO dto = list.get(position);
		convertView.setTag(dto);
		priorrowid.setText(dto.getPriority().getDesc());
		desc.setText(dto.getDesc().getDesc());
		doseqty.setText(dto.getOrderdoseqty());
		doseuom.setText(dto.getOrderdoseuom().getDesc());
		durz.setText(dto.getOrderdur().getDesc());
		firstdaytimes.setText(dto.getOrderfirstdaytimes());
		freq.setText(dto.getOrderfreq().getDesc());
		instr.setText(dto.getOrderinstr().getDesc());
		masterseqno.setText(dto.getOrdermasterseqno());
		packqty.setText(dto.getOrderpackqty()+dto.getOrderpackuom());
		seqno.setText(dto.getSeqno());
		recdep.setText(dto.getOrderrecdep().getDesc());
		skintest.setText(dto.getAction().getDesc());
		
		
		return convertView;
	}

}
