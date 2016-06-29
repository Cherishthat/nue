package com.hxgy.nurexcute.ui.frg;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.IBarInterface;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetLabNoAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetOrdAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteGetPhaOrdAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteRelationOrdAdapter;
import com.hxgy.nurexcute.adapter.ExcuteExcuteWHXROrdAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.AllPatOrdDTO;
import com.hxgy.nurexcute.dto.LabNoDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.PatientDetailDTO;
import com.hxgy.nurexcute.dto.PhaOrdDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.dto.WHXROrdDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ExcuteExcuteMain extends Fragment implements ExcuteContent , IBarInterface{

	public static final int WORTER=0;
	public static final int LIS=1;
	public static final int PHA=2;
	public static final int WHXR=3;
	private Context context;
	private PatientDTO patient;
	private MainActivity main;
	private ListView lv;
	private String grpCode;
	private TextView tvName;
	private TextView tvPatNo;
	private TextView txtBedNo;
	//private  BroadcastReceiver myReceiver;
	public ExcuteExcuteMain (){
		//myReceiver = new BarReceiver(this);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.excute_excute_main, null);
	}
   
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 context = getActivity().getApplicationContext();
		 patient=(PatientDTO) getArguments().getSerializable("patient");
		 grpCode=getArguments().getString("grpCode");
		 main= ((MainActivity)getActivity());
		
		
		 setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 
		((MainActivity)getActivity()).EnableMenu();
		init();
//		main.showExcute=true;
//		main.invalidateOptionsMenu();
		
	}



	private void init(){
		 lv=(ListView) getActivity().findViewById(R.id.excute_excute_lv);
		 tvName = (TextView) getActivity().findViewById(R.id.excute_excute_tvname);
		 tvPatNo = (TextView) getActivity().findViewById(R.id.excute_excute_tvpatno);
		 txtBedNo = (TextView) getActivity().findViewById(R.id.excute_excute_tvbed);
		 if(patient!=null) {
		 tvName.setText(patient.getName());
		 tvPatNo.setText(patient.getPatNo());
		 txtBedNo.setText(patient.getBedNo());
		 }
		
       int grp=Integer.parseInt(grpCode);
		switch (grp) {
		case WORTER: 
			getActivity().setTitle(R.string.menu_right_bar_worter);
			 if(patient!=null) {
			 getOrd();
			 }
			break;
		case LIS: 
			getActivity().setTitle(R.string.menu_right_bar_labno);
			 if(patient!=null) {
			getLabOrd();
			 }
			break;
		case PHA: 
			getActivity().setTitle(R.string.menu_right_bar_pha);
			 if(patient!=null) {
			getPhaOrd();
			 }
			break;
		case WHXR: 
			getActivity().setTitle(R.string.menu_right_bar_whxr);
			 if(patient!=null) {
			getWHXROrd();
			 }
			break;
			
		};
	}
	//查询当日所有输液
	private void getOrd() {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetOrd(patient.getAdm(),new OrdHander(this.context));
	}
	//查询病人所有检验(包括已执行)
	private void getLabOrd(){
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetLabNo(patient.getAdm(),new LabOrdHander(this.context));
	}
	//查询病人所有口服发药
	private void getPhaOrd(){
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetPhaOrd(patient.getAdm(),new PhaOrdHander(this.context));
	}
	//雾化吸入
	private void getWHXROrd(){
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetGetWHXROrd(patient.getAdm(),new WHXROrdHander(this.context));
	}
	
	
		
	@Override
	public void excute() {
		
	}
	@Override
	public void search() {
		
	}
	@Override
	public void chart() {
		
	}
