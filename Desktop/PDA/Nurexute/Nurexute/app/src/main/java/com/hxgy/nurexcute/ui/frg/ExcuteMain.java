package com.hxgy.nurexcute.ui.frg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteMainLVAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.TipHelper;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("ValidFragment")
public class ExcuteMain extends Fragment implements ExcuteContent{
	private Context context;
	private String bgDate;
	private String edDate;
	private String orderType;
	private PatientDTO patient;
	private ListView excutelv ;
	private TextView tvName;
	private TextView tvPatNo;
	private TextView txtBedNo;
	ExcuteMainLVAdapter lvAdapter =null;
	List<ExcuteOrderDTO> excuteOrdeList=null;
	View listHeard=null;
	private ArrayList<ExcuteOrderDTO> toExcuteOrders=new ArrayList<ExcuteOrderDTO>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.excute_main, null);
	}
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		patient=(PatientDTO) getArguments().getSerializable("patient");
		
		setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
		Calendar cal = Calendar.getInstance();
        this.orderType="Default";
        this.edDate= this.bgDate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
	    init();
			
	}

	
	private void initExcuteOrderList(List<ExcuteOrderDTO> p) {
		 lvAdapter = new ExcuteMainLVAdapter(this.context, p);
		 excutelv.setAdapter(lvAdapter);
	}
	
	
	private void init() {
		getActivity().setTitle(R.string.menu_right_bar_excute);
		 tvName = (TextView) getActivity().findViewById(R.id.excute_main_tvname);
		 tvPatNo = (TextView) getActivity().findViewById(R.id.excute_main_tvpatno);
		 txtBedNo = (TextView) getActivity().findViewById(R.id.excute_main_tvbed);
		excutelv = (ListView)getActivity().findViewById(R.id.excute_main_excutelv);
		listHeard= LayoutInflater.from(this.context).inflate(R.layout.excute_main_lv_head, null);
		listHeard.setBackgroundColor(Color.parseColor("#4169E1"));
		listHeard.setOnClickListener(new ListHeard());
		final CheckBox ordNameHeadItemCk=(CheckBox)listHeard.findViewById(R.id.excute_main_head_OrderName);
		ordNameHeadItemCk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (ExcuteOrderDTO iterable_element : excuteOrdeList) {
						iterable_element.setChecked(ordNameHeadItemCk.isChecked());
				}
				lvAdapter.notifyDataSetChanged();
			}
		
		});
		
		excutelv.addHeaderView(listHeard);
		excutelv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				
				CheckBox ordNameCk=(CheckBox) v.findViewById(R.id.excute_main_head_OrderName);
				if(ordNameCk.isChecked()) {
					ordNameCk.setChecked(false);
					
				}else {
					ordNameCk.setChecked(true);
				}
				
				ExcuteOrderDTO o=(ExcuteOrderDTO)v.getTag();
				
//				o.setChecked(ordNameCk.isChecked());
				Boolean flag=false;
				for (ExcuteOrderDTO iterable_element : excuteOrdeList) {
					if(iterable_element.getOrdID().equals(o.getOrdID())){
					    flag=true;
						iterable_element.setChecked(ordNameCk.isChecked());
					}
					if(flag&&o.getSerNo().equals(iterable_element.getLinkSerNo())){
						iterable_element.setChecked(ordNameCk.isChecked());
						break;
					}
				}
				lvAdapter.notifyDataSetChanged();
				
			}
			
		});
		
		
		
//		excutelv.setOnItemLongClickListener(new LVLongClickListener(this.context));
//		
//		excutelv.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
//				@Override
//				public void onRefresh() {
//					
//					getExcuteOrder();
//				}
//			});
		if(patient!=null) {
			 tvName.setText(patient.getName());
			 tvPatNo.setText(patient.getPatNo());
			 txtBedNo.setText(patient.getBedNo());
			 getExcuteOrder();
		}
		
	}
	
	class ListHeard implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			CheckBox ordNameCk=(CheckBox) v.findViewById(R.id.excute_main_head_OrderName);
			if(ordNameCk.isChecked()) {
				ordNameCk.setChecked(false);
				
			}else {
				ordNameCk.setChecked(true);
			}
			for (ExcuteOrderDTO iterable_element : excuteOrdeList) {
					iterable_element.setChecked(ordNameCk.isChecked());
			}
			lvAdapter.notifyDataSetChanged();
		}
	}
	
