package com.hxgy.nurexcute.ui.frg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.OBSItemDTO;
import com.hxgy.nurexcute.dto.OBSItemLineDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.UserResultMess;
import com.hxgy.nurexcute.ui.SignsChart;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SignsMain extends Fragment implements ExcuteContent{
	private Context context;
	private PatientDTO patient;
	private LinearLayout root;
	private TextView name; 
	private TextView patno;
	private TextView bedno;
	private EditText tvdate;
	private Button eventAdd;
	private CheckBox ckDate;
	private Spinner sptime;
	private EditText tvTime;
	private MainActivity main ;
	private Calendar cal = Calendar.getInstance();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.signs_main, null);
	}
   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 context = getActivity();
		 patient=(PatientDTO) getArguments().getSerializable("patient");
		 main= ((MainActivity)getActivity());
		setRetainInstance(true);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((MainActivity)getActivity()).EnableMenu();
	    init();
	}
	
	private void init() {
		Calendar cal = Calendar.getInstance();
		getActivity().setTitle(R.string.signs_title);
		root = (LinearLayout) getActivity().findViewById(R.id.signs_main_root);
		name = (TextView) getActivity().findViewById(R.id.signs_main_name);
		patno = (TextView) getActivity().findViewById(R.id.signs_main_patno);
		bedno = (TextView) getActivity().findViewById(R.id.signs_main_bedno);
		tvdate=(EditText) getActivity().findViewById(R.id.signs_mian_tvdate);
		tvdate.setOnClickListener(new DatebtnClick());
		eventAdd=(Button) getActivity().findViewById(R.id.signs_mian_eventbtn);
		ckDate=(CheckBox) getActivity().findViewById(R.id.signs_mian_ckdate);
		sptime=(Spinner) getActivity().findViewById(R.id.signs_mian_sptime);
		tvTime=(EditText) getActivity().findViewById(R.id.signs_mian_tvtime);
		ckDate.setOnCheckedChangeListener(new CheckDateClick());
		
		
		ArrayAdapter<CharSequence> sptimeadapter = ArrayAdapter.createFromResource( getActivity(), R.array.obstime, android.R.layout.simple_spinner_item);
		sptime.setAdapter(sptimeadapter);
		sptimeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		Date currDate=new Date();
		int position=sptimeadapter.getCount()-1;
		for (int i=0;i<sptimeadapter.getCount();i++) {
			String time=sptimeadapter.getItem(i).toString();
			int hour=Integer.parseInt(time.split(":")[0]);
			int minter=Integer.parseInt(time.split(":")[1]);
			Calendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)), cal.get(Calendar.DAY_OF_MONTH), hour, minter,0);    
			Date d = calendar.getTime();    
			if (d.getTime()<currDate.getTime()){
				continue;
			}else{
				position=i;
				break;
			}
		}
		sptime.setSelection(position);
		tvdate.setKeyListener(null);
		tvdate.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
		eventAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (patient==null) {
					return;
				}
				UIHelper.showEvent(getActivity(), patient);
			}
		});
		if(patient!=null) {
		  name.setText(patient.getName());
		  patno.setText(patient.getPatNo());
		  bedno.setText(patient.getBedNo());
		}
		initUI();
		
	}
	class CheckDateClick implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
				sptime.setVisibility(View.VISIBLE);
				tvTime.setVisibility(View.GONE);
				
			}else{
				sptime.setVisibility(View.GONE);
				tvTime.setVisibility(View.VISIBLE);
				SimpleDateFormat simpleformat = new SimpleDateFormat("HH:mm");    
				tvTime.setText(simpleformat.format(Calendar.getInstance().getTime()));
				
			}
			
		}
		
	}
	
	 class DatebtnClick implements OnClickListener{
		 
			@Override
			public void onClick(View v) {
				
				new DatePickerDialog(getActivity(), new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						tvdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
						
					}
		            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
			}
	    	
	    }

	@Override
	public void excute() {
		if(patient==null) {
			UIHelper.ToastMessage(context, R.string.info_choice_patient);
			return;
		}
		DialogTool.createConfirmDialog(context, "提示", "体征数据录入", "确定", "取消", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveDate();
			}
			
		}, new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		}, DialogTool.NO_ICON).show();
		
	}

	@Override
	public void search() {
		if(patient==null) {
			UIHelper.ToastMessage(context, R.string.info_choice_patient);
			return;
		}
		initUI();
	}
