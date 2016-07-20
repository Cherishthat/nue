package com.hxgy.nurexcute.ui.frg;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.ExamReportAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.TipHelper;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;


public class ExameReport extends Fragment {
		
	private TextView tvbed;
	private TextView tvpatno;
	private TextView tvname;
//	private EditText txtbgdate;
//	private EditText txteddate;
//	private Button btnbgdate;
//	private Button btneddate;
	private ListView orderlist;
	private PatientDTO patient;
	private Context context;
//	 private  int BGYEAR=0;
//	 private  int BGMOTH=0;
//	 private  int BGDAY=0;
//	 private  int EDYEAR=0;
//	 private  int EDMOTH=0;
//	 private  int EDDAY=0;
//	 private Calendar cal = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		patient=(PatientDTO) getArguments().getSerializable("patient");
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.exam_report, null);
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
		
		 context = getActivity().getApplicationContext();
		 
		 getActivity().setTitle(R.string.menu_right_bar_exam);
//	     BGYEAR=cal.get(Calendar.YEAR);
//         BGMOTH=cal.get(Calendar.MONTH);
//         BGDAY=cal.get(Calendar.DAY_OF_MONTH);
//         EDYEAR=cal.get(Calendar.YEAR);
//         EDMOTH=cal.get(Calendar.MONTH);
//		 EDDAY=cal.get(Calendar.DAY_OF_MONTH);
		 tvbed= (TextView) getActivity().findViewById(R.id.exam_tvbed);
		 tvpatno= (TextView) getActivity().findViewById(R.id.exam_tvpatno);
		 tvname= (TextView) getActivity().findViewById(R.id.exam_tvname);
		
//		  txtbgdate = (EditText) getActivity().findViewById(R.id.exam_bgdate);
//		  txteddate= (EditText) getActivity().findViewById(R.id.exam_eddate);
//		  btnbgdate=(Button)getActivity().findViewById(R.id.exam_bgdate_btn);
//		  btneddate=(Button)getActivity().findViewById(R.id.exam_eddate_btn);
		  orderlist=(ListView)getActivity().findViewById(R.id.exame_orderlist);
		  
		  orderlist.setOnItemLongClickListener(new LVLongClickListener(this.context,this.patient));
		  if(patient!=null) {	
		  tvbed.setText(patient.getBedNo());
		  tvpatno.setText(patient.getPatNo());
		  tvname.setText(patient.getName());	
		  GetOrderDetail();
		  }
}
	
	class LVLongClickListener implements OnItemLongClickListener{
		Context context=null;
		PatientDTO patient=null;
		public LVLongClickListener(Context context,PatientDTO patient){
			this.context=context;
			this.patient=patient;
		}
		@Override
		public boolean onItemLongClick(AdapterView<?> adapter, View v,
				int position, long id) {
			TipHelper.Vibrate(getActivity(), 100);
			ExamOrderDTO order = (ExamOrderDTO)v.getTag();
			UIHelper.showExameResult(getActivity(), order, this.patient);

			return false;
		}
		
	}
	class DataHandler extends AsyncHttpResponseHandler{
		Context context;
		
		public DataHandler(Context context){
			this.context=context;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<ExamOrderDTO> pList = gson.fromJson(result,new TypeToken<List<ExamOrderDTO>>(){}.getType() );
			orderlist.setAdapter(new ExamReportAdapter(context ,pList));
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
	
	private void GetOrderDetail(){
		if(this.patient.getAdm()==""){
			return ;
		}
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.ExameReportOrder(patient.getAdm(), new DataHandler(context));
	}
	
//	class DatebtnClick implements OnClickListener{
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.exam_bgdate:
//			case R.id.exam_bgdate_btn :
//				new DatePickerDialog(getActivity(), new OnDateSetListener() {
//					@Override
//					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//						txtbgdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//					}
//		            }, BGYEAR, BGMOTH, BGDAY).show();
//				break;
//			case R.id.exam_eddate:
//			case R.id.exam_eddate_btn :
//				new DatePickerDialog(getActivity(), new OnDateSetListener() {
//					@Override
//					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//						txteddate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//	                }
//		            }, EDYEAR, EDMOTH, EDDAY).show();
//				break;
//			default:
//				break;
//			}
//			 
//			
//		}
    	
//    }
	

}
