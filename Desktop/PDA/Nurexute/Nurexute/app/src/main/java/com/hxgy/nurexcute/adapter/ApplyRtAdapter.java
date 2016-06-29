package com.hxgy.nurexcute.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ApplyRTDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.GroupLoc;
import com.hxgy.nurexcute.ui.ApplyRtReasonDia;

public class ApplyRtAdapter  extends BaseAdapter {

	Context context;
	List<ApplyRTDTO> applyRTList;
	Activity parent;
	List<BaseDataDTO> reasonList;
	
	public ApplyRtAdapter(Context context,Activity parent, List<ApplyRTDTO> applyRTList,List<BaseDataDTO> reasonList){
		this.context=context;
		this.applyRTList=applyRTList;
		this.parent=parent;
		this.reasonList=reasonList;
		
	}
	
	@Override
	public int getCount() {
		return applyRTList.size();
	}

	@Override
	public Object getItem(int position) {
		return applyRTList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.applyfordrugretrun_item, null);
		TextView itmdesc=(TextView) convertView.findViewById(R.id.applyrt_itmdesc);
		TextView ordqty=(TextView) convertView.findViewById(R.id.applyrt_ordqty);
		TextView ordStartDate=(TextView) convertView.findViewById(R.id.applyrt_ordStartDate);
		TextView prescno=(TextView) convertView.findViewById(R.id.applyrt_prescno);
		EditText reason=(EditText) convertView.findViewById(R.id.applyrt_reason);
		EditText reqqty=(EditText) convertView.findViewById(R.id.applyrt_reqqty);
		TextView status=(TextView) convertView.findViewById(R.id.applyrt_status);
		TextView unit=(TextView) convertView.findViewById(R.id.applyrt_unit);
		ApplyRTDTO dto = applyRTList.get(position);
		
		itmdesc.setText(dto.getDesc());
		ordqty.setText(dto.getDispQty());
		ordStartDate.setText(dto.getOrdStartDate());
		prescno.setText(dto.getPrescNo());
		reason.setText(dto.getReason());
		reason.setKeyListener(null);
		reason.setTag(dto);
		reason.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showReasonDia();
			}
		});
		reqqty.setText(dto.getReqQty());
		reqqty.addTextChangedListener(new ReasonTextWatcher(this.context, reqqty,dto,convertView));
		reason.addTextChangedListener(new ReasonTextWatcher(this.context, reason,dto,convertView));
		status.setText(dto.getStatus());
		unit.setText(dto.getUom());
		if ((!dto.getReqQty().isEmpty())&&Float.parseFloat(dto.getReqQty())>0&&(dto.getReason()!=null && !dto.getReason().isEmpty())) {
			
			convertView.setBackgroundColor(context.getResources().getColor(R.color.lemonyellow));
			convertView.setAlpha(200);
		}
		return convertView;
	}
	
	private void showReasonDia(){
		Intent intent = new Intent(context, ApplyRtReasonDia.class);
		intent.putExtra("reason", (ArrayList<BaseDataDTO>)this.reasonList);
		parent.startActivityForResult(intent, 200);
	}

	
	
	class ReasonTextWatcher implements android.text.TextWatcher{
		TextView tv;
		ApplyRTDTO dto;
		Context context;
		
		View v;
		public ReasonTextWatcher(Context context, TextView tv,ApplyRTDTO dto,View v){
        	this.tv=tv;
        	this.dto=dto;
        	this.context=context;
        	this.v=v;
        }
		@Override
		public void afterTextChanged(Editable s) {
//			UIHelper.ToastMessage(context, tv.getText().toString());
			if(tv.getId()==R.id.applyrt_reason){
			dto.setReason(this.tv.getText().toString());}
			else {
				 int pos = s.length() - 1;
				 if ((!s.toString().isEmpty())&& Float.parseFloat(s.toString())>Float.parseFloat(dto.getDispQty())){
					 s.delete(pos,pos+1);
				 }
				 
				 dto.setReqQty(this.tv.getText().toString());
			}
		
			if (!dto.getReqQty().isEmpty()&&Float.parseFloat(dto.getReqQty())>0&&(dto.getReason()!=null && !dto.getReason().isEmpty())) {
				
				this.v.setBackgroundColor(context.getResources().getColor(R.color.lemonyellow));
				this.v.setAlpha(200);
			}else {
				this.v.setBackgroundColor(context.getResources().getColor(R.color.white));
				this.v.setAlpha(255);
			}
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			
		}
		
	}
}