private void saveDate(){
	String searchDate=tvdate.getText().toString();
	String searchTime="";
	
	if(ckDate.isChecked()){
		searchTime=sptime.getSelectedItem().toString();
	}else{
		searchTime=tvTime.getText().toString();
	}
	
	if(searchDate.isEmpty()||searchTime.isEmpty()) {
		UIHelper.ToastMessage(context, R.string.signs_main_chesemess);
		return ;
	}
	
	
	main.setSupportProgressBarIndeterminateVisibility(true);
	List<OBSItemDTO> list=new ArrayList<OBSItemDTO>();
	LinearLayout row;
	EditText value;
	for (int i=0;i<root.getChildCount();i++){
		 row=(LinearLayout) root.getChildAt(i);
		 value =(EditText) row.getChildAt(1);
		 OBSItemDTO o = new OBSItemDTO();
		 o.setId(value.getTag().toString());
		 o.setValue(value.getText().toString());
		 list.add(o);
	}
	Gson gson = new Gson();
	String values = gson.toJson(list);
	
	ApiClient.saveOBSItem(patient.getAdm(),searchDate,searchTime,CurUser.userId, values,new SaveObsHander(this.context));
	
}

private void initUI(){
	
	
	String searchDate=tvdate.getText().toString();
	String searchTime="";
	
	if(ckDate.isChecked()){
		searchTime=sptime.getSelectedItem().toString();
	}else{
		searchTime=tvTime.getText().toString();
	}
	
	if(searchDate.isEmpty()||searchTime.isEmpty()) {
		return ;
	}
	if(patient!=null){
	 main.setSupportProgressBarIndeterminateVisibility(true);
	 ApiClient.getOBSItem(patient.getAdm(),searchDate,searchTime,new InitHandler(this.context));
	}
}
	@SuppressWarnings("ResourceType")
