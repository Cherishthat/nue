package com.hxgy.nurexcute.common;


import java.util.ArrayList;
import java.util.List;

import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.dto.EventDTO;
import com.hxgy.nurexcute.dto.ExamOrderDTO;
import com.hxgy.nurexcute.dto.ExcuteOrderDTO;
import com.hxgy.nurexcute.dto.GroupLoc;
import com.hxgy.nurexcute.dto.OrderDocEnterDTO;
import com.hxgy.nurexcute.dto.PatientDTO;
import com.hxgy.nurexcute.ui.BlueSetting;
import com.hxgy.nurexcute.ui.EventInput;
import com.hxgy.nurexcute.ui.EventMain;
import com.hxgy.nurexcute.ui.ExamResult;
import com.hxgy.nurexcute.ui.ExcuteSearch;
import com.hxgy.nurexcute.ui.MainActivity;
import com.hxgy.nurexcute.ui.OrderEnterInput;
import com.hxgy.nurexcute.ui.OrderEnterItemSelect;
import com.hxgy.nurexcute.ui.SettingBedGroup;
import com.hxgy.nurexcute.ui.WaitExcuteList;
import com.hxgy.nurexcute.ui.Login;
import com.hxgy.nurexcute.ui.Menu;
import com.hxgy.nurexcute.ui.PatientInfo;
import com.hxgy.nurexcute.ui.PatientList;
import com.hxgy.nurexcute.ui.WaitExcuteList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;




/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UIHelper {
	
	public static void showPatintList(Activity activity){
		Intent intent = new Intent(activity, PatientList.class);
		activity.startActivity(intent);
		//activity.finish();
	}
	public static void showMenu(Activity activity){
		Intent intent = new Intent(activity, Menu.class);
		activity.startActivity(intent);
		activity.finish();
	}
	public static void showLogin(Activity activity) {
		Intent intent = new Intent(activity, Login.class);
		activity.startActivity(intent);
	}
	
	public static void showWaitExcute(Activity activity,ArrayList<ExcuteOrderDTO> list){
		Intent intent = new Intent(activity, WaitExcuteList.class);
		intent.putExtra("waitexcute", list);
		activity.startActivityForResult(intent,200);
	}
	public static void showWaitSearch(Activity activity,int code){
		Intent intent = new Intent(activity, ExcuteSearch.class);
		activity.startActivityForResult(intent, code);
	}
	
	public static void showOrderEnterInput(Activity activity,int code,PatientDTO pateint,OrderDocEnterDTO order){
		Intent intent = new Intent(activity, OrderEnterInput.class);
		intent.putExtra("patient", pateint);
		intent.putExtra("editorder", order);
		activity.startActivityForResult(intent, code);
	}
	
	public static void showPatientInfo(Activity activity,String pid){
		Intent intent = new Intent(activity, PatientInfo.class);
		intent.putExtra("pid", pid);
		activity.startActivity(intent);
		//activity.finish();
	}
	
	public static void showEvent(Activity activity,PatientDTO patient){
		Intent intent = new Intent(activity, EventMain.class);
		intent.putExtra("patient",patient);
		activity.startActivity(intent);
		//activity.finish();
	}
	
	public static void showEventEdit(Activity activity,PatientDTO patient,EventDTO event){
		Intent intent = new Intent(activity, EventInput.class);
		intent.putExtra("patient",patient);
		intent.putExtra("event",event);
		activity.startActivityForResult(intent,200);
		//activity.finish();
	}
	
	public static void showExameResult(Activity activity,ExamOrderDTO dto,PatientDTO patient){
		Intent intent = new Intent(activity, ExamResult.class);
		intent.putExtra("examorder", dto);
		intent.putExtra("patient",patient);
		activity.startActivity(intent);
		//activity.finish();
	}
	public static void showOrderEnterSelect(Activity activity,int code,PatientDTO patient,int inputType,String inputData){
		Intent intent = new Intent(activity, OrderEnterItemSelect.class);
		intent.putExtra("inputData", inputData);
		intent.putExtra("inputType", inputType);
		intent.putExtra("patient",patient);
		activity.startActivityForResult(intent,code);
		
	}
	public static void showBedGroupSelect(Activity activity){
		Intent intent = new Intent(activity, SettingBedGroup.class);
	
		activity.startActivity(intent);
		
	}
	
	
	public static void showMain(Activity activity,ArrayList<GroupLoc> list){
		Intent intent = new Intent(activity, MainActivity.class);
		intent.putExtra("departselect", list);
		activity.startActivity(intent);
		activity.finish();
	}
	
	public static void showBlueSetting(Activity activity){
		Intent intent = new Intent(activity, BlueSetting.class);
		activity.startActivity(intent);
	}
	

	public static void ToastFailMessage(Context cont,Throwable throwable){
		Toast.makeText(cont, "没有数据", Toast.LENGTH_SHORT).show(); 
	}

	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						Intent i = new Intent(Intent.ACTION_SEND);
						// i.setType("text/plain"); //模拟器
						i.setType("message/rfc822"); // 真机
						i.putExtra(Intent.EXTRA_EMAIL,
								new String[] { "27522982@qq.com" });
						i.putExtra(Intent.EXTRA_SUBJECT,
								"护士执行 - 错误报告");
						i.putExtra(Intent.EXTRA_TEXT, crashReport);
						cont.startActivity(Intent.createChooser(i, "发送错误报告"));
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}

}
