package com.hxgy.nurexcute.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.ExamOrderDTO;

public class ExamReportAdapter  extends BaseAdapter {

	Context context;
	List<ExamOrderDTO> examOrderList;


	public ExamReportAdapter(Context context,List<ExamOrderDTO> examOrderList){
		this.context=context;
		this.examOrderList=examOrderList;
		
	}
	
	@Override
	public int getCount() {
		return examOrderList.size();
	}

	@Override
	public Object getItem(int position) {
		return examOrderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.exam_report_item, null);
		TextView arcimdesc=(TextView) convertView.findViewById(R.id.exam_arcimdesc);
		TextView itemdate=(TextView) convertView.findViewById(R.id.exam_itemdate);
		TextView itemstatus=(TextView) convertView.findViewById(R.id.exam_itemstatus);
		TextView locname=(TextView) convertView.findViewById(R.id.exam_locname);
		TextView memo=(TextView) convertView.findViewById(R.id.exam_memo);
		TextView studyno=(TextView) convertView.findViewById(R.id.exam_studyno);
		TextView yxret=(TextView) convertView.findViewById(R.id.exam_yxret);
		TextView isill=(TextView) convertView.findViewById(R.id.exame_isill);
		TextView ordertype=(TextView) convertView.findViewById(R.id.exam_ordertype);
		
		ExamOrderDTO dto = examOrderList.get(position);
		convertView.setTag(dto);
		arcimdesc.setText(dto.getItemName());
		itemdate.setText(dto.getItemDate());
		itemstatus.setText(dto.getItemStatus());
		locname.setText(dto.getLocName());
		memo.setText(dto.getMemo());
		studyno.setText(dto.getStudyNo());
		yxret.setText(dto.getYxret());
		isill.setText(dto.getIsIll());
		ordertype.setText(dto.getOrdCat());
		
		return convertView;
	}
}
