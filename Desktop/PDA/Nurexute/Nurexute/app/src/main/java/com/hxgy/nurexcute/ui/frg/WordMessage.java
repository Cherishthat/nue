package com.hxgy.nurexcute.ui.frg;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BedAdapter;
import com.hxgy.nurexcute.adapter.WardMessageAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.WardMessageDTO;
import com.hxgy.nurexcute.ui.EventInput;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.ui.frg.MenuPatient.QueryHander;
import com.hxgy.nurexcute.widget.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class WordMessage extends Fragment {
	
   private Context context ;
   private WardMessageAdapter wardMessageAdapter;
   private PullToRefreshListView listview;
	private List<WardMessageDTO> p =null;
	private EditText bgDate ;
	private EditText edDate;
	private Gson gson ;
	private Calendar cal = Calendar.getInstance();
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ward_message, null);
	}
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	  //	((MainActivity)getActivity()).EnableMenu();
		init();
			
	}
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			refresh();
		}
	};
   public void init(){
	   gson=new Gson();
	   getActivity().setTitle(R.string.WordMessage);
	   listview=(PullToRefreshListView)getActivity().findViewById(R.id.ward_message_list);
	   listview.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				
				refresh();
			}
		});
	   bgDate=(EditText)getActivity().findViewById(R.id.ward_input_bgdate);
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   String date=df.format(new Date(System.currentTimeMillis()));
	   bgDate.setText(date);
	   
	   bgDate.setKeyListener(null);
	   bgDate.addTextChangedListener(textWatcher);
	   edDate=(EditText)getActivity().findViewById(R.id.ward_input_eddate);
	   edDate.setText(date);
	   edDate.addTextChangedListener(textWatcher);
	   edDate.setKeyListener(null);
	   
	   if (p==null){
			refresh();
	   }else{
		   initMessageList();
	   }
	   
	   bgDate.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			new DatePickerDialog(context, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					bgDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					
				}
	            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
		}
		
	});
	   
	   edDate.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			new DatePickerDialog(context, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					edDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					
				}
	            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
		}
		
	});
   }
   
   public void refresh(){

	
		((MainActivity)getActivity()).loading();
	
		ApiClient.GetWardMessage(CurUser.userDepartmentID, this.bgDate.getText().toString(),this.edDate.getText().toString(),new QueryHander());
		
	}
   class QueryHander extends AsyncHttpResponseHandler{
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			
			try {
			p = gson.fromJson(result,new TypeToken<List<WardMessageDTO>>(){}.getType() );
			initMessageList();
			listview.onRefreshComplete();
//			((MainActivity)getActivity()).showSecondaryMenu();
			}catch(Exception ex) {
				UIHelper.ToastMessage(getActivity(), ex.getMessage());
			}
		}
		@Override
		public void onFinish() {
			((MainActivity)getActivity()).loadfinish();
		}
		}
   void initMessageList()	{
	   wardMessageAdapter = new WardMessageAdapter(getActivity(), p);
	   listview.setAdapter(wardMessageAdapter);
	}
}
