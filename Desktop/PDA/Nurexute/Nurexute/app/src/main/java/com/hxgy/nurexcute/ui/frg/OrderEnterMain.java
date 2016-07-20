package com.hxgy.nurexcute.ui.frg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.adapter.OrderEnterMainAdapter;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.OrderDocEnterDTO;
import com.hxgy.nurexcute.dto.OrderItemDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.ResultMessDTO;
import com.hxgy.nurexcute.ui.ExcuteContent;
import com.hxgy.nurexcute.ui.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

public class OrderEnterMain extends Fragment implements ExcuteContent{
	private Context context;
    private Button addButton;
	private PatientDTO patient;
	private ListView lv;
	private List<OrderDocEnterDTO> list;
	private OrderEnterMainAdapter adapter;
	private MainActivity main;
	private TextView tvName;
	private TextView tvPatNo;
	private TextView txtBedNo;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.orderenter_main, null);
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
private void init(){
	 getActivity().setTitle(R.string.menu_right_bar_order);
	 lv=(ListView) getActivity().findViewById(R.id.orderenter_main_lv) ;
	 tvName = (TextView) getActivity().findViewById(R.id.orderenter_main_tvname);
	 tvPatNo = (TextView) getActivity().findViewById(R.id.orderenter_main_tvpatno);
	 txtBedNo = (TextView) getActivity().findViewById(R.id.orderenter_main_tvbed);
	addButton=(Button) getActivity().findViewById(R.id.orderenter_btnadd) ;
	addButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			UIHelper.showOrderEnterInput(getActivity(), 200,patient,null);
		}
	});
	list=new ArrayList<OrderDocEnterDTO>();
	adapter=new OrderEnterMainAdapter(context,list);
	lv.setAdapter(adapter);
	lv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
		
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			menu.add(Menu.NONE,Menu.FIRST,0,"删除");
			menu.add(Menu.NONE,Menu.FIRST+1,1,"修改");
		}
	});
	if(patient!=null) {
		 tvName.setText(patient.getName());
		 tvPatNo.setText(patient.getPatNo());
		 txtBedNo.setText(patient.getBedNo());
	}
}


@Override
public boolean onContextItemSelected(MenuItem item) {
	AdapterView.AdapterContextMenuInfo menuinfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	if(item.getItemId()==Menu.FIRST){
	  list.remove(menuinfo.position);
	  adapter.notifyDataSetChanged();
	  }
	if(item.getItemId()==Menu.FIRST+1){
		OrderDocEnterDTO o=list.get(menuinfo.position)	;
		UIHelper.showOrderEnterInput(getActivity(), 200,patient,o);
	}
	
	
	return super.onContextItemSelected(item);
}

