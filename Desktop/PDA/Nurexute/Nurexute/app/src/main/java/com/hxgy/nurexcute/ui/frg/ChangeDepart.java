package com.hxgy.nurexcute.ui.frg;

import java.util.ArrayList;
import java.util.List;

import com.hxgy.nurexcute.App;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.GroupLoc;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.widget.PullToRefreshListView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChangeDepart extends Fragment{
	ListView lv;
	Context context;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		setRetainInstance(true);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.depart_select, null);
	}
   
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
	private void init(){
		 getActivity().setTitle(R.string.menu_right_bar_depselect);
		final ArrayList<GroupLoc>  list= (ArrayList<GroupLoc>) getActivity().getIntent().getSerializableExtra("departselect");
		lv = (ListView) getActivity().findViewById(R.id.depart_select_list);
		lv.setAdapter(new ChangeDepartAdapter(context,list));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				GroupLoc loc= (GroupLoc)v.getTag();
				CurUser.userDepartmentID=loc.getLocid();
				CurUser.userGroupID=loc.getGrpid();
				CurUser.userDepartment=loc.getLocdesc();
				CurUser.userGroup=loc.getGrpid();
				
				((MainActivity)getActivity()).OpenMenu();
				((MainActivity)getActivity()).showSecondaryMenu();
			}
			
		});
		
	}
	
	class ChangeDepartAdapter extends BaseAdapter{

		Context context;
		ArrayList<GroupLoc>  loc;
		public ChangeDepartAdapter(Context context,ArrayList<GroupLoc> loc){
			this.context=context;
			this.loc=loc;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return loc.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return loc.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(context).inflate(R.layout.depart_select_item, null);
			TextView locname=(TextView) convertView.findViewById(R.id.depart_select_item_locname);
			TextView groupname=(TextView) convertView.findViewById(R.id.depart_select_item_groupname);
			GroupLoc item = loc.get(position);
			convertView.setTag(item);
			locname.setText(item.getLocdesc());
			groupname.setText(item.getGrpdesc());
			return convertView;
		}
		
	}
	
}
