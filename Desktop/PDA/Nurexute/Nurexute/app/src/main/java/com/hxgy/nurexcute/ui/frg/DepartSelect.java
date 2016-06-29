package com.hxgy.nurexcute.ui.frg;


import java.util.ArrayList;

import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.widget.wheel.WheelView;
import com.hxgy.nurexcute.widget.wheel.adapters.ArrayWheelAdapter;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.GroupLoc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("ValidFragment")
public class DepartSelect extends Fragment{
	private WheelView depart_select_wv;
	private Button login_btn_login ;
	private String[] locIds;
	private String[] locDescs;
	private GroupLoc[] GroupLocs;
	private Context context;
	public DepartSelect(){
		context=getActivity().getApplicationContext();
	}
	public DepartSelect(Context context){
		this.context = context;
		
	}
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.depart_select_dialog, null);
		
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		depart_select_wv=(WheelView) getActivity().findViewById(R.id.depart_select_wv);
        login_btn_login=(Button) getActivity().findViewById(R.id.depart_select_ok);
        
        final ArrayList<GroupLoc>  list= (ArrayList<GroupLoc>) getActivity().getIntent().getSerializableExtra("departselect");
        
        GroupLocs=(GroupLoc[]) list.toArray(new GroupLoc[0]);
        CurUser. groupLocList=list;
        locIds= new String[list.size()];
        for (int i=0;i<list.size();i++){
        	locIds[i]=list.get(i).getLocdesc();
        }
        
        //String []cities= new String[]{"asd","asd1","asd2","asd3","asd4","asd5","asd6","asd7"};
        ArrayWheelAdapter<String> adapter =
                new ArrayWheelAdapter<String>(context, locIds );
       
        
    adapter.setTextSize(25);
    depart_select_wv.setViewAdapter(adapter);
    depart_select_wv.setCurrentItem(locIds.length / 2);    
    login_btn_login.setOnClickListener(new OnClickListener(){

		public void onClick(View arg0) {
			int indx=depart_select_wv.getCurrentItem();
			
			CurUser.userDepartmentID=list.get(indx).getLocid();
			CurUser.userDepartment=list.get(indx).getLocdesc();
			CurUser.userGroup=list.get(indx).getGrpdesc();
			CurUser.userGroupID=list.get(indx).getGrpid();
			MainActivity main = (MainActivity)getActivity();
//			main.setAdmId("");
//			main.setComm(MenuRighBar.ABOUT);
			main.switchContent();
//			UIHelper.showMenu(DepartSelect.this);
		}
    	
    });
    
	}
			
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	}
}
