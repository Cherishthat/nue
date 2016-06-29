package com.hxgy.nurexcute.ui.frg;

import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetPhaOrdAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteRelationOrdAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PhaOrdDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;



public class ExcuteExcutePHA extends ExcuteExcuteMainA {

	@Override
public void init(){
		getActivity().setTitle(R.string.menu_right_bar_pha);
	}
	@Override
	public void getData() {
		super.getData();
		if(patient!=null) {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetPhaOrd(patient.getAdm(),new PhaOrdHander(this.context));
		}
	}

	@Override
	public void exuteBar(String barCode) {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.ExuteKFY(barCode, CurUser.userId, patient.getAdm(),new ExuteKFYHander(this.context,barCode,patient.getAdm()));
	}
	@Override
	public void showBarData(String barCode){
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.KFYRelationOrd(barCode,patient.getAdm(),new KFYRelationOrdHander(this.context,barCode));
	   
	}
	
	class KFYRelationOrdHander extends  AsyncHttpResponseHandler{
		Context context =null;
		String barCode="";
	    public KFYRelationOrdHander(Context context,String barCode) {
	    	this.context =context;
	    	this.barCode=barCode;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			
			Gson gson = new Gson();
			try {
			List<RelationOrdDTO> p = gson.fromJson(result,new TypeToken<List<RelationOrdDTO>>(){}.getType() );
			ExcuteExcuteRelationOrdAdapter adapter = new ExcuteExcuteRelationOrdAdapter(context, p);
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
//	class ExuteRelationOrdHander extends  AsyncHttpResponseHandler{
//		Context context =null;
//		String ordId,no,admId;
//	    public ExuteRelationOrdHander(Context context,String ordId,String no,String admId) {
//	    	this.context =context;
//	    	this.ordId=ordId;
//	    	this.no=no;
//	    	this.admId=admId;
//	    }
//		@Override
//		public void onSuccess(String result) {
//			super.onSuccess(result);
//			try {
//			Gson gson = new Gson();
//			List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
//		    if( p.get(0).getCode().equals("0")) {
//		    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_sucess);
//		    	getData();
//		    }else{
//		    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_failure);
//		    	UIHelper.ToastMessage(context, p.get(0).getMessage());
//		    }
//			}catch (Exception ex) {
//				UIHelper.ToastMessage(context, ex.getMessage());
//			}
//		}
//
//		@Override
//		public void onFailure(Throwable throwable, String arg1) {
//			super.onFailure(throwable, arg1);
//			UIHelper.ToastFailMessage( this.context, throwable);
//			if(main!=null){
//				main.setSupportProgressBarIndeterminateVisibility(false);
//			}
//			
//		}
//		@Override
//		public void onFinish() {
//			
//		}
//	}
//	
	class PhaOrdHander extends  AsyncHttpResponseHandler{
		Context context =null;
	    public PhaOrdHander(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try {
			List<PhaOrdDTO> p = gson.fromJson(result,new TypeToken<List<PhaOrdDTO>>(){}.getType() );
			ExcuteExcuteGetPhaOrdAdapter adapter = new ExcuteExcuteGetPhaOrdAdapter(context, p);
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
	
	class ExuteKFYHander extends  AsyncHttpResponseHandler{
		Context context =null;
		String barCode="";
		String admId="";
	    public ExuteKFYHander(Context context,String barCode,String admId) {
	    	this.context =context;
	    	this.barCode=barCode;
	    	this.admId=admId;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try{
			List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		    if( p.get(0).getCode().equals("0")) {
		    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_sucess);
		    	getData();
		    	//		    	ApiClient.KFYRelationOrd(this.barCode,this.admId,new KFYRelationOrdHander(this.context));
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
			if(main!=null){
				main.setSupportProgressBarIndeterminateVisibility(false);
			}
			
		}
		@Override
		public void onFinish() {
			
		}
	}

}