private void createUI(List<OBSItemDTO> list){
	LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
	LinearLayout.LayoutParams itmparm = new LinearLayout.LayoutParams (0 ,ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
	LinearLayout.LayoutParams tvparm = new LinearLayout.LayoutParams (0 ,ViewGroup.LayoutParams.WRAP_CONTENT,2.0f);
	root.removeAllViews();
	for (OBSItemDTO obsItemDTO : list) {
		LinearLayout row=new LinearLayout(this.context);
		row.setOrientation(LinearLayout.HORIZONTAL);
		row.setLayoutParams(parm);
		row.setPadding(2, 2, 2, 2);
		
		TextView tv = new TextView(this.context);
		if(patient!=null){
		  tv.setOnClickListener(new ItemClick(obsItemDTO,patient.getAdm()));
		}
		tv.setText(obsItemDTO.getDesc());
		tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		tv.setTextColor(Color.BLACK);
		tv.setLayoutParams(tvparm);
		EditText value = new EditText(this.context);
		value.setTag(obsItemDTO.getId());
		value.setInputType(InputType.TYPE_CLASS_PHONE);
		value.setTextAppearance(context, android.R.attr.textAppearanceLargeInverse);
		value.setText(obsItemDTO.getValue());
		value.setLayoutParams(tvparm);
		TextView uom = new TextView(this.context);
		uom.setText(obsItemDTO.getUom());
		uom.setTextColor(Color.BLACK);
		uom.setLayoutParams(itmparm);
		row.addView(tv);
		row.addView(value);
		row.addView(uom);
		root.addView(row);
	}
}
	
class InitHandler extends AsyncHttpResponseHandler{
	Context context =null;
    public InitHandler(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try {
		List<OBSItemDTO> list = gson.fromJson(result,new TypeToken<List<OBSItemDTO>>(){}.getType() );
		
		createUI(list);
		}catch(Exception ex) {
			UIHelper.ToastMessage(context, ex.getMessage());
		}
		
	}

	@Override
	public void onFailure(Throwable throwable, String arg1) {
		super.onFailure(throwable, arg1);
		UIHelper.ToastFailMessage( this.context, throwable);
		
	}
	@Override
	public void onFinish() {
		
		if(main!=null){
				
			main.setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}

class ItemClick implements OnClickListener{
	OBSItemDTO obsItem;
	String admId;
	public ItemClick (OBSItemDTO obsItem,String admId) {
		this.obsItem =obsItem;
		this.admId=admId;
	}
	@Override
	public void onClick(View v) {
		main.setSupportProgressBarIndeterminateVisibility(true);
		ApiClient.GetOBSLineData(admId, this.obsItem.getId(), new ShowObsLineHander(context,this.obsItem));
	}
	
}

class ShowObsLineHander extends AsyncHttpResponseHandler{
	Context context =null;
	OBSItemDTO obsItem;
    public ShowObsLineHander(Context context,OBSItemDTO obsItem) {
    	this.context =context;
    	this.obsItem=obsItem;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		 try {
		SimpleDateFormat dateConvert = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Gson gson = new Gson();
       
		List<OBSItemLineDTO> list = gson.fromJson(result,new TypeToken<List<OBSItemLineDTO>>(){}.getType() );
	    if (list.size()<1) {
	    	UIHelper.ToastMessage(context, "没有数据");
	    	return ;
	    }
       
		SignsChart chart = new SignsChart();
		
		ArrayList<Date[]> xdate = new ArrayList<Date[]>();
		ArrayList<double[]> values = new ArrayList<double[]>();
		xdate.add(new Date[list.size()]);
		values.add(new double[list.size()]);
		//xdate.get(0)[0]=
		for (int i=0;i<list.size();i++){
			try {
				xdate.get(0)[i]= dateConvert.parse(list.get(i).getDate());
				values.get(0)[i]=Double.parseDouble(list.get(i).getData());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		chart.setXdate(xdate);
		chart.setValues(values);
		chart.setTitle(this.obsItem.getDesc());
      
		Intent inter = chart.execute(this.context);
		startActivity(inter);
		}catch (Exception ex) {
	        	UIHelper.ToastMessage(context, ex.getMessage());
	        }
	}
	@Override
	public void onFailure(Throwable throwable, String arg1) {
		super.onFailure(throwable, arg1);
		UIHelper.ToastFailMessage( this.context, throwable);
		
	}
	@Override
	public void onFinish() {
		
		if(main!=null){
				
			main.setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}

class SaveObsHander extends AsyncHttpResponseHandler{
	Context context =null;
    public SaveObsHander(Context context) {
    	this.context =context;
    }
	@Override
	public void onSuccess(String result) {
		super.onSuccess(result);
		Gson gson = new Gson();
		try {
		List<UserResultMess> list = gson.fromJson(result,new TypeToken<List<UserResultMess>>(){}.getType() );
		UserResultMess mess = list.get(0);
		if(mess.getCode().equals("0")){
			UIHelper.ToastMessage(context, R.string.signs_main_sucess);
		}else{
			UIHelper.ToastMessage(context, mess.getMessage());
		}
	
		}catch(Exception ex) {
			UIHelper.ToastMessage(context, ex.getMessage());
		}
		
	}

	@Override
	public void onFailure(Throwable throwable, String arg1) {
		super.onFailure(throwable, arg1);
		UIHelper.ToastFailMessage( this.context, throwable);
		
	}
	@Override
	public void onFinish() {
		MainActivity main = ((MainActivity)getActivity());
		if(main!=null){
			main.setSupportProgressBarIndeterminateVisibility(false);
		}
	}
}

@Override
public void chart() {
//	SignsChart chart = new SignsChart();
//	
//	Intent inter = chart.execute(this.context);
//	startActivity(inter);
}


}
