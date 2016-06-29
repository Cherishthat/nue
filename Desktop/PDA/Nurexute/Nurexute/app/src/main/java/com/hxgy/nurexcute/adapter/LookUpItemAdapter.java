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
import com.hxgy.nurexcute.dto.ArcItemDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.GroupLoc;
import com.hxgy.nurexcute.ui.ApplyRtReasonDia;

public class LookUpItemAdapter  extends BaseAdapter {

	Context context;
	List<ArcItemDTO> arcItemList;

	
	public LookUpItemAdapter(Context context, List<ArcItemDTO> arcItemList){
		this.context=context;
		this.arcItemList =arcItemList;
		
	}
	
	@Override
	public int getCount() {
		return arcItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return arcItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=LayoutInflater.from(context).inflate(R.layout.orderenter_select_item, null);
		TextView arcimdes=(TextView) convertView.findViewById(R.id.orderenter_select_arcimdes);
		TextView subcate=(TextView) convertView.findViewById(R.id.orderenter_select_subcate);
		TextView price=(TextView) convertView.findViewById(R.id.orderenter_select_price);
		TextView stockqty=(TextView) convertView.findViewById(R.id.orderenter_select_stockqty);
		TextView insu=(TextView) convertView.findViewById(R.id.orderenter_select_insu);
		TextView genericname=(TextView) convertView.findViewById(R.id.orderenter_select_genericname);
		ArcItemDTO dto = arcItemList.get(position);
		arcimdes.setText(dto.getArcDesc());
		subcate.setText(dto.getSubCategoryDesc());
		price.setText(dto.getPrice());
		stockqty.setText(dto.getStockQty());
		insu.setText(dto.getBxType());
		genericname.setText(dto.getGenericName());
		convertView.setTag(dto.getRowid());
		return convertView;
	}
	
		

}
