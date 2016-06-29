package com.hxgy.nurexcute.ui;

import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.OrderDoseqtySpanAdapter;
import com.hxgy.nurexcute.adapter.OrderEnterSpanAdapter;
import com.hxgy.nurexcute.adapter.OrderRevLocSpanAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.ArcItemDetialDTO;
import com.hxgy.nurexcute.dto.ArcItemDoseQtyDTO;
import com.hxgy.nurexcute.dto.ArcItemRevLocDTO;
import com.hxgy.nurexcute.dto.BaseDataDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseDTO;
import com.hxgy.nurexcute.dto.OrdEnterBaseItemDTO;
import com.hxgy.nurexcute.dto.OrderDocEnterDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class OrderEnterInput extends SherlockActivity {
	private OrdEnterBaseDTO baseData;
	private Spinner priority;
	private EditText desc;
    private String ShortOrderPriorRowid="3";
	private EditText orderpackqty;
	private EditText orderdoseqty;
	private Spinner orderdoseuom_sp;
	private EditText orderdoseuom;

	private EditText orderfreq;
	private EditText orderdur;
	private Spinner orderdur_sp;
	private Spinner orderfreq_sp;
	private EditText orderinstr;
	private EditText ordermasterseqno;
	private Spinner orderinstr_sp;
	private Spinner orderrecdep;
	private EditText orderpackuom;
	private EditText price;
	private EditText action;
	private EditText orderfirstdaytimes;
	private Spinner action_sp;
	private EditText seqno;
	private Button add;
	private OrderDocEnterDTO editOrder;
    private ArcItemDetialDTO arcItemDetialDTO;
	private EditText orderskintest;
	LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams (ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.WRAP_CONTENT);
	private PatientDTO patient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderenter_input);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		init();
	}
	private void init(){
		priority = (Spinner) findViewById(R.id.orderenter_input_priority);
		desc=(EditText) findViewById(R.id.orderenter_input_desc);
		orderdoseqty=(EditText) findViewById(R.id.orderenter_input_orderdoseqty);
		orderdoseuom_sp=(Spinner) findViewById(R.id.orderenter_input_orderdoseuom_sp);
		orderdoseuom=(EditText) findViewById(R.id.orderenter_input_orderdoseuom);
		orderfreq=(EditText) findViewById(R.id.orderenter_input_orderfreq);
		orderfreq_sp=(Spinner) findViewById(R.id.orderenter_input_orderfreq_sp);
		orderinstr=(EditText) findViewById(R.id.orderenter_input_orderinstr);
		orderdur=(EditText) findViewById(R.id.orderenter_input_orderdur);
		orderdur_sp=(Spinner) findViewById(R.id.orderenter_input_orderdur_sp);
		orderinstr_sp=(Spinner) findViewById(R.id.orderenter_input_orderinstr_sp);
		ordermasterseqno=(EditText) findViewById(R.id.orderenter_input_ordermasterseqno);
		orderpackqty=(EditText) findViewById(R.id.orderenter_input_orderpackqty);
		orderpackuom=(EditText) findViewById(R.id.orderenter_input_orderpackuom);
		orderpackuom.setEnabled(false);
		action=(EditText) findViewById(R.id.orderenter_input_action);
		action_sp=(Spinner) findViewById(R.id.orderenter_input_action_sp);
		orderfirstdaytimes=(EditText) findViewById(R.id.orderenter_input_firstdaytimes);
		price=(EditText) findViewById(R.id.orderenter_input_orderprice);
		add=(Button) findViewById(R.id.orderenter_input_add);
		patient = (PatientDTO) getIntent().getSerializableExtra("patient");
		editOrder = (OrderDocEnterDTO) getIntent().getSerializableExtra("editorder");
		desc.setOnKeyListener(new DescKeyListener(this));
		desc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				desc.selectAll();
			}
		});
		seqno=(EditText) findViewById(R.id.orderenter_input_seqno);
		orderrecdep=(Spinner) findViewById(R.id.orderenter_input_orderrecdep);
		orderskintest=(EditText) findViewById(R.id.orderenter_input_action);
		setSupportProgressBarVisibility(true);
		ApiClient.OrdEnterBase(new OrdEnterBaseHandle(this));
		add.setOnClickListener(new OrdEnter());
	    if (editOrder!=null) {
	    	add.setText(R.string.orderenter_input_edit);
	    }
		
	}
//	 @Override
//	    public boolean onCreateOptionsMenu(Menu menu) {
//	        getSupportMenuInflater().inflate(R.menu.close, menu);
//
//	        return true;
//	    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
//		case R.id.menu_close_btn:
//			this.finish();
//			break;
		}
