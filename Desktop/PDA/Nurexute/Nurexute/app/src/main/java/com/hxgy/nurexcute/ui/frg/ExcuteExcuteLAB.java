package com.hxgy.nurexcute.ui.frg;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetLabNoAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteLabAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.LabNoDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;



public class ExcuteExcuteLAB extends ExcuteExcuteMainA {

	@Override
	public void init(){
		getActivity().setTitle(R.string.menu_right_bar_labno);
	}
	@Override
	public void getData() {
		super.getData();
		if(patient!=null) {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetLabNo(patient.getAdm(),new LabOrdHander(this.context));
		}
	}

	@Override
	public void exuteBar(String barCode) {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.ExuteLabOritem(barCode,patient.getAdm(),CurUser.userId,new ExcuteBarHander(this.context));
	}
	
	@Override
	public void showBarData(String barCode){
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.LabOritem(barCode,patient.getAdm(),CurUser.userId,new ExcuteLabOrdHander(this.context,barCode));
	}
	
	class LabOrdHander extends  AsyncHttpResponseHandler{
		Context context =null;
	    public LabOrdHander(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try {
			List<LabNoDTO> p = gson.fromJson(result,new TypeToken<List<LabNoDTO>>(){}.getType() );
			ExcuteExcuteGetLabNoAdapter adapter = new ExcuteExcuteGetLabNoAdapter(context, p);
			lv.setAdapter(adapter);
			}catch (Exception ex) {
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
	class ExcuteLabOrdHander extends  AsyncHttpResponseHandler{
		Context context =null;
		String barCode="";
	    public ExcuteLabOrdHander(Context context,String barCode) {
	    	this.context =context;
	    	this.barCode=barCode;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try {
			List<RelationOrdDTO> p = gson.fromJson(result,new TypeToken<List<RelationOrdDTO>>(){}.getType() );
			ExcuteExcuteLabAdapter adapter = new ExcuteExcuteLabAdapter(context, p);
			lv.setAdapter(adapter);
			if(p.size()>0){
				 showBtn(this.barCode);
				}
			}catch (Exception ex) {
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
	
	class ExcuteBarHander extends  AsyncHttpResponseHandler{
		Context context =null;
	    public ExcuteBarHander(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try{
			List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
			 if( p.get(0).getCode().equals("0")) {
			    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_sucess);
//			    	getLabOrd();
			    	getData();
			    }else{
			    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_failure);
			    	UIHelper.ToastMessage(context, p.get(0).getMessage());
			    }
		
			}catch (Exception ex) {
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

}
