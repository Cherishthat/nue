package com.hxgy.nurexcute.ui;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.bcreader.BCRBarCodeValue;
import android.hardware.bcreader.BCRListener;
import android.hardware.bcreader.BCRManager;
import android.hardware.bcreader.BCRTicketInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.capipad.scan.ScanManager;
import com.capipad.scan.ScanResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.dto.PatientDetailDTO;
import com.hxgy.nurexcute.ui.frg.ApplyRT;
import com.hxgy.nurexcute.ui.frg.ChangeDepart;
import com.hxgy.nurexcute.ui.frg.ExameReport;
import com.hxgy.nurexcute.ui.frg.ExcuteExcuteLAB;
import com.hxgy.nurexcute.ui.frg.ExcuteExcuteMainA;
import com.hxgy.nurexcute.ui.frg.ExcuteExcutePHA;
import com.hxgy.nurexcute.ui.frg.ExcuteExcuteWHXR;
import com.hxgy.nurexcute.ui.frg.ExcuteExcuteWorter;
import com.hxgy.nurexcute.ui.frg.ExcuteMain;
import com.hxgy.nurexcute.ui.frg.MenuRighBar;
import com.hxgy.nurexcute.ui.frg.OrderEnterMain;
import com.hxgy.nurexcute.ui.frg.PatientInfo;
import com.hxgy.nurexcute.ui.frg.QueryTodayOrder;
import com.hxgy.nurexcute.ui.frg.Setting;
import com.hxgy.nurexcute.ui.frg.SignsMain;
import com.hxgy.nurexcute.ui.frg.WordMessage;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.smartshell.bluetooth.CommonUtils;

import java.util.List;

public class MainActivity extends BaseActivity  {

	private Fragment mContent;

	private final BroadcastReceiver myReceiver= new MyReceiver();

	private static final String ACTION_BROADCASTRECEIVER ="action.broadcast.smartshell.data";
	private BarCodeBroadcastReceiver mBarCodeBroadcastReceiver=new BarCodeBroadcastReceiver();
	private BroadcastReceiver SIMSCANReceiver = new SIMSCANBroadcastReceiver();
	private LevonReceiver levonReceiver=  new LevonReceiver();
	private TruTalk_q910 truTalk_q910Receiver=new TruTalk_q910();
	private Honeywell honeywellReceiver = new Honeywell();
	private Motorola motorolaReceiver = new Motorola();
	private DXC568 dxc568Receiver = new DXC568();
	private BayReceiver bayReceiver = new BayReceiver();
	private ZGDXReceiver zgdxReceiver = new ZGDXReceiver();
	//中普达 c568
	private ScanManager mScan;
	private Boolean canScan=false;
	private FLY fly = new FLY();
	//private TelephonyManager tmType =null;
	//联新
	private LanXinBroadcastReceiver lanxin=new LanXinBroadcastReceiver();
	PowerManager powerManager = null;
	WakeLock wakeLock = null;


	public Boolean showExcute=false;

	private BCRManager mBCRManager;
	private BCRListener drListener;
	private BCRTicketInfo mBCRTicketInfo[] = {null, null};

	public MainActivity() {
		super(R.string.menu_right_bar_about);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//tmType=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");

		if (mContent == null){
			mContent = new ChangeDepart();
		}
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, mContent)
				.commit();

		this.powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
		this.wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "nurexcute");
