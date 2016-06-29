package com.hxgy.nurexcute.ui;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.UIHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity{
	private Button btn_patientlist;
	private Button btn_vitalsigns;
	private Button btn_linchuangshuye;
	private Button btn_hushizhixi;
	private Button btn_caijibiaoben;
	
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.menu);
	        
	        init();
	        bindEvent();
	        
	 }

	private void bindEvent() {
		
		OnClickListener listerner = new OnClickListener() {
			
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.menu_patientlist_btn:
					
					UIHelper.showPatintList(Menu.this);
					
					break;

				default:
					break;
				}
			}
		};
		
		btn_patientlist.setOnClickListener(listerner);
		
	}

	private void init() {
		// TODO Auto-generated method stub
		btn_patientlist =(Button) findViewById(R.id.menu_patientlist_btn);
		btn_patientlist.getBackground().setAlpha(140);
		btn_vitalsigns =(Button) findViewById(R.id.menu_vitalsigns);
		btn_vitalsigns.getBackground().setAlpha(140);
		btn_linchuangshuye =(Button) findViewById(R.id.menu_linchuangshuye);
		btn_linchuangshuye.getBackground().setAlpha(140);
		btn_caijibiaoben =(Button) findViewById(R.id.menu_caijibiaoben);
		btn_caijibiaoben.getBackground().setAlpha(140);
		btn_hushizhixi =(Button) findViewById(R.id.menu_hushizhixing);
		btn_hushizhixi.getBackground().setAlpha(140);
		
	}
	
	
	 
	
}
