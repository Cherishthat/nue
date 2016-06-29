package com.hxgy.nurexcute.ui.frg;


import java.util.Calendar;
import java.util.List;


import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;

import com.hxgy.nurexcute.adapter.QueryTodayAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.OrderTypeDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;



import com.loopj.android.http.AsyncHttpResponseHandler;

public class QueryTodayOrder  extends Fragment implements ExcuteContent{
	private Context context;
	private String orderType;
	protected MainActivity main;
	private ListView querytodylist ;
   private String now="";
	private Spinner spinner;
	QueryTodayAdapter lvAdapter =null;
//	List<ExcuteOrderDTO> excuteOrdeList=null;
	View listHeard=null;


	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.querytodayorder, null);
	}
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		
		
		setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 main= ((MainActivity)getActivity());
		 main.EnableMenu();
		Calendar cal = Calendar.getInstance();
        this.orderType="Default";
       
	    init();
			
	}

	private void init() {
		Calendar cal = Calendar.getInstance();
		this.now=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);;
		getActivity().setTitle(R.string.query_today_title);
		querytodylist=(ListView) getActivity().findViewById(R.id.querytody_list);
		spinner=(Spinner) getActivity().findViewById(R.id.querytody_ordertype);
		listHeard= LayoutInflater.from(this.context).inflate(R.layout.querytoday_head, null);
		listHeard.setBackgroundColor(Color.parseColor("#4169E1"));
//		listHeard.setOnClickListener(new ListHeard());
	
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
		
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				OrderTypeDTO dto=(OrderTypeDTO) arg1.getTag();
				 orderType=dto.getRw();
				 getExcuteOrder();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
			}
			
		});
		getOrdType();
		querytodylist.addHeaderView(listHeard);
			
	}
	private void getExcuteOrder(){
		if (this.orderType.equals("")) return ;
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.getExcuteOrder("",this.now,this.now,this.orderType,CurUser.userId,CurUser.userDepartmentID,"", new ExcuteOrderHander(this.context));
	}
	
	 private void getOrdType(){
		 main.setSupportProgressBarIndeterminateVisibility(true);
	    	ApiClient.getordertypeList(CurUser.userGroupID, CurUser.userId, new OrdTypeHander(this.context));
	    }
	 class OrdTypeHander extends  AsyncHttpResponseHandler{

	    	Context context;
	    	public OrdTypeHander(Context context){
	    		this.context=context;
	    	}
	    	@Override
	    	public void onFailure(Throwable throwable, String arg1) {
	    		super.onFailure(throwable, arg1);
	    		UIHelper.ToastFailMessage(this.context, throwable);
	    	
	    	}

	    	@Override
	    	public void onFinish() {
	    		super.onFinish();
	    		if(main!=null){
					
					main.setSupportProgressBarIndeterminateVisibility(false);
				}
	    	}

	    	@Override
	    	public void onSuccess(String result) {
	    		super.onSuccess(result);
	    		
	    		Gson gson = new Gson();
	    		try{
	    		List<OrderTypeDTO> list = gson.fromJson(result,new TypeToken<List<OrderTypeDTO>>(){}.getType() );
	    		spinner.setAdapter(new OrdTypeAdpter(context,list));
	    		}catch(Exception ex){
	    			System.out.print(ex.getMessage());
	    			UIHelper.ToastMessage(this.context, ex.getMessage());
	    		}
	    	}
	    	
	    }
	    
	    class OrdTypeAdpter extends BaseAdapter{

	    	Context context;
	    	List<OrderTypeDTO> list;
	    	public OrdTypeAdpter(Context context,List<OrderTypeDTO> list){
	    		this.context = context;
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
				convertView=LayoutInflater.from(context).inflate(R.layout.excute_search_item, null);
				TextView name=(TextView) convertView.findViewById(R.id.excute_search_item_name);
				TextView code=(TextView) convertView.findViewById(R.id.excute_search_item_code);
				OrderTypeDTO item = list.get(position);
				name.setText(item.getDesc());
				code.setText(item.getRw());
				convertView.setTag(item);
				return convertView;
			}
	    	
	    }
	    
	    class ExcuteOrderHander extends AsyncHttpResponseHandler{
	    	Context context =null;
	        public ExcuteOrderHander(Context context) {
	        	this.context =context;
	        }
	    	@Override
	    	public void onSuccess(String result) {
	    		super.onSuccess(result);
	    		Gson gson = new Gson();
	    		try {
	    		 List<ExcuteOrderDTO> excuteOrderList = gson.fromJson(result,new TypeToken<List<ExcuteOrderDTO>>(){}.getType() );
	    		 lvAdapter = new QueryTodayAdapter(this.context, excuteOrderList);
	    		 querytodylist.setAdapter(lvAdapter);
	    		} catch (Exception ex) {
	    			UIHelper.ToastMessage(context, ex.getMessage());
	    		}
	    	}

	    	@Override
	    	public void onFailure(Throwable throwable, String arg1) {
	    		super.onFailure(throwable, arg1);
	    		UIHelper.ToastFailMessage( this.context, throwable);
	    		
	    	}
	    	@Override
	    	public void onFinish() {
	    		
	    		if(main!=null){
	    				
	    			main.setSupportProgressBarIndeterminateVisibility(false);
	    		}
	    	}
	    }
	    	
	
	

	

	



	


@Override
public void search() {
	
}



@Override
public void chart() {
	// TODO Auto-generated method stub
	
}



@Override
public void excute() {
	
	
}
}

