package com.hxgy.nurexcute.ui.frg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.ui.MainActivity;

public class MenuRighBar extends Fragment {
	
   public static final int PATIENTINFO=1;
   public static final int EXCUTE=2;
   public static final int WORTER=3;
   public static final int INJECT=16;    //注射用药
   public static final int BLOODSUGAR=17; //血糖录入
   public static final int SIGHS=4;
   public static final int SETTING=5;
   public static final int ABOUT=6;
   public static final int EXAME=7;
   public static final int ORDER=8;
   public static final int SELECTDEP=9; //科室选择
   public static final int LABNO=10; //检验
   public static final int PHA=11; //口服发药
   public static final int WHXR=12; //雾化吸入
   public static final int APPLYRT=13;
   public static final int ORDERTODAY=14; //当日医嘱查询
   public static final int WordMessage=15;// 交班信息
   
   
   private LinearLayout patientinfo;
   private LinearLayout excute;
   private LinearLayout depselect;
   private LinearLayout setting;
   private LinearLayout sings;
   private LinearLayout worter;
   private LinearLayout inject;
   private LinearLayout bloodsugar;
   private LinearLayout labno;
   private LinearLayout whxr;
   private LinearLayout pha;
   private LinearLayout examreport;
   private LinearLayout applyrt;
   private LinearLayout orderenter;
   private LinearLayout ordertoday;
   private LinearLayout wordmessage;
   
   
   private int comm=SELECTDEP;
   
	
	public int getComm() {
	return comm;
}

public void setComm(int comm) {
	this.comm = comm;
}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_right_bar, null);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		BarClick click=new BarClick();
		BarClickNoAdm clickNoAdm=new BarClickNoAdm();
		
		patientinfo = (LinearLayout) getActivity().findViewById(R.id.menu_right_bar_patientinfo);
		patientinfo.setOnClickListener(click);
		
		excute = (LinearLayout) getActivity().findViewById(R.id.menu_right_bar_excute);
		excute.setOnClickListener(click);
		
		depselect=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_depselect);
		depselect.setOnClickListener(clickNoAdm);
		
		setting=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_setting);
		setting.setOnClickListener(clickNoAdm);
		
		sings=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_Signs);
		sings.setOnClickListener(click);
		
		worter=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_worter);
		worter.setOnClickListener(click);

		inject=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_injectDrug);
		inject.setOnClickListener(click);

		bloodsugar=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_BloodSugar);
		bloodsugar.setOnClickListener(click);

		ordertoday=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_ordertoday);
		ordertoday.setOnClickListener(clickNoAdm);
		
		labno=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_labno);
		labno.setOnClickListener(click);
		
		whxr=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_whxr);
		whxr.setOnClickListener(click);
		
		pha=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_pha);
		pha.setOnClickListener(click);
		
		examreport=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_examreport);
		examreport.setOnClickListener(click);
		
		applyrt=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_applyrt);
		applyrt.setOnClickListener(click);
		
		orderenter=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_orderenter);
		orderenter.setOnClickListener(click);
		
		wordmessage=(LinearLayout) getActivity().findViewById(R.id.menu_right_bar_wordMessage);
		wordmessage.setOnClickListener(clickNoAdm);
	}
	
	class BarClickNoAdm implements OnClickListener{

		@Override
		public void onClick(View v) {
			MainActivity main = ((MainActivity) getActivity());
			switch (v.getId()) {
			case R.id.menu_right_bar_depselect:
				comm=SELECTDEP;
				break;
			case R.id.menu_right_bar_setting:
				comm=SETTING;
				break;
			case R.id.menu_right_bar_ordertoday:
				comm=ORDERTODAY;
				break;
			case R.id.menu_right_bar_wordMessage:
				comm=WordMessage;
				break;
			default:
				break;
			}
			
			main.swithContextNoAdm();
		}
		
	}
	
	class BarClick implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			MainActivity main = ((MainActivity) getActivity());
			switch (v.getId()) {
			case R.id.menu_right_bar_patientinfo:
				comm=PATIENTINFO;
				break;
			case R.id.menu_right_bar_excute:
				comm=EXCUTE;
				break;
			case R.id.menu_right_bar_depselect:
				comm=SELECTDEP;
				break;
			case R.id.menu_right_bar_setting:
				comm=SETTING;
				break;
			
			case R.id.menu_right_bar_Signs:
				comm=SIGHS;
				break;
			case R.id.menu_right_bar_worter:
				comm=WORTER;
				break;
			case R.id.menu_right_bar_injectDrug:
				comm=INJECT;
				break;
			case R.id.menu_right_bar_BloodSugar:
				comm=BLOODSUGAR;
				break;
			case R.id.menu_right_bar_labno:
				comm=LABNO;
				break;
			case R.id.menu_right_bar_whxr:
				comm=WHXR;
				break;
			case R.id.menu_right_bar_pha:
				comm=PHA;
				break;
			case R.id.menu_right_bar_examreport:
				comm=EXAME;
				break;
			case R.id.menu_right_bar_applyrt:
				comm=APPLYRT;
				break;
			case R.id.menu_right_bar_orderenter:
				comm=ORDER;
				break;
		
			 
			default:
				break;
			}
			
			main.switchContent();
//			main.getSlidingMenu().showContent();
		}
		
	}

	

	
	



	

	
	

	
}