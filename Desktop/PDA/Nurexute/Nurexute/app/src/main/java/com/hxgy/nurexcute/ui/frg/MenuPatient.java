package com.hxgy.nurexcute.ui.frg;

import java.util.List;

import com.example.comm.jason.RunServerMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.BedAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.widget.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuPatient extends Fragment {
	Gson gson ;
	private String selectAdm ="";
	private BedAdapter bedAdapter;
	private List<PatientDTO> p =null;
	private PullToRefreshListView pRpatientList;
	private String adm=null;
	private LinearLayout viewRoot ;



	public String getAdm() {
		return adm;
	}

	public void setAdm(String adm) {
		this.adm = adm;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.patient_list, container,false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	     init();
	}

	@Override
	public void onStart() {
		super.onStart();
		Application app=getActivity().getApplication();
	}
	private void initBedList() {
		 bedAdapter = new BedAdapter(getActivity(), p,selectAdm);
		 pRpatientList.setAdapter(bedAdapter);
		

	}
	
	class PatientItemClick implements OnItemClickListener{
        
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int pos,
				long arg3) {
			
			PatientDTO patientdto =(PatientDTO) v.getTag();
			adm=patientdto.getAdm();
			selectAdm=adm;
			bedAdapter.setSelectAdm(selectAdm);
			MainActivity main = (MainActivity)getActivity();
		
			main.setPatient(patientdto);
			bedAdapter.notifyDataSetInvalidated();
			itemClick();
		}
		
	}
	public void barRefresh(String admId) {
		
		for (PatientDTO po : p) {
			if(po.getAdm().equals(admId)) {
				selectAdm=admId;
				bedAdapter.setSelectAdm(selectAdm);
				bedAdapter.notifyDataSetInvalidated();
				break;
			}
		}
	}
	
	private void init() {
		viewRoot=(LinearLayout)getActivity().findViewById(R.id.lv_bed_root);
		 pRpatientList = (PullToRefreshListView)getActivity().findViewById(R.id.lv_bed_list);
    
       if (p==null){
				refresh();
		   }else{
			   initBedList();
		   }
			
		gson=new Gson();
		pRpatientList.setOnItemClickListener(new PatientItemClick());
//		refrash();
		pRpatientList.setOnRefreshListener(new com.hxgy.nurexcute.widget.PullToRefreshListView.OnRefreshListener() {
				@Override
				public void onRefresh() {
					
					refresh();
				}
			});
	}
	



class QueryHander extends AsyncHttpResponseHandler{
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		System.out.print(result);
		try {
		p = gson.fromJson(result,new TypeToken<List<PatientDTO>>(){}.getType() );
		initBedList();
		pRpatientList.onRefreshComplete();
//		((MainActivity)getActivity()).showSecondaryMenu();
		}catch(Exception ex) {
			UIHelper.ToastMessage(getActivity(), ex.getMessage());
		}
	}

	@Override
	public void onFinish() {
		super.onFinish();
		((MainActivity)getActivity()).loadfinish();
	}

	@Override
	public void onFailure(Throwable arg0, String arg1) {
		// TODO Auto-generated method stub
		super.onFailure(arg0, arg1);
	}
	
}

	

	
	private void itemClick(){
		switchFragment(adm);
	}
	
	private void switchFragment(String admId) {
		if (getActivity() == null)
			return;
		
		MainActivity main = (MainActivity)getActivity();
//		main.setAdmId(admId);
		//Fragment fragment = new PatientInfo(getActivity().getApplicationContext(),admId);
		main.switchContent();
		
		
	}
	
	public void refresh(){
//		CurUser.userDepartmentID="179";
		if ( CurUser.userDepartmentID==null||CurUser.userDepartmentID.equals("")){
			return ;
		}
		selectAdm="";
		adm=null;
		((MainActivity)getActivity()).loading();
	
		SharedPreferences prefer =getActivity().getSharedPreferences("APPSET", Activity.MODE_PRIVATE);
		String bedGroup=prefer.getString(CurUser.userDepartmentID, "");
		ApiClient.getpatientList(CurUser.userDepartmentID, bedGroup,new QueryHander());
		
	}

	
}
