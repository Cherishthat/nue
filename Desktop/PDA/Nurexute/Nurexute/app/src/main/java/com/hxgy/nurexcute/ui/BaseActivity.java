package com.hxgy.nurexcute.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.frg.MenuPatient;
import com.hxgy.nurexcute.ui.frg.MenuRighBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected MenuPatient mFrag;
	protected MenuRighBar mBar;
//	protected String admId;
	protected PatientDTO patient;
	
	
public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}




	//	public String getAdmId() {
//		return admId;
//	}
//
//
//
//	public void setAdmId(String admId) {
//		this.admId = admId;
//	}
//
//	public int getComm() {
//		return comm;
//	}
//
//	public void setComm(int comm) {
//		this.comm = comm;
//	}
	protected int comm=MenuRighBar.SELECTDEP;
	public MenuPatient getmFrag() {
		return mFrag;
	}

	public void setmFrag(MenuPatient mFrag) {
		this.mFrag = mFrag;
	}

	public BaseActivity(int titleRes) {
	
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(mTitleRes);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new MenuPatient();
			
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
			
			mBar = new MenuRighBar();
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.menu_frame_two, mBar)
			.commit();
			
			
			
		} else {
			mFrag = (MenuPatient)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
			mBar=(MenuRighBar)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame_two);
		}
 
//		this.patient=mFrag.getCurrentPatient();
		this.comm=mBar.getComm();
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setSecondaryMenu(R.layout.menu_frame_two);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setRightBehindWidth(310);
	
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		
	}
	

	

	public void ShowMessage(String message)	{
		UIHelper.ToastMessage(this, message);
		
	}
}