//			  drListener=new DRListener();
//			  mBCRManager = BCRManager.getInstance();
		mScan = new ScanManager(this);

		mScan.registScanDataListen(new ScanResult() {


			@Override
			public void onResult(String data) {
				// TODO Auto-generated method stub


				if (data != null && !data.isEmpty()) {
					toBarCode(data,MainActivity.this);
				}
			}

		});

	}

	@SuppressWarnings("deprecation")
	public void onDestroy() {
		super.onDestroy();
//	   	 if (mBCRManager.isReady()) {
//	            mBCRManager.close();
//	        }
		CommonUtils.pausebtservice(this.getApplicationContext());
		ActivityManager activityMgr= (ActivityManager) getSystemService(ACTIVITY_SERVICE );
		activityMgr.restartPackage(getPackageName());

		mScan.unregistScanDataListen();
		mScan.finalize();
//	          unregisterReceiver(myReceiver);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		ExcuteContent excute =null;
		switch (item.getItemId()) {
			case android.R.id.home:
				toggle();
				break;
			case R.id.menu_excute_btn:
				excute=(ExcuteContent)this.mContent;
				excute.excute();
				break;
			case R.id.menu_search_btn:
				excute=(ExcuteContent)this.mContent;
				excute.search();
				break;
//		case R.id.menu_chart_btn:
//			excute=(ExcuteContent)this.mContent;
//			excute.chart();
//			break;
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		MenuItem actionExcute = menu.findItem(R.id.menu_excute_btn);
		MenuItem actionSearch=menu.findItem(R.id.menu_search_btn);
//		MenuItem actionChart=menu.findItem(R.id.menu_chart_btn);
		if(this.mContent instanceof ExcuteMain){
			actionExcute.setVisible(true);
			actionSearch.setVisible(true);
		}
		if(this.mContent instanceof SignsMain){
			actionSearch.setVisible(true);
			actionExcute.setVisible(true);
		}

		if(this.mContent instanceof ApplyRT){
			actionExcute.setVisible(true);
		}

		if(this.mContent instanceof OrderEnterMain){
			actionExcute.setVisible(true);
		}
		if(showExcute&&(this.mContent instanceof ExcuteExcuteMainA)){
			actionExcute.setVisible(true);
		}

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(mContent!=null){
			getSupportFragmentManager().putFragment(outState, "mContent", mContent);
		}
	}

	public void swithContextNoAdm(){
		this.comm=mBar.getComm();
		switch (this.comm) {

			case MenuRighBar.SELECTDEP:
				CloseMenu();
				mContent = new ChangeDepart();
				break;
			case MenuRighBar.SETTING:
				CloseMenu();
				mContent = new Setting();
				break;
			case MenuRighBar.ORDERTODAY:
				mContent= new QueryTodayOrder();
				break;
			case MenuRighBar.WordMessage:
				mContent= new WordMessage();
				break;
			default:
				break;
		}
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, mContent)
				.commit();
		getSlidingMenu().showContent();

		supportInvalidateOptionsMenu();

	}
	public void switchContent() {
		mContent = getFragmentByComm();
		if(mContent==null) return;
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.content_frame, mContent)
				.commit();
		getSlidingMenu().showContent();

		supportInvalidateOptionsMenu();
	}

	private Fragment getFragmentByComm()
	{
//		this.patient= mFrag.getCurrentPatient();

//		String admId=patient.getAdm();
		this.comm=mBar.getComm();
//		if (patient==null){
//			getSlidingMenu().showMenu();
//			return null;
//		}

		if (this.comm==MenuRighBar.SELECTDEP){
			getSlidingMenu().showSecondaryMenu();
			return null;
		}
		if (this.comm==MenuRighBar.SETTING){
			getSlidingMenu().showSecondaryMenu();
			return null;
		}

		Bundle args = new Bundle();
//		args.putString("admId", admId);
		args.putSerializable("patient", this.patient);
		switch (this.comm) {
			case MenuRighBar.PATIENTINFO :
				mContent = new PatientInfo();
				mContent.setArguments(args);
				break;
			case MenuRighBar.EXCUTE:
				mContent = new ExcuteMain();
				mContent.setArguments(args);
				break;
			case MenuRighBar.EXAME:
				mContent = new ExameReport();
				mContent.setArguments(args);
				break;
			case MenuRighBar.SELECTDEP:
				CloseMenu();
				mContent = new ChangeDepart();
				mContent.setArguments(args);
				break;
			case MenuRighBar.SETTING:
				CloseMenu();
				mContent = new Setting();
				break;
			case MenuRighBar.WordMessage:
				//CloseMenu();
				mContent=new WordMessage();
				break;
			case MenuRighBar.SIGHS:
				mContent = new SignsMain();
				mContent.setArguments(args);
				break;
			case MenuRighBar.WORTER:
				mContent = new ExcuteExcuteWorter();
				args.putSerializable("grpCode", "0");
				mContent.setArguments(args);
				break;
			case MenuRighBar.LABNO:
				mContent = new ExcuteExcuteLAB();
				args.putSerializable("grpCode", "1");
				mContent.setArguments(args);
				break;
			case MenuRighBar.PHA:
				mContent = new ExcuteExcutePHA();
				args.putSerializable("grpCode", "2");
				mContent.setArguments(args);
				break;
			case MenuRighBar.WHXR:
				mContent = new ExcuteExcuteWHXR();
				args.putSerializable("grpCode", "3");
				mContent.setArguments(args);
				break;
			case MenuRighBar.APPLYRT:
				mContent = new ApplyRT();
				mContent.setArguments(args);
				break;
			case MenuRighBar.ORDER:
				mContent = new OrderEnterMain();
				mContent.setArguments(args);
				break;

			default:
				break;
		}
		return mContent;
	}

	public void loading(){
		this.patient=null;
		setSupportProgressBarIndeterminateVisibility(true);
	}

	public void loadfinish(){
		setSupportProgressBarIndeterminateVisibility(false);
	}

	public void OpenMenu(){
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
//		getSlidingMenu().sh
//		getSlidingMenu().showSecondaryMenu();
		getmFrag().refresh();

	}
	public void EnableMenu(){
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}
	public void CloseMenu(){
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(false);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(this.mContent != null)
			this.mContent.onActivityResult(requestCode, resultCode, data);
	}
	@Override

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (keyCode) {
			case KeyEvent.KEYCODE_F12:

				if (canScan) {
					mScan.startScan();
				}
				break;
			case KeyEvent.KEYCODE_F11:
				if (canScan) {
					mScan.startScan();
				}
				break;
			case KeyEvent.KEYCODE_BACK:
				DialogTool.createConfirmDialog(this, "提示", "是否退出", "退出", "不 退出",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}

						}, new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

							}

						}, DialogTool.NO_ICON).show();
				break;

			default:
				super.onKeyDown(keyCode, event);
				break;
		}
		return true;

