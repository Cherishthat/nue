package com.hxgy.nurexcute.ui.frg;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;

public class ExcuteExcuteMainA extends Fragment implements ExcuteContent {

	public static final int WORTER=0;
	public static final int LIS=1;
	public static final int PHA=2;
	public static final int WHXR=3;
	protected Context context;
	protected PatientDTO patient;
	protected MainActivity main;
	protected Button btnok,btncancel;
	
	protected ListView lv;
	protected LinearLayout bottom;
	protected String grpCode;
	protected TextView tvName;
	protected TextView tvPatNo;
	protected TextView txtBedNo;
	protected String BARCODE;
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
		 context = getActivity();
		 patient=(PatientDTO) getArguments().getSerializable("patient");
		
		 grpCode=getArguments().getString("grpCode");
		 main= ((MainActivity)getActivity());
		 main.showExcute=false;
		 main.invalidateOptionsMenu();
		
		 setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
		 lv=(ListView) getActivity().findViewById(R.id.excute_excute_lv);
		 tvName = (TextView) getActivity().findViewById(R.id.excute_excute_tvname);
		 tvPatNo = (TextView) getActivity().findViewById(R.id.excute_excute_tvpatno);
		 txtBedNo = (TextView) getActivity().findViewById(R.id.excute_excute_tvbed);
		 btnok=(Button)getActivity().findViewById(R.id.excute_excute_btnok);
		 btncancel=(Button)getActivity().findViewById(R.id.excute_excute_btncancel);
		 bottom=(LinearLayout) getActivity().findViewById(R.id.excute_excute_bottom);
		 bottom.setVisibility(View.GONE);
		 if(patient!=null) {
		 tvName.setText(patient.getName());
		 tvPatNo.setText(patient.getPatNo());
		 txtBedNo.setText(patient.getBedNo());
		 }
		 init();
		 getData();
		 btncancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 bottom.setVisibility(View.GONE);
			}
		});
		 
		 btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				exuteBar(BARCODE);
			}
		});
	}
	
	public void init(){
		
	}
	public void getData(){
		main.showExcute=false;
		bottom.setVisibility(View.GONE);
		main.invalidateOptionsMenu();
	}
	
	public void showBarData(String barCode){
		BARCODE=barCode;
		main.showExcute=true;
		bottom.setVisibility(View.VISIBLE);
		main.invalidateOptionsMenu();
		
	}
	
	public void showBtn(String barCode) {
		BARCODE=barCode;
		main.showExcute=true;
		bottom.setVisibility(View.VISIBLE);
		main.invalidateOptionsMenu();
	}
	
	public void exuteBar(String barCode){
		
	}
	
	@Override
	public void excute() {
		DialogTool.createConfirmDialog(context, "提示信息", "是否执行", "执行", "取消",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						exuteBar(BARCODE);
					}

				}, new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				}, DialogTool.NO_ICON).show();
	}

	@Override
	public void search() {
		
	}

	@Override
	public void chart() {
		
	}

}