//	
//    
        return true;
    }
	
	class OrdEnter implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			OrderDocEnterDTO enterObj = new OrderDocEnterDTO();
			enterObj.setSeqno(seqno.getText().toString());
			if(orderdoseqty.isEnabled()&&orderdoseqty.getText().toString().trim().isEmpty()){
				UIHelper.ToastMessage(OrderEnterInput.this, "输入单次量");
				return ;
			}
//			if (orderpackqty.getText().toString().trim().isEmpty()){
//				UIHelper.ToastMessage(OrderEnterInput.this, "输入数量");
//				return ;
//			}
			BaseDataDTO prioritydto = new BaseDataDTO();
			OrdEnterBaseItemDTO priorityItm=(OrdEnterBaseItemDTO)priority.getSelectedItem();
			prioritydto.setRowid(priorityItm.getRowid());
			prioritydto.setDesc(priorityItm.getDesc());
			enterObj.setPriority(prioritydto);
			enterObj.setOrderfirstdaytimes(orderfirstdaytimes.getText().toString());
//			priority.setRowid();
			BaseDataDTO descdto= new BaseDataDTO();
			descdto.setRowid(desc.getTag().toString());
			descdto.setDesc(desc.getText().toString());
			enterObj.setDesc(descdto);
			enterObj.setOrdermasterseqno(ordermasterseqno.getText().toString());
			enterObj.setOrderdoseqty(orderdoseqty.getText().toString());
					
			BaseDataDTO orderdoseuomdto= new BaseDataDTO();
			if (arcItemDetialDTO!=null) {
				enterObj.setOrderdrugformRowid(arcItemDetialDTO.getFormstr().getDrugformrowid());
				enterObj.setOrderprice(arcItemDetialDTO.getPrice().getPatprice());	
				enterObj.setOrdertype(arcItemDetialDTO.getOrdertype());
				enterObj.setPhprescType(arcItemDetialDTO.getPhprescType());
			}
			if (orderdoseuom_sp.getVisibility()==View.VISIBLE){
				ArcItemDoseQtyDTO orderdoseuomItm=(ArcItemDoseQtyDTO)orderdoseuom_sp.getSelectedItem();
				orderdoseuomdto.setRowid(orderdoseuomItm.getRowid());
				orderdoseuomdto.setDesc(orderdoseuomItm.getDesc());
			}
			enterObj.setOrderdoseuom(orderdoseuomdto);
			BaseDataDTO orderfreqdto= new BaseDataDTO();
			if (orderfreq_sp.getVisibility()==View.VISIBLE){
				OrdEnterBaseItemDTO orderfreqItm=(OrdEnterBaseItemDTO)orderfreq_sp.getSelectedItem();
				orderfreqdto.setRowid(orderfreqItm.getRowid());
				orderfreqdto.setNum(orderfreqItm.getNum());
				orderfreqdto.setInterval(orderfreqItm.getInterval());
				orderfreqdto.setDesc(orderfreqItm.getDesc());
			}
			enterObj.setOrderfreq(orderfreqdto);
			BaseDataDTO orderinstrdto= new BaseDataDTO();
			if (orderinstr_sp.getVisibility()==View.VISIBLE){
				OrdEnterBaseItemDTO orderinstrItm=(OrdEnterBaseItemDTO)orderinstr_sp.getSelectedItem();
				orderinstrdto.setRowid(orderinstrItm.getRowid());
				orderinstrdto.setDesc(orderinstrItm.getDesc());
			}
			enterObj.setOrderinstr(orderinstrdto);
			
			BaseDataDTO orderdurdto= new BaseDataDTO();
			if (orderdur_sp.getVisibility()==View.VISIBLE){
				OrdEnterBaseItemDTO orderdurItm=(OrdEnterBaseItemDTO)orderdur_sp.getSelectedItem();
				orderdurdto.setRowid(orderdurItm.getRowid());
				orderdurdto.setNum(orderdurItm.getNum());
				orderdurdto.setDesc(orderdurItm.getDesc());
			}
			enterObj.setOrderdur(orderdurdto);
			
			enterObj.setOrderpackqty(orderpackqty.getText().toString());
			BaseDataDTO orderrecdepdto= new BaseDataDTO();
			if (orderrecdep.getVisibility()==View.VISIBLE){
				ArcItemRevLocDTO orderrecdepItm=(ArcItemRevLocDTO)orderrecdep.getSelectedItem();
				orderrecdepdto.setRowid(orderrecdepItm.getRowid());
				orderrecdepdto.setDesc(orderrecdepItm.getDesc());
			}
			enterObj.setOrderrecdep(orderrecdepdto);
			
			BaseDataDTO actiondto= new BaseDataDTO();
			if (action_sp.getVisibility()==View.VISIBLE){
				OrdEnterBaseItemDTO actionItm=(OrdEnterBaseItemDTO)action_sp.getSelectedItem();
				actiondto.setRowid(actionItm.getRowid());
				actiondto.setDesc(actionItm.getDesc());
			}
			
			enterObj.setOrderpackuom(orderpackuom.getText().toString());
			enterObj.setAction(actiondto);
			Intent intent=new Intent();  
			intent.putExtra("order", enterObj); 
			if (editOrder!=null) {
				setResult(201, intent);  
			}else{
				setResult(200, intent);  
			}
		    finish();  
		}
		
	}
	
	class DescKeyListener implements View.OnKeyListener{

		private Context context=null;
		public DescKeyListener(Context context){
			this.context = context;
		}
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_UP){  
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
				 if(imm.isActive()){  
				 imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );  
				 }
				 if(desc.getText().toString().trim().isEmpty()){
					 return true;
				 }
				 UIHelper.showOrderEnterSelect(OrderEnterInput.this, 200, patient,OrderEnterItemSelect.ARCITEM, desc.getText().toString());
				 return true;
			}
			
			return false;
		}
		
	}
	
	class OrdEnterBaseHandle extends AsyncHttpResponseHandler {
		Context context =null;
	    public OrdEnterBaseHandle(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<OrdEnterBaseDTO> p = gson.fromJson(result,new TypeToken<List<OrdEnterBaseDTO>>(){}.getType() );
			baseData = p.get(0);
			priority.setAdapter(new OrderEnterSpanAdapter(context, baseData.getPriority()));
			orderfreq_sp.setAdapter(new OrderEnterSpanAdapter(context, baseData.getFreq()));
			orderinstr_sp.setAdapter(new OrderEnterSpanAdapter(context, baseData.getInstruc()));
			orderdur_sp.setAdapter(new OrderEnterSpanAdapter(context, baseData.getDur()));
			action_sp.setAdapter(new OrderEnterSpanAdapter(context, baseData.getAction()));
			if(editOrder!=null){
				ApiClient.GetArcDetial(patient.getAdm(), editOrder.getDesc().getRowid(), CurUser.userDepartmentID, new ArcDetialHandle(context));
			}
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
			setSupportProgressBarVisibility(false);
		}
	}
	
	private void changeRow(Boolean enablePackQty,Boolean enableDuration){
		orderdoseqty.setEnabled(true);
		orderdoseuom.setVisibility(View.GONE);
		orderdoseuom_sp.setVisibility(View.VISIBLE);
		orderfreq.setVisibility(View.GONE);
		orderfreq_sp.setVisibility(View.VISIBLE);
		orderinstr.setVisibility(View.GONE);
		orderinstr_sp.setVisibility(View.VISIBLE);
		if(enablePackQty){
			orderpackqty.setEnabled(true);
		}else {
			orderpackqty.setEnabled(false);
		}
		if (enableDuration){
			orderdur.setVisibility(View.GONE);
			orderdur_sp.setVisibility(View.VISIBLE);
		}else {
			orderdur.setVisibility(View.VISIBLE);
			orderdur.setEnabled(false);
			orderdur_sp.setVisibility(View.GONE);
		}
//		orderrecdep.setAdapter(adapter);
		ordermasterseqno.setEnabled(true);
		orderskintest.setEnabled(true);
		action.setVisibility(View.GONE);
		action_sp.setVisibility(View.VISIBLE);
	}
	
	private void setNormalControl(String orderType,Boolean enableDuration,Boolean enableFrequence){
		
		if(!enableFrequence) {
			orderdoseqty.setEnabled(false);
			orderfreq.setEnabled(false);
			orderfreq.setVisibility(View.VISIBLE);
			orderfreq_sp.setVisibility(View.GONE);
			
		}else {
			orderfreq.setVisibility(View.GONE);
			orderfreq_sp.setVisibility(View.VISIBLE);
		}
		if(!enableDuration){
			orderdur.setEnabled(false);
			orderdur.setVisibility(View.VISIBLE);
			orderdur_sp.setVisibility(View.GONE);
		}else {
			orderdur.setVisibility(View.GONE);
			orderdur_sp.setVisibility(View.VISIBLE);
		}
		orderdoseuom.setEnabled(false);
		orderfirstdaytimes.setEnabled(false);
		orderdoseuom.setVisibility(View.VISIBLE);
		orderdoseuom_sp.setVisibility(View.GONE);
		ordermasterseqno.setEnabled(false);
		orderinstr.setEnabled(false);
		orderinstr.setVisibility(View.VISIBLE);
		orderinstr_sp.setVisibility(View.GONE);
		
		action.setVisibility(View.VISIBLE);
		action_sp.setVisibility(View.GONE);
		
	}
	
	private void  setControlData(ArcItemDetialDTO data) {
//		orderpackqty.setText("1");
		desc.setText(data.getDesc());
		desc.setTag(data.getRowid());
//		desc.setSelection(0, desc.getText().toString().length()-1);
		Boolean enableDuration=false;
		Boolean enableFrequence=false;
		orderpackuom.setText(data.getPackqty().getUom());
		orderpackqty.setText(data.getPackqty().getQty());
		price.setText(data.getPrice().getPatprice());
		
		for (int i=0;i<orderinstr_sp.getCount();i++){
			
			OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)orderinstr_sp.getItemAtPosition(i);
 
			if(o.getRowid().equals(data.getInstruction().getRowid())){
				orderinstr_sp.setSelection(i);
			}
		}
	   for (int i=0;i<priority.getCount();i++){
			
			OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)priority.getItemAtPosition(i);
			if(o.getRowid().equals(data.getDefprior().getRowid())){
				priority.setSelection(i);
			}
		}
		
		String orderPriorRowid=data.getDefprior().getRowid();
		if (!data.getIsskintype().equals("")){
			UIHelper.ToastMessage(this, "请开具皮试医嘱!");
		}
		if (!data.getOrdertype().equals("R")){
			if (data.getPhprescType().equals("4")){
				 enableDuration=true;
				 enableFrequence=true;
			}
			setNormalControl(data.getOrdertype(), enableDuration, enableFrequence);
	     }else{
	    	 if(data.getPhprescType().equals("3")) {
	    		 enableDuration=true;
	    	 }
	    	 if(orderPriorRowid.equals(ShortOrderPriorRowid)){
	    		 enableDuration=true;
	    	 }
	    	 changeRow(true,enableDuration);
	     }
		orderdoseuom_sp.setAdapter(new OrderDoseqtySpanAdapter(this,data.getDoseqty()));
		orderrecdep.setAdapter(new OrderRevLocSpanAdapter(this,data.getRecloc()));
		
		if(editOrder!=null) {
			desc.setEnabled(false);
			seqno.setText(editOrder.getSeqno());
			orderdoseqty.setText(editOrder.getOrderdoseqty());
			orderpackqty.setText(editOrder.getOrderpackqty());
			orderfirstdaytimes.setText(editOrder.getOrderfirstdaytimes());
			for (int i=0;i<orderinstr_sp.getCount();i++){
				OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)orderinstr_sp.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getOrderinstr().getRowid())){
					orderinstr_sp.setSelection(i);
				}
			}
			
			for (int i=0;i<orderdur_sp.getCount();i++){
				OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)orderdur_sp.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getOrderdur().getRowid())){
					priority.setSelection(i);
				}
			}
			for (int i=0;i<priority.getCount();i++){
				OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)priority.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getPriority().getRowid())){
					priority.setSelection(i);
				}
			}
			for (int i=0;i<orderdoseuom_sp.getCount();i++){
				ArcItemDoseQtyDTO o=(ArcItemDoseQtyDTO)orderdoseuom_sp.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getOrderdoseuom().getRowid())){
					orderdoseuom_sp.setSelection(i);
				}
			}
			
			for (int i=0;i<orderrecdep.getCount();i++){
				ArcItemRevLocDTO o=(ArcItemRevLocDTO)orderrecdep.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getOrderrecdep().getRowid())){
					orderrecdep.setSelection(i);
				}
			}
			
			for (int i=0;i<action_sp.getCount();i++){
				OrdEnterBaseItemDTO o=(OrdEnterBaseItemDTO)action_sp.getItemAtPosition(i);
				if(o.getRowid().equals(editOrder.getAction().getRowid())){
					action_sp.setSelection(i);
				}
			}
			
		}
	}
	class ArcDetialHandle extends AsyncHttpResponseHandler {
		Context context =null;
	    public ArcDetialHandle(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<ArcItemDetialDTO> p = gson.fromJson(result,new TypeToken<List<ArcItemDetialDTO>>(){}.getType() );
			arcItemDetialDTO=p.get(0);
			setControlData(arcItemDetialDTO);
			
			  }catch (Exception ex) {
		        	UIHelper.ToastMessage(context, ex.getMessage());
		        }
		
			
//			setNormalControl("", false, false);
		}

		@Override
		public void onFailure(Throwable throwable, String arg1) {
			super.onFailure(throwable, arg1);
			UIHelper.ToastFailMessage( this.context, throwable);
			
		}
		@Override
		public void onFinish() {
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(200==resultCode){
//		UIHelper.ToastMessage(this, data.getStringExtra("arcitmid"));
			String code=data.getStringExtra("arcitmid");
			ApiClient.GetArcDetial(patient.getAdm(), code, CurUser.userDepartmentID, new ArcDetialHandle(this));
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