class OrdHander extends  AsyncHttpResponseHandler{
	Context context =null;
    public OrdHander(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try {
		List<AllPatOrdDTO> p = gson.fromJson(result,new TypeToken<List<AllPatOrdDTO>>(){}.getType() );
		ExcuteExcuteGetOrdAdapter adapter = new ExcuteExcuteGetOrdAdapter(context, p);
		lv.setAdapter(adapter);
		}catch(Exception ex) {
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

class WHXROrdHander extends  AsyncHttpResponseHandler{
	Context context =null;
    public WHXROrdHander(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try {
		List<WHXROrdDTO> p = gson.fromJson(result,new TypeToken<List<WHXROrdDTO>>(){}.getType() );
		ExcuteExcuteWHXROrdAdapter adapter = new ExcuteExcuteWHXROrdAdapter(context, p);
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

class RelationOrdHander extends  AsyncHttpResponseHandler{
	Context context =null;
    public RelationOrdHander(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try {
		List<RelationOrdDTO> p = gson.fromJson(result,new TypeToken<List<RelationOrdDTO>>(){}.getType() );
		ExcuteExcuteRelationOrdAdapter adapter = new ExcuteExcuteRelationOrdAdapter(context, p);
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

class KFYRelationOrdHander extends  AsyncHttpResponseHandler{
	Context context =null;
    public KFYRelationOrdHander(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		
		Gson gson = new Gson();
		try {
		List<RelationOrdDTO> p = gson.fromJson(result,new TypeToken<List<RelationOrdDTO>>(){}.getType() );
		ExcuteExcuteRelationOrdAdapter adapter = new ExcuteExcuteRelationOrdAdapter(context, p);
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
    public ExcuteLabOrdHander(Context context) {
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
		    	getLabOrd();
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

class ExuteRelationOrdHander extends  AsyncHttpResponseHandler{
	Context context =null;
	String ordId,no,admId;
    public ExuteRelationOrdHander(Context context,String ordId,String no,String admId) {
    	this.context =context;
    	this.ordId=ordId;
    	this.no=no;
    	this.admId=admId;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		try {
		Gson gson = new Gson();
		List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
	    if( p.get(0).getCode().equals("0")) {
	    	UIHelper.ToastMessage(context, R.string.WaitExcuteList_sucess);
	    	ApiClient.RelationOrd(ordId,no,patient.getAdm(),new RelationOrdHander(this.context));
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
	    	ApiClient.KFYRelationOrd(this.barCode,this.admId,new KFYRelationOrdHander(this.context));
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

@Override
public void excuteLab(String barCode) {
	String ordStr="";
	main.setSupportProgressBarIndeterminateVisibility(true);
	ApiClient.LabOritem(barCode,patient.getAdm(),CurUser.userId,new ExcuteLabOrdHander(this.context));
	
}
@Override
public void excuteWhxr(String barCode) {
	String [] strArr=barCode.split("-");
	String ordId=strArr[0]+"||"+strArr[1];
	String no=strArr[2];
	main.setSupportProgressBarIndeterminateVisibility(true);
	ApiClient.ExuteRelationOrd(ordId, CurUser.userId, no,patient.getAdm(), new ExuteRelationOrdHander(this.context,ordId,no,patient.getAdm()));
	
}
@Override
public void excutePHA(String barCode) {

	main.setSupportProgressBarIndeterminateVisibility(true);
	ApiClient.ExuteKFY(barCode, CurUser.userId, patient.getAdm(),new ExuteKFYHander(this.context,barCode,patient.getAdm()));
	
}
@Override
public void excuteWorter(String barcode) {
	String [] strArr=barcode.split("-");
	String ordId=strArr[0]+"||"+strArr[1];
	String no=strArr[2];
	
	main.setSupportProgressBarIndeterminateVisibility(true);
	ApiClient.ExuteRelationOrd(ordId, CurUser.userId, no, patient.getAdm(),new ExuteRelationOrdHander(this.context,ordId,no,patient.getAdm()));
	
}
@Override
public void getPatient(String barCode) {
	gotoPatient(barCode);
}

private void gotoPatient(String barCode ) {
	 ApiClient.GetPatientBetialByPatNo(barCode,CurUser.userDepartmentID, new GetPatientHandler(this.context));
}

class GetPatientHandler extends AsyncHttpResponseHandler{
		Context context;
		
		public GetPatientHandler(Context context){
			this.context=context;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			
			try {
			Gson gson = new Gson();
			List<PatientDetailDTO> pList = gson.fromJson(result,new TypeToken<List<PatientDetailDTO>>(){}.getType() );
			if(pList.size()==0){
				return ;
			}
			PatientDetailDTO p = pList.get(0);
			patient.setAdm(p.getAdm());
			patient.setAdmDoc(p.getAdmDoc());
			patient.setAdmReason(p.getAdmreason());
			patient.setAge(p.getAge());
			patient.setBedNo(p.getBed());
			patient.setCtlocDesc(p.getCtloc());
			patient.setDia(p.getDia());
			patient.setName(p.getName());
			patient.setPatId(p.getPatId());
			patient.setPatNo(p.getRegno());
			patient.setSex(p.getSex());
			//patient.setSexId(p.getse);
			
			tvName.setText(p.getName());
			tvPatNo.setText(p.getRegno());
			txtBedNo.setText(p.getBed());
//		    UIHelper.ToastMessage(context,p.getName());
			}catch (Exception ex) {
				UIHelper.ToastMessage(context, ex.getMessage());
			}
			
		}
		
		public void onFailure(Throwable throwable, String arg1) {
			super.onFailure(throwable, arg1);
			UIHelper.ToastFailMessage( this.context, throwable);
			if(main!=null){
				main.setSupportProgressBarIndeterminateVisibility(false);
			}
		}
}


}