public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	

	if(200==resultCode){
		OrderDocEnterDTO o=(OrderDocEnterDTO)data.getSerializableExtra("order");
		int seqno=list.size();
		o.setSeqno(String.valueOf(seqno+1));
		list.add(o);
		adapter.notifyDataSetChanged();
	
	}
	if(201==resultCode){
		OrderDocEnterDTO o=(OrderDocEnterDTO)data.getSerializableExtra("order");
		for (OrderDocEnterDTO e : list) {
			if(e.getSeqno().equals(o.getSeqno())){
				e.setAction(o.getAction());
				e.setOrderdoseqty(o.getOrderdoseqty());
				e.setOrderfirstdaytimes(o.getOrderfirstdaytimes());
				e.setOrderdoseuom(o.getOrderdoseuom());
				e.setOrderdur(o.getOrderdur());
				e.setOrderfreq(o.getOrderfreq());
				e.setOrderinstr(o.getOrderinstr());
				e.setOrdermasterseqno(o.getOrdermasterseqno());
				e.setOrderpackqty(o.getOrderpackqty());
				e.setOrderpackuom(o.getOrderpackuom());
				e.setOrderrecdep(o.getOrderrecdep());
				e.setPriority(o.getPriority());
			}
		}
		adapter.notifyDataSetChanged();
	}
	super.onActivityResult(requestCode, resultCode, data);
}
	@Override
	public void excute() {
		DialogTool.createConfirmDialog(context, "提示", "审核医嘱", "审核", "取消", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				List<OrderItemDTO> orderItemList = new ArrayList<OrderItemDTO>();
				StringBuilder sb= new StringBuilder();
				if(list.size()==0) {
					UIHelper.ToastMessage(context, "没有医嘱");
					return;
				}
				for (OrderDocEnterDTO dto : list) {
					OrderItemDTO o = new OrderItemDTO();
					o.setBilltypeRowid("");
					o.setOrderActionRowid(dto.getAction().getRowid());
					o.setOrderARCIMRowid(dto.getDesc().getRowid());
					o.setOrderARCOSRowid("");
					o.setOrderCoverMainIns("Y");
					o.setOrderCPWStepItemRowId("");
					o.setOrderDepProcNote("");
					o.setOrderDIACatRowId("");
					o.setOrderDoseQty(dto.getOrderdoseqty());
					o.setOrderDoseUOMRowid(dto.getOrderdoseuom().getRowid());
					o.setOrderDrugFormRowid(dto.getOrderdrugformRowid());
					o.setOrderDurRowid(dto.getOrderdur().getRowid());
					o.setOrderEndDate("");
					o.setOrderEndTime("");
					o.setOrderFirstDayTimes(dto.getOrderfirstdaytimes());
					o.setOrderFreqRowid(dto.getOrderfreq().getRowid());
					o.setOrderInstrRowid(dto.getOrderinstr().getRowid());
					o.setOrderInsurCatRowId("");
					o.setOrderLabSpecRowid("");
					o.setOrderMasterSeqNo(dto.getOrdermasterseqno());
					o.setOrderMultiDate("");
					o.setOrderNotifyClinician("N");
					o.setOrderPackQty(dto.getOrderpackqty());
					o.setOrderPhSpecInstr("");
					o.setOrderPrice(dto.getOrderprice());
					o.setOrderPriorRowid(dto.getPriority().getRowid());
					float orderQtySum=0;
					String interval=dto.getOrderfreq().getInterval();
					
					float dur=dto.getOrderdur().getNum().isEmpty()?0:Float.parseFloat(dto.getOrderdur().getNum());
					String freq=dto.getOrderfreq().getNum();
					if(dto.getOrdertype().equals("R")){
						if(!interval.isEmpty()){
							float convert=dur/Float.parseFloat(interval);
							float fact=dur%Float.parseFloat(interval);
							if (fact>0) {
								fact=1;
							} else {
								fact=0;
							}
							dur=(float) (Math.floor(convert)+fact);
						}
						if (freq.isEmpty()) {
							freq="1";
						}
						orderQtySum=Float.parseFloat(dto.getOrderdoseqty())*dur* Float.parseFloat(freq);
					}else{
						if (dto.getOrdertype().equals("L")&&dto.getOrderpackqty().endsWith("")){
							 o.setOrderPackQty("1");
						}
						orderQtySum=Float.parseFloat(dto.getOrderpackqty());
						o.setOrderPackQty("");
					}
					o.setOrderQtySum(String.valueOf(orderQtySum));	
					
					if(dto.getOrderdoseqty().equals("")){
						o.setOrderDoseUOMRowid("");
					}
					o.setOrderRecDepRowid(dto.getOrderrecdep().getRowid());
					o.setOrderSeqNo(dto.getSeqno());
				
					if (dto.getAction().getRowid().isEmpty()||dto.getAction().getRowid().equals("0")){
						o.setOrderSkinTest("N");
					}else {
						o.setOrderSkinTest("Y");
					}
					o.setOrderStartDate("");
					o.setOrderStartTime("");
					o.setOrderType(dto.getOrdertype());
					o.setPhprescType(dto.getPhprescType());
					
					if(sb.length()==0) {
						sb.append(o.toString());
					}else {
						sb.append((char)1);
						sb.append(o.toString());
					}
					
					
				}
				
				main.setSupportProgressBarVisibility(true);
				ApiClient.EnterOrder(patient.getAdm(), sb.toString(), CurUser.userId, CurUser.userDepartmentID, new EnterOrder(context));
			}
			
		}, new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		}, DialogTool.NO_ICON).show();
	}

	@Override
	public void search() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chart() {
		// TODO Auto-generated method stub
		
	}

 class EnterOrder extends AsyncHttpResponseHandler {
		Context context =null;
	    public EnterOrder(Context context) {
	    	this.context =context;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			Gson gson = new Gson();
			try {
			List<ResultMessDTO> p = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
			ResultMessDTO o = p.get(0);
			if(o.getCode().equals("1")){
				
				UIHelper.ToastMessage(context, "成功");
				list.clear();
				adapter.notifyDataSetChanged();
			}else{
				UIHelper.ToastMessage(context, "失败");
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
			main.setSupportProgressBarVisibility(false);
		}
		
	}

}