//	 if (android.os.Build.MODEL.equals("Saga")&&keyCode==58){
//		 mBCRManager.startScan(BCRManager.SCAN_TYPE_SINGLE, BCRManager.SCAN_MODE_ONESHOT);
//	 }
//
//		if(keyCode==240&&android.os.Build.MODEL.equals("aiolia_we_kk")){
//			 mBCRManager.startScan(BCRManager.SCAN_TYPE_SINGLE, BCRManager.SCAN_MODE_ONESHOT);
//		}



	}

	private class SIMSCANBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			this.abortBroadcast();

			String barCode= intent.getStringExtra("value");
			toBarCode(barCode,context);
		}

	}
	private class LanXinBroadcastReceiver extends BroadcastReceiver{

		@Override

		public void onReceive(Context arg0, Intent arg1) {

			String barCode = arg1.getStringExtra("lachesis_barcode_value_notice_broadcast_data_string");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,arg0);

		}

	}

	private class ZGDXReceiver extends BroadcastReceiver{

		@Override

		public void onReceive(Context arg0, Intent arg1) {

			String barCode = arg1.getStringExtra("data");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,arg0);

		}

	}

	private class LevonReceiver extends BroadcastReceiver
	{
		private LevonReceiver()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();
			if(action.equals("action.barcode.reader.value")){
				String barCode=paramIntent.getStringExtra("borcode_value");
				barCode=barCode.replaceAll("\r|\n","");
				toBarCode(barCode,paramContext);
			}
			//UIHelper.ToastMessage(paramContext, paramIntent.getStringExtra("value"));

			//Toast.makeText(paramContext, paramIntent.getStringExtra("value"), 0).show();
		}
	}

	private class BayReceiver extends BroadcastReceiver
	{
		private BayReceiver()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();
			if(action.equals("SYSTEM_BAR_READ")){
				String barCode=paramIntent.getStringExtra("BAR_VALUE");
				barCode=barCode.replaceAll("\r|\n","");
				toBarCode(barCode,paramContext);
			}
			//UIHelper.ToastMessage(paramContext, paramIntent.getStringExtra("value"));

			//Toast.makeText(paramContext, paramIntent.getStringExtra("value"), 0).show();
		}
	}



	private class Honeywell extends BroadcastReceiver
	{
		private Honeywell()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();
			if(action.equals("com.honeywell.tools.action.scan_result")){
				String barCode=paramIntent.getStringExtra("barcode_data");
				barCode=barCode.replaceAll("\r|\n","");
				toBarCode(barCode,paramContext);
			}

		}
	}

	private class ZGDX extends BroadcastReceiver
	{
		private ZGDX()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();

			String barCode=paramIntent.getStringExtra("com.android.action.BARCODE_DATA");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,paramContext);


		}
	}

	private class DXC568 extends BroadcastReceiver
	{
		private DXC568()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();

			String barCode=paramIntent.getStringExtra("data");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,paramContext);


		}
	}

	private class FLY extends BroadcastReceiver
	{
		private FLY()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();


			String barCode=paramIntent.getStringExtra("BARCODE");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,paramContext);



		}
	}


	private class Motorola extends BroadcastReceiver
	{
		private Motorola()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();

			String barCode=paramIntent.getStringExtra("com.motorolasolutions.emdk.datawedge.data_string");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,paramContext);


		}
	}


	private class TruTalk_q910 extends BroadcastReceiver
	{
		private TruTalk_q910()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			String action = paramIntent.getAction();
			if(action.equals("com.android.scanner.result")){
				String barCode=paramIntent.getStringExtra("result");
				barCode=barCode.replaceAll("\r|\n","");
				toBarCode(barCode,paramContext);
			}
			//UIHelper.ToastMessage(paramContext, paramIntent.getStringExtra("value"));

			//Toast.makeText(paramContext, paramIntent.getStringExtra("value"), 0).show();
		}
	}

	private class DRListener implements BCRListener {
		@Override
		public void onScanFinished(int scanType, String elapseTime) {


			mBCRTicketInfo = mBCRManager.getTicketInfo();
			String barCode=mBCRTicketInfo[0].getTicketValue();
			toBarCode(barCode,MainActivity.this);

		}

		@Override
		public void onOpened(boolean isReady) {

			if (isReady) {
				mBCRManager.enableCode(BCRBarCodeValue.CODE_TYPE_DATA_MATRIX);
				mBCRManager.enableCode(BCRBarCodeValue.CODE_TYPE_INTERLEAVED_2_OF_5);
				mBCRManager.enableCode(BCRBarCodeValue.CODE_TYPE_PDF417);
				//  mBCRManager.startScan(BCRManager.SCAN_TYPE_SINGLE, BCRManager.SCAN_MODE_CONTINUE);
			}
		}
	};

	private class BarCodeBroadcastReceiver extends BroadcastReceiver
	{
		private BarCodeBroadcastReceiver()
		{
		}

		public void onReceive(Context paramContext, Intent paramIntent)
		{
			//UIHelper.ToastMessage(paramContext, paramIntent.getStringExtra("value"));
			String barCode=paramIntent.getStringExtra("value");
			barCode=barCode.replaceAll("\r|\n","");
			toBarCode(barCode,paramContext);
			//Toast.makeText(paramContext, paramIntent.getStringExtra("value"), 0).show();
		}
	}


	private class MyReceiver extends BroadcastReceiver{
		private String barCode;

		public String getBarCode() {
			return barCode;
		}

		public void setBarCode(String barCode) {
			this.barCode = barCode;
		}


		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
			if (!mKeyguardManager.inKeyguardRestrictedInputMode()) {
				if (ACTION_BROADCASTRECEIVER.equals(action)){
					barCode=intent.getStringExtra("smartshell_data");

					showExcute=false;
					if(!barCode.equals("")){
						barCode=barCode.split(":")[1];
					}
					toBarCode(barCode,context);
				}
			}

		}
	}


	private void gotoPatient(String barCode ) {

		ApiClient.GetPatientBetialByPatNo(barCode, CurUser.userDepartmentID,new DataHandler(MainActivity.this));
	}


	private void toBarCode(String barCode, Context context) {
		Log.d("barCode", barCode);
		if(barCode.isEmpty()){
			UIHelper.ToastMessage(context, "读取条码失败");
			return;
		}
		if (barCode.substring(0, 2).equals("SJ") || ((barCode.substring(0, 1).equals("0") && (barCode.length() == 10)))){
//
//				if (mContent instanceof ExcuteExcuteMain) {
//
//					((IBarInterface)mContent).getPatient(barCode);
//				}else {
//					gotoPatient(barCode);
//				}
//				 barCode="0010971393";
			gotoPatient(barCode);
			return ;

		}
//			 barCode="591||123||2";
//			 ApiClient.GetBarInfo(patient.getAdm(), barCode, String.valueOf(comm), new BarInfoHandler(context,barCode));
		ExcuteExcuteMainA excuteContent=null;
		switch (comm){
			case MenuRighBar.WORTER :
//				   ((IBarInterface)mContent).excuteWorter(barCode);
				excuteContent=(ExcuteExcuteWorter)mContent;
				break;
			case MenuRighBar.PHA :
				excuteContent=(ExcuteExcutePHA)mContent;
				break;
			case MenuRighBar.WHXR :
				excuteContent=(ExcuteExcuteWHXR)mContent;
				break;
			case MenuRighBar.LABNO :
				excuteContent=(ExcuteExcuteLAB)mContent;
				break;

		}
		if(excuteContent!=null&&patient!=null) {
			try{
				excuteContent.showBarData(barCode);
			}catch(Exception ex) {
				UIHelper.ToastMessage(context, "条码错误");
			}
		}else {
			UIHelper.ToastMessage(context, "请选择功能和病人后执行");
		}
	}
	class BarInfoHandler extends AsyncHttpResponseHandler{
		Context context;
		String barCode;

		public BarInfoHandler(Context context,String barCode){
			this.context=context;
			this.barCode=barCode;
		}
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			ExcuteExcuteMainA excuteContent=null;
			switch (comm){
				case MenuRighBar.WORTER :
//					((IBarInterface)mContent).excuteWorter(barCode);
					excuteContent=(ExcuteExcuteWorter)mContent;
					break;
				case MenuRighBar.PHA :
					excuteContent=(ExcuteExcutePHA)mContent;
					break;
				case MenuRighBar.WHXR :
					excuteContent=(ExcuteExcuteWHXR)mContent;
					break;
				case MenuRighBar.LABNO :
					excuteContent=(ExcuteExcuteLAB)mContent;
					break;
				default :
					UIHelper.ToastMessage(context, "请选择功能后执行");
					break;
			}
			if(excuteContent!=null) {
				excuteContent.showBarData(this.barCode);
			}
//				try{
//				Gson gson = new Gson();
//				List<ResultMessDTO> pList = gson.fromJson(result,new TypeToken<List<ResultMessDTO>>(){}.getType() );
//				if(pList.size()==0){
//					return ;
//				}
//				ResultMessDTO p = pList.get(0);
//				if (p.getCode().equals("1")){
//					DialogTool.createConfirmDialog(context, "医嘱信息", p.getMessage(), "执行", "取消", new android.content.DialogInterface.OnClickListener(){
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							switch (comm){
//							   case MenuRighBar.WORTER :
//								   ((IBarInterface)mContent).excuteWorter(barCode);
//								   break;
//							   case MenuRighBar.PHA :
//								   ((IBarInterface)mContent).excutePHA(barCode);
//								   break;
//							   case MenuRighBar.WHXR :
//								   ((IBarInterface)mContent).excuteWhxr(barCode);
//								   break;
//							   case MenuRighBar.LABNO :
//								   ((IBarInterface)mContent).excuteLab(barCode);
//								   break;
//							  default :
//								  UIHelper.ToastMessage(context, "请选择功能后执行");
//								  break;
//							 }
//						}
//
//					}, new android.content.DialogInterface.OnClickListener(){
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//
//						}
//
//					}, DialogTool.NO_ICON).show();
//
//
//				}else {
//					UIHelper.ToastMessage(context, p.getMessage());
//				}
//
//				}catch(Exception ex){
//					UIHelper.ToastMessage(context, ex.getMessage());
//				}

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

			try{
				Gson gson = new Gson();
				List<PatientDetailDTO> pList = gson.fromJson(result,new TypeToken<List<PatientDetailDTO>>(){}.getType() );
				if(pList.size()==0){
					return ;
				}
//				SharedPreferences prefer = getSharedPreferences("APPSET", Activity.MODE_PRIVATE);
//				String selectIdx=prefer.getString("BEDGROUP", "");
				PatientDetailDTO p = pList.get(0);
//				if(p.getCtlocId().equals(CurUser.userDepartmentID)){
//				System.out.println(CurUser.userDepartmentID);
				if(p.getCode().equals("-1")){
					UIHelper.ToastMessage(context, p.getMessage());
					return;
				}
				if(!p.getIsinclude().equals("1")){
					UIHelper.ToastMessage(context, "病人不在当前护理单元");
					return;
				}else {
					mFrag.barRefresh(p.getAdm());
				}
				patient = new PatientDTO();
				patient.setAdm(p.getAdm());
				patient.setAdmDoc(p.getAdmDoc());
				patient.setAdmReason(p.getAdmreason());
				patient.setAge(p.getAge());
				patient.setBedNo(p.getBed());
				patient.setCtlocDesc(p.getCtloc());
				patient.setDia(p.getDia());
				patient.setName(p.getName());
				patient.setPatId(p.getPatId());
				patient.setPatNo(p.getRegno());
				patient.setSex(p.getSex());
				patient.setSexId(p.getSexId());
//				patient.setBedGroup(p.get);
//				switchContent();
				if(comm==MenuRighBar.SELECTDEP||comm==MenuRighBar.SETTING){
					comm=MenuRighBar.PATIENTINFO;

				}
				mBar.setComm(comm);
				switchContent();
//				}else{
//					UIHelper.ToastMessage(context, "病人不在当前科室");
//				}
			}catch(Exception ex){
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
			setSupportProgressBarIndeterminateVisibility(false);
		}
	}


	@Override
	protected void onPause() {
		unregisterReceiver(myReceiver);
		unregisterReceiver(mBarCodeBroadcastReceiver);
		unregisterReceiver(SIMSCANReceiver);
		unregisterReceiver(levonReceiver);
		unregisterReceiver(bayReceiver);
		unregisterReceiver(zgdxReceiver);

		unregisterReceiver(truTalk_q910Receiver);
		unregisterReceiver(honeywellReceiver);
		unregisterReceiver(motorolaReceiver);
		unregisterReceiver(dxc568Receiver);
		unregisterReceiver(fly);
		unregisterReceiver(lanxin);

//		 mBCRManager.stopScan();
		mScan.setScanState(false);


		this.wakeLock.release();
		super.onPause();

	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_BROADCASTRECEIVER);
		registerReceiver(myReceiver, filter);

		IntentFilter localIntentFilter = new IntentFilter("com.ge.action.barscan");
		registerReceiver(this.mBarCodeBroadcastReceiver, localIntentFilter);

		IntentFilter mFilter = new IntentFilter("com.sim.action.SIMSCAN");
		mFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		this.registerReceiver(SIMSCANReceiver, mFilter);


		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("action.barcode.reader.value");
		registerReceiver(levonReceiver, myIntentFilter);

		IntentFilter TruTalk_q910Intent = new IntentFilter("com.android.scanner.result");
		registerReceiver(this.truTalk_q910Receiver, TruTalk_q910Intent);

		IntentFilter honeywellIntent = new IntentFilter("com.honeywell.tools.action.scan_result");
		registerReceiver(this.honeywellReceiver, honeywellIntent);

		IntentFilter motorolaIntent = new IntentFilter("com.msi.motoroal.barbroadcast");
		registerReceiver(this.motorolaReceiver, motorolaIntent);

		IntentFilter dxc568ReceiverIntent = new IntentFilter("com.android.action.BARCODE_DATA");
		registerReceiver(this.dxc568Receiver, dxc568ReceiverIntent);

		IntentFilter bayFilterIntent =new IntentFilter("SYSTEM_BAR_READ");
		registerReceiver(this.bayReceiver,bayFilterIntent);

		IntentFilter zgdxFilterIntent =new IntentFilter("com.android.action.BARCODE_DATA");
		registerReceiver(this.zgdxReceiver,zgdxFilterIntent);


		IntentFilter flyReceiverIntent = new IntentFilter("com.barcode.sendBroadcast");
		registerReceiver(this.fly, flyReceiverIntent);

		IntentFilter lanxiReceiverIntent = new IntentFilter("lachesis_barcode_value_notice_broadcast");
		registerReceiver(this.lanxin, lanxiReceiverIntent);

//		   mBCRManager.registerListener(drListener);
//		   mBCRManager.open(this);

		mScan.setScanState(true);


		this.wakeLock.acquire();
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onStop();

//	        mBCRManager.stopScan();
	}


	@Override
	public void onBackPressed() {

		finish();
	}



}
