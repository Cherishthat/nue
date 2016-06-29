package com.hxgy.nurexcute.adapter;

import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.PatientDTO;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BedAdapter extends BaseAdapter {

	Context context;
	List<PatientDTO> patList;
	String selectAdm="";
	LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams (35 ,35);
	public BedAdapter(Context contex,List<PatientDTO> patList ,String selectAdm){
		this.context=contex;
		this.patList=patList;
		this.selectAdm=selectAdm;
		
	}
	
	@Override
	public int getCount() {
		return patList.size();
	}

	@Override
	public Object getItem(int position) {
		return patList.get(position);
	}

	

	public String getSelectAdm() {
		return selectAdm;
	}

	public void setSelectAdm(String selectAdm) {
		this.selectAdm = selectAdm;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.layout_bed_item, null);
		TextView patNo=(TextView) convertView.findViewById(R.id.lb_bed_item_patno);
		TextView patName=(TextView) convertView.findViewById(R.id.lb_bed_item_name);
		TextView bedNo=(TextView) convertView.findViewById(R.id.lb_bed_item_bedno);
		TextView dia=(TextView) convertView.findViewById(R.id.lb_bed_item_dia);
	    LinearLayout imageContent = (LinearLayout) convertView.findViewById(R.id.lb_bed_item_img);
	    
	
		PatientDTO patient = patList.get(position);
		
		if(selectAdm.equals(patient.getAdm())){
			    convertView.setBackgroundResource(R.color.itemgreen);
			    convertView.setAlpha(200);
			    patNo.setTextColor(Color.WHITE);
				patName.setTextColor(Color.WHITE);
				bedNo.setTextColor(Color.WHITE);
				dia.setTextColor(Color.WHITE);
		}
		String imgs=patient.getImgname();
		String [] imgArr=imgs.split(",");
		for(int i=0;i<imgArr.length;i++) {
			ImageView imgv = new ImageView(context);
			imgv.setLayoutParams(parm);
			if(imgArr[i].equals("dangerous")){
				imgv.setImageResource(R.drawable.bingwei);
			}
			if(imgArr[i].equals("caretypeone")){
				imgv.setImageResource(R.drawable.caretypeone);
			}
			if(imgArr[i].equals("caretypesp1")){
				imgv.setImageResource(R.drawable.caretypesp1);
			}
			if(imgArr[i].equals("caretypesp2")){
				imgv.setImageResource(R.drawable.caretypesp2);
			}
			if(imgArr[i].equals("dhccpwflag")){
				imgv.setImageResource(R.drawable.dhccpwflag);
			}
			if(imgArr[i].equals("dhcmedccfeedback")){
				imgv.setImageResource(R.drawable.dhcmedccfeedback);
			}
			if(imgArr[i].equals("dhcmedccfeedbackok")){
				imgv.setImageResource(R.drawable.dhcmedccfeedbackok);
			}
			if(imgArr[i].equals("dhcyxflag")){
				imgv.setImageResource(R.drawable.dhcyxflag);
			}
			if(imgArr[i].equals("docdisch")){
				imgv.setImageResource(R.drawable.docdisch);
			}
			if(imgArr[i].equals("epdnotreport")){
				imgv.setImageResource(R.drawable.epdnotreport);
			}
			if(imgArr[i].equals("epdreport")){
				imgv.setImageResource(R.drawable.epdreport);
			}
			if(imgArr[i].equals("feverinthreeday")){
				imgv.setImageResource(R.drawable.feverinthreeday);
			}
			if(imgArr[i].equals("jzyjj")){
				imgv.setImageResource(R.drawable.jzyjj);
			}
			if(imgArr[i].equals("neworder")){
				imgv.setImageResource(R.drawable.neworder);
			}
			if(imgArr[i].equals("newpatient")){
				imgv.setImageResource(R.drawable.newpatient);
			}
			if(imgArr[i].equals("regalert")){
				imgv.setImageResource(R.drawable.regalert);
			}
			if(imgArr[i].equals("stopord")){
				imgv.setImageResource(R.drawable.stopord);
			}
			if(imgArr[i].equals("suscept")){
				imgv.setImageResource(R.drawable.suscept);
			}
			if(imgArr[i].equals("todayoperation")){
				imgv.setImageResource(R.drawable.todayoperation);
			}
			if(imgArr[i].equals("weikai")){
				imgv.setImageResource(R.drawable.weikai);
			}
			
			imageContent.addView(imgv);
		}
		patNo.setText(patient.getPatNo());
		patName.setText(patient.getName());
		bedNo.setText(patient.getBedNo());
		dia.setText(patient.getDia());
		convertView.setTag(patient);

		return convertView;
	}

}
