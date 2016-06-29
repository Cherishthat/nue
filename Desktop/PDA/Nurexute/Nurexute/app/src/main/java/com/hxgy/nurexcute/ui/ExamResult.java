/*
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hxgy.nurexcute.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExcuteExcuteRelationOrdAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.RelationOrdDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ExamResult extends SherlockActivity {
	
	TextView txtbed;
	TextView txtpatno;
	TextView txtname;
	TextView txtresult;
   



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.exam_report_result);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
        return true;
    }
    private void init(){
	    this.setTitle(R.string.menu_right_bar_exam);
	   
	    
	    PatientDTO patient= (PatientDTO)getIntent().getSerializableExtra("patient");
	    txtbed = (TextView) this.findViewById(R.id.exam_result_tvbed);
	    txtpatno = (TextView) this.findViewById(R.id.exam_result_tvpatno);
	    txtname = (TextView) this.findViewById(R.id.exam_result_tvname);
	    txtresult=(TextView) this.findViewById(R.id.exam_txtresult);
	    txtresult.setMovementMethod(ScrollingMovementMethod.getInstance());  
	    txtbed.setText(patient.getBedNo());
	    txtpatno.setText(patient.getPatNo());
	    txtname.setText(patient.getName());
	   
	   
	    
	    		
 }
 
@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 setSupportProgressBarIndeterminateVisibility(true);
		ExamOrderDTO examdto = (ExamOrderDTO)getIntent().getSerializableExtra("examorder");
		 ApiClient.ExameGetReport(examdto.getOeOrderDr(),new GetResultHandle(this));
	}

class GetResultHandle extends AsyncHttpResponseHandler {
	Context context =null;
    public GetResultHandle(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		try {
		Gson gson = new Gson();
		List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
		ResultMessDTO resultMess = p.get(0);
		
		txtresult.setText(resultMess.getMessage());
		
		}catch(Exception ex){
			UIHelper.ToastMessage(context, ex.toString());
		}
	}

	@Override
	public void onFailure(Throwable throwable, String arg1) {
		super.onFailure(throwable, arg1);
		UIHelper.ToastFailMessage( this.context, throwable);
		
	}
	@Override
	public void onFinish() {
		setSupportProgressBarIndeterminateVisibility(false);
	}
}
	
    
}
