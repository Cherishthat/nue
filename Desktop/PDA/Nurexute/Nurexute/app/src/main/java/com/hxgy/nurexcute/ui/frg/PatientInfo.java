package com.hxgy.nurexcute.ui.frg;

import java.util.List;

import com.example.comm.jason.RunServerMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.PatientDetailDTO;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PatientInfo extends Fragment {
    LinearLayout viewroot;
	TextView patient_info_patno;
	TextView patient_info_name;
	TextView patinet_info_bed;
	TextView patient_info_sex;
	TextView patient_info_age;
	TextView patient_info_indate;
	TextView patient_info_admdoc;
	TextView patient_info_admloc;
	TextView patient_info_binglihao;
	TextView patient_info_admreason;
	TextView patient_info_lianxiren;
	TextView patient_info_dianhua;

	TextView patient_info_zongfeiyong;
	TextView patient_info_yajin;
	TextView patient_info_dia;
	TextView patient_info_hljb;
	TextView patient_info_ys;
		

	private String admId;
	private PatientDTO patient;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patient=(PatientDTO) getArguments().getSerializable("patient");
		setRetainInstance(true);
		
	 


	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.patient_info, null);
	}

	@Override
	public void onStart() {
		super.onStart();
		Application app=getActivity().getApplication();
//		((App)app).changeFont(viewroot);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
		
		init();

	} 
	
	private void init() {
		 context = getActivity().getApplicationContext();
		 
//		admId=getArguments().getString("admId");
		 if(patient==null) {
			 return ;
		 }
		 admId=patient.getAdm();
		 getActivity().setTitle(R.string.menu_right_bar_patinetInfo);
		 viewroot=(LinearLayout)getActivity().findViewById(R.id.patient_info_root);
		 patient_info_patno=(TextView)getActivity().findViewById(R.id.patient_info_patno);
		 patient_info_name=(TextView)getActivity().findViewById(R.id.patinet_info_name);
		 patinet_info_bed=(TextView)getActivity().findViewById(R.id.patinet_info_bed);
		 patient_info_sex=(TextView)getActivity().findViewById(R.id.patient_info_sex);
		 patient_info_age=(TextView)getActivity().findViewById(R.id.patient_info_age);
		 patient_info_indate=(TextView)getActivity().findViewById(R.id.patient_info_indate);
		 patient_info_admdoc=(TextView)getActivity().findViewById(R.id.patient_info_admdoc);
		 patient_info_admloc=(TextView)getActivity().findViewById(R.id.patient_info_admloc);
		 patient_info_binglihao=(TextView)getActivity().findViewById(R.id.patient_info_binglihao);
		 patient_info_admreason=(TextView)getActivity().findViewById(R.id.patient_info_admreason);
		 patient_info_lianxiren=(TextView)getActivity().findViewById(R.id.patient_info_lianxiren);
		 patient_info_dianhua=(TextView)getActivity().findViewById(R.id.patient_info_dianhua);
		 patient_info_zongfeiyong=(TextView)getActivity().findViewById(R.id.patient_info_zongfeiyong);
		 patient_info_yajin=(TextView)getActivity().findViewById(R.id.patient_info_yajin);
		 patient_info_dia=(TextView)getActivity().findViewById(R.id.patient_info_dia);
		 patient_info_hljb=(TextView)getActivity().findViewById(R.id.patient_info_hljb);
		 patient_info_ys=(TextView)getActivity().findViewById(R.id.patient_info_ys);
		 
		 
		 GetPatientDetail();
	}
	
	class DataHandler extends AsyncHttpResponseHandler{
		Context context;
		
		public DataHandler(Context context){
			this.context=context;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			
			
			Gson gson = new Gson();
			try {
			List<PatientDetailDTO> pList = gson.fromJson(result,new TypeToken<List<PatientDetailDTO>>(){}.getType() );
			if(pList.size()==0){
				return ;
			}
			PatientDetailDTO p = pList.get(0);
			patient_info_patno.setText(p.getRegno());
			patient_info_name.setText(p.getName());
			patinet_info_bed.setText(p.getBed());
			patient_info_sex.setText(p.getSex());
			patient_info_age.setText(p.getAge());
			patient_info_indate.setText(p.getInDate());
			patient_info_admdoc.setText(p.getAdmDoc());
			patient_info_admloc.setText(p.getCtloc());
			patient_info_binglihao.setText(p.getMedno());
			patient_info_lianxiren.setText(p.getContact());
			patient_info_dianhua.setText(p.getMobo());
			patient_info_zongfeiyong.setText(p.getFee());
			patient_info_yajin.setText(p.getDeposit());
			patient_info_admreason.setText(p.getAdmreason());
			patient_info_dia.setText(p.getDia());
			patient_info_hljb.setText(p.getHljb());
			patient_info_ys.setText(p.getYs());
			}catch (Exception ex) {
				UIHelper.ToastMessage(context, ex.getMessage());
			}
			
			
			
		}
 
		@Override
		public void onFinish() {
			super.onFinish();
			MainActivity main = ((MainActivity)getActivity());
			if(main!=null){
					
				main.setSupportProgressBarIndeterminateVisibility(false);
			}
		}

		@Override
		public void onFailure(Throwable arg0, String arg1) {
			super.onFailure(arg0, arg1);
			
			UIHelper.ToastFailMessage(this.context, arg0);
		}
	}
	
	private void GetPatientDetail(){
		if(this.admId==""){
			return ;
		}
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.getPatientDetail(this.admId, CurUser.userDepartmentID, new DataHandler(this.context));
	}
	
	

}
