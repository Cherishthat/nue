package com.hxgy.nurexcute.ui.frg;

import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.example.comm.jason.RunServerMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ApplyRtAdapter;
import com.hxgy.nurexcute.adapter.ExamReportAdapter;
import com.hxgy.nurexcute.adapter.PhalocAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.TipHelper;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ApplyRTDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.PatientDetailDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.ui.ApplyRtReasonDia;
import com.hxgy.nurexcute.ui.ExamResult;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.ExcuteSearch;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.ui.frg.ExcuteMain.LVLongClickListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;


public class ApplyRT extends Fragment implements ExcuteContent{
		
	private TextView tvbed;
	private TextView tvpatno;
	private TextView tvname;
	private ListView orderlist;
	private PatientDTO patient;
	private Context context;
	private Spinner phaloc;
	private List<BaseDataDTO> resonList;
	MainActivity main ;
	private HashMap<String, List<ApplyRTDTO>> map = new HashMap<String, List<ApplyRTDTO>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patient=(PatientDTO) getArguments().getSerializable("patient");
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.applyfordrugretrun, null);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
		init();

	} 
	
	private void init() {
		 main = ((MainActivity)getActivity());
		 context = getActivity();
		 
		 getActivity().setTitle(R.string.menu_right_bar_applyrt);
		 tvbed= (TextView) getActivity().findViewById(R.id.applyrt_tvbed);
		 tvpatno= (TextView) getActivity().findViewById(R.id.applyrt_tvpatno);
		 tvname= (TextView) getActivity().findViewById(R.id.applyrt_tvname);
		 phaloc=(Spinner) getActivity().findViewById(R.id.applyrt_phaloc);
		
		 orderlist=(ListView)getActivity().findViewById(R.id.applyrt_orderlist);
		
		 phaloc.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int arg2, long arg3) {
				String key=((BaseDataDTO)v.getTag()).getRowid();
				if(map.get(key)==null){
					
					if(main!=null){
						main.setSupportProgressBarIndeterminateVisibility(true);
					}
				   ApiClient.GetApplyRetrun(patient.getPatNo(), patient.getAdm(), ((BaseDataDTO)v.getTag()).getRowid(), CurUser.userDepartmentID, new DataHandler(context,key));
				}else {
					orderlist.setAdapter(new ApplyRtAdapter(context ,getActivity(),map.get(key),resonList));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			 
		 });
		
		 if(patient!=null){
		   tvbed.setText(patient.getBedNo());
		   tvpatno.setText(patient.getPatNo());
		   tvname.setText(patient.getName());
		   GetInitData();
		 }
	
		 
}
	private void GetInitData(){
		ApiClient.GetRetReason(new ReasonHandler(context));
	}
	private void GetPhaLoc(){
		ApiClient.GetPhaLoc(CurUser.hospId, new PhalocHandler(context));
	}
	class ReasonHandler extends AsyncHttpResponseHandler{
		Context context;
		String key;
		
		public ReasonHandler(Context context){
			this.context=context;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			resonList=gson.fromJson(result,new TypeToken<List<BaseDataDTO>>(){}.getType() );
			 GetPhaLoc();
			}catch(Exception ex) {
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
	
	
	class PhalocHandler extends AsyncHttpResponseHandler{
		Context context;
		
		public PhalocHandler(Context context){
			this.context=context;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<BaseDataDTO> pList = gson.fromJson(result,new TypeToken<List<BaseDataDTO>>(){}.getType() );
			phaloc.setAdapter(new PhalocAdapter(context,pList));
			}catch(Exception ex){
				UIHelper.ToastMessage(context, ex.getMessage());
			}
//			ApiClient.GetRetReason()
//			GetOrderDetail();
			
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
	
	class DataHandler extends AsyncHttpResponseHandler{
		Context context;
		String key;
		
		public DataHandler(Context context,String key){
			this.context=context;
			this.key=key;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try{
			Gson gson = new Gson();
			List<ApplyRTDTO> applyRtList = gson.fromJson(result,new TypeToken<List<ApplyRTDTO>>(){}.getType() );
			map.put(key, applyRtList);
			orderlist.setAdapter(new ApplyRtAdapter(context ,getActivity(),applyRtList,resonList));
			}catch(Exception ex){
				UIHelper.ToastMessage(context, ex.getMessage());
			}
		}
 
		@Override
		public void onFinish() {
			super.onFinish();
			
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
	
	

	@Override
	public void excute() {
		DialogTool.createConfirmDialog(context, "提示", "退药申请", "申请", "取消", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String oedisStr="";
		        String prescnoStr="";
		        String pharowidStr="";
		        String reqqtyStr="";
		        String reasonStr="";
		        boolean flg=false;
				Collection<List<ApplyRTDTO>> colletion= map.values();
				for (List<ApplyRTDTO> list : colletion) {
					for (ApplyRTDTO applyRTDTO : list) {
						if(applyRTDTO.getReasonDr()!=null&&!applyRTDTO.getReasonDr().isEmpty()&&applyRTDTO.getReqQty()!=null&&applyRTDTO.getReqQty()!=""&&Float.parseFloat(applyRTDTO.getReqQty())>0){
							oedisStr=oedisStr+"^"+applyRTDTO.getOedis();
							prescnoStr=prescnoStr+"^"+applyRTDTO.getPrescNo();
							reqqtyStr=reqqtyStr+"^"+applyRTDTO.getReqQty();
							reasonStr=reasonStr+"^"+applyRTDTO.getReasonDr();
							pharowidStr=pharowidStr+"^"+applyRTDTO.getPharowid();
							flg=true;
						}
					}
				}
//				UIHelper.ToastMessage(context, reasonStr);
				if (flg){
				map.clear();
				if(main!=null){
					main.setSupportProgressBarIndeterminateVisibility(true);
				}
				ApiClient.ApplyRt(oedisStr, prescnoStr, pharowidStr, reqqtyStr, reasonStr, CurUser.userId, CurUser.userDepartmentID, new ApplyRtHander(context));
			   }else{
				   UIHelper.ToastMessage(context, "没有退的医嘱");
			   }
			}
			
		}, new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		}, DialogTool.NO_ICON).show();
		
        
	}
	
class ApplyRtHander  extends AsyncHttpResponseHandler{
	Context context;
	
	public ApplyRtHander(Context context){
		this.context=context;
	}
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try{
		List<ResultMessDTO> pList = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO dto=pList.get(0);
		//		phaloc.setAdapter(new PhalocAdapter(context,pList));
		if (dto.getCode().equals("1")){
			UIHelper.ToastMessage(context, "保存成功");
		}else {
			UIHelper.ToastMessage(context, "保存失败");
		}
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
	@Override
	public void search() {
	}

	@Override
	public void chart() {
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(200==resultCode){
		
//		UIHelper.ToastMessage(context, "asdfasf");
		TextView tv=(TextView) orderlist.findFocus();
		BaseDataDTO dto= (BaseDataDTO) data.getSerializableExtra("checkReason");
		tv.setText(dto.getDesc());
		ApplyRTDTO appDTO=(ApplyRTDTO)tv.getTag();
		appDTO.setReasonDr(dto.getRowid());
		appDTO.setReason(dto.getDesc());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
