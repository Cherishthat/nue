package com.hxgy.nurexcute.ui.frg;


import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;



public class Setting extends Fragment implements ExcuteContent{
	
   private Context context ;
   
   private LinearLayout settingSmartShell;
   private LinearLayout settingBedGroup;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.setting, null);
	}


   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
		init();
			
	}

   public void init(){
	   getActivity().setTitle(R.string.menu_right_bar_Setting);
	   settingSmartShell = (LinearLayout) getActivity().findViewById(R.id.Setting_SmartShell);
	   SettingClick listenter = new SettingClick();
	   settingSmartShell.setOnClickListener(listenter);
	   settingBedGroup=(LinearLayout) getActivity().findViewById(R.id.setting_bedgroupset);
	   settingBedGroup.setOnClickListener(listenter);
   }
   
   class SettingClick implements OnClickListener{

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Setting_SmartShell:
			UIHelper.showBlueSetting(getActivity());
			break;
		case R.id.setting_bedgroupset:
			UIHelper.showBedGroupSelect(getActivity());
		default:
			break;
		}
		
	}
	   
   }

	@Override
	public void excute() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void chart() {
		// TODO Auto-generated method stub
		
	}
}
