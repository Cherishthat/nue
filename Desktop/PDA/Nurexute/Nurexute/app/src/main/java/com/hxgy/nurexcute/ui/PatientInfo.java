package com.hxgy.nurexcute.ui;

import com.hxgy.nurexcute.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PatientInfo extends Activity {

	ImageView patient_info_back;
	ProgressBar patient_info_head_progress;
	ImageView patient_info_refresh;
	TextView patient_info_patno;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_info);
		
		init();
	}
	private void init() {
		 patient_info_back =(ImageView)findViewById(R.id.patient_info_back);
		 patient_info_head_progress=(ProgressBar)findViewById(R.id.patient_info_head_progress);
		 patient_info_refresh=(ImageView)findViewById(R.id.patient_info_refresh);
		 patient_info_patno=(TextView)findViewById(R.id.patient_info_patno);
		 patinet_info_bed=(TextView)findViewById(R.id.patinet_info_bed);
		 patient_info_sex=(TextView)findViewById(R.id.patient_info_sex);
		 patient_info_age=(TextView)findViewById(R.id.patient_info_age);
		 patient_info_indate=(TextView)findViewById(R.id.patient_info_indate);
		 patient_info_admdoc=(TextView)findViewById(R.id.patient_info_admdoc);
		 patient_info_admloc=(TextView)findViewById(R.id.patient_info_admloc);
		 patient_info_binglihao=(TextView)findViewById(R.id.patient_info_binglihao);
		 patient_info_admreason=(TextView)findViewById(R.id.patient_info_admreason);
		 patient_info_lianxiren=(TextView)findViewById(R.id.patient_info_lianxiren);
		 patient_info_dianhua=(TextView)findViewById(R.id.patient_info_dianhua);
		 patient_info_zongfeiyong=(TextView)findViewById(R.id.patient_info_zongfeiyong);
		 patient_info_yajin=(TextView)findViewById(R.id.patient_info_yajin);
	}

}