class LVLongClickListener implements OnItemLongClickListener{
	Context context=null;
	public LVLongClickListener(Context context){
		this.context=context;
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View v,
			int position, long id) {
		TipHelper.Vibrate(getActivity(), 100);
		ExcuteOrderDTO order = (ExcuteOrderDTO)v.getTag();
		if(position<2){
			return false;
		}
		toExcuteOrders.add(order);
		lvAdapter.getExcuteOrderList().remove(position-2);
		lvAdapter.notifyDataSetChanged();
		return false;
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
		excuteOrdeList = gson.fromJson(result,new TypeToken<List<ExcuteOrderDTO>>(){}.getType() );
		initExcuteOrderList(excuteOrdeList);
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
		MainActivity main = ((MainActivity)getActivity());
		if(main!=null){
				
			main.setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}
	
private String getOrdString(){
	String ordIDStr="";
	for (ExcuteOrderDTO o : lvAdapter.getExcuteOrderList()) {
		if(!o.isChecked()){
			continue;
		}
		if(o.getOrderName().contains("____")){
			continue;
		}
		
		ordIDStr=ordIDStr+"^"+o.getOrdID();
		
	}
	return ordIDStr;
}

private String getDisposeStatCode(){
	String disposeStatCodeStr="";
	for (ExcuteOrderDTO o : lvAdapter.getExcuteOrderList()) {
		if(!o.isChecked()){
			continue;
		}
		if(o.getOrderName().contains("____")){
			continue;
		}
		disposeStatCodeStr=disposeStatCodeStr+"^"+o.getDisposeStatCode();
	}
	return disposeStatCodeStr;
}

public void excute(){
	
	if(this.orderType.contains("PSD")) {
		DialogTool.createSkinDialog(context,"皮试结果","阴性","阳性","取消",new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
//				setSupportProgressBarIndeterminateVisibility(true);
				String ordIDStr=getOrdString();
				//String disposeStatCodeStr=getDisposeStatCode();
				
				if (ordIDStr.equals("")){
					UIHelper.ToastMessage(context, R.string.info_choice_order_to_exec);
					return;
				}
				
				((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
				ApiClient.SetSkinTest(ordIDStr, CurUser.userId, "N", new SkinTestHander(context));
				
			}
			
		}, new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String ordIDStr=getOrdString();
				if (ordIDStr.equals("")){
					UIHelper.ToastMessage(context, R.string.info_choice_order_to_exec);
					return;
				}
				
				((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
				ApiClient.SetSkinTest(ordIDStr, CurUser.userId, "Y", new SkinTestHander(context));
				
			}
			
		}, 
		 new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		},
		DialogTool.NO_ICON).show();
		return;
		
	}
	
	DialogTool.createConfirmDialog(context, "提示", "医嘱执行", "执行", "取消", new android.content.DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
//			setSupportProgressBarIndeterminateVisibility(true);
			String ordIDStr=getOrdString();
			String disposeStatCodeStr=getDisposeStatCode();
			
			if (ordIDStr.equals("")){
				UIHelper.ToastMessage(context, R.string.info_choice_order_to_exec);
				return;
			}
			
			((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
			ApiClient.excuteOrder(ordIDStr, "E", CurUser.userId, CurUser.userDepartmentID, disposeStatCodeStr, "", new ExcuteHander(context));
			
		}
		
	}, new android.content.DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
		
	}, DialogTool.NO_ICON).show();
	
	
}

@Override
public void search() {
	if(patient==null){
		UIHelper.ToastMessage(context, "请选择病人");
		return;
	}
	UIHelper.showWaitSearch(getActivity(), 200);
}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	if(200==resultCode){
		this.orderType=data.getExtras().getString("ordertype"); 
		this. bgDate=data.getExtras().getString("bgDate"); 
		this. edDate=data.getExtras().getString("edDate"); 
		final CheckBox ordNameHeadItemCk=(CheckBox)listHeard.findViewById(R.id.excute_main_head_OrderName);
		if(ordNameHeadItemCk!=null){
			ordNameHeadItemCk.setChecked(false);
		}
		getExcuteOrder();
	}
	if(201==resultCode){
		toExcuteOrders.clear();
	}
	super.onActivityResult(requestCode, resultCode, data);
}

private void getExcuteOrder(){
	
	((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);

	ApiClient.getExcuteOrder(patient.getAdm(),this.bgDate,this.edDate,this.orderType,CurUser.userId,CurUser.userDepartmentID,"", new ExcuteOrderHander(this.context));
}

@Override
public void chart() {
	// TODO Auto-generated method stub
	
}
class ExcuteHander extends AsyncHttpResponseHandler{

	Context context;
	public ExcuteHander(Context context){
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
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
		
	}

	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		
		Gson gson = new Gson();
		try{
		List<ResultMessDTO> list = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO o = list.get(0); 
		if (!o.getCode().equals("0")){
			UIHelper.ToastMessage(this.context, o.getMessage());
//			 Intent data=new Intent();  
//			 data.putExtra(); 
		
		
			
		}else{
			UIHelper.ToastMessage(this.context, R.string.WaitExcuteList_sucess);
			getExcuteOrder();
//			 setResult(201);  
//		     finish();  
		}
		}catch(Exception ex){
			UIHelper.ToastMessage(this.context, ex.getMessage());
		}
	}
	
}


class SkinTestHander extends AsyncHttpResponseHandler{

	Context context;
	public SkinTestHander(Context context){
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
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
	}

	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		
		Gson gson = new Gson();
		try{
		List<ResultMessDTO> list = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO o = list.get(0); 
//		if (!o.getCode().equals("0")){
			UIHelper.ToastMessage(this.context, o.getMessage());
//			 Intent data=new Intent();  
//			 data.putExtra(); 
		
			
//		}else{
//			UIHelper.ToastMessage(this.context, R.string.WaitExcuteList_sucess);
//			 setResult(201);  
//		     finish();  
//		}
		}catch(Exception ex){
			UIHelper.ToastMessage(this.context, ex.getMessage());
		}
	}
	
}
}
