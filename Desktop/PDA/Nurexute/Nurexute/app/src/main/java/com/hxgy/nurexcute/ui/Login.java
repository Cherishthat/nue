package com.hxgy.nurexcute.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.CurUser;
import com.hxgy.nurexcute.R;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.AppException;
import com.hxgy.nurexcute.common.StringUtils;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.GroupLoc;
import com.hxgy.nurexcute.dto.UserResultMess;
import com.hxgy.nurexcute.dto.User;
import com.hxgy.nurexcute.widget.wheel.WheelView;
import com.hxgy.nurexcute.widget.wheel.adapters.ArrayWheelAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewSwitcher;

public class Login extends Activity {
	private ViewSwitcher mViewSwitcher;
	private Button btn_login;
	private AutoCompleteTextView mAccount;
	private EditText mPwd;
	private AnimationDrawable loadingAnimation;
	private View loginLoading;
	private InputMethodManager imm;

	private Gson gson = new Gson();
	
	public final static int LOGIN_OTHER = 0x00;
	public final static int LOGIN_MAIN = 0x01;
	public final static int LOGIN_SETTING = 0x02;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog);
        
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        
        
        mViewSwitcher = (ViewSwitcher)findViewById(R.id.logindialog_view_switcher);       
        loginLoading = (View)findViewById(R.id.login_loading);
        mAccount = (AutoCompleteTextView)findViewById(R.id.login_account);
        mPwd = (EditText)findViewById(R.id.login_password);
        
        
        btn_login = (Button)findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
				
				String account = mAccount.getText().toString();
				String pwd = mPwd.getText().toString();
				if(StringUtils.isEmpty(account)){
					UIHelper.ToastMessage(v.getContext(), getString(R.string.msg_login_uid_null));
					return;
				}
				if(StringUtils.isEmpty(pwd)){
					UIHelper.ToastMessage(v.getContext(), getString(R.string.msg_login_pwd_null));
					return;
				}
				
		        loadingAnimation = (AnimationDrawable)loginLoading.getBackground();
		        loadingAnimation.start();
		        mViewSwitcher.showNext();
		        
		        login(account, pwd);
			}
		});

      
    }
    
    private void login(final String account, final String pwd) {
    	ApiClient.getUser(account, pwd, new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(String result) {
				super.onSuccess(result);

				List<UserResultMess> obj = gson.fromJson(result,new TypeToken<List<UserResultMess>>(){}.getType() );
				UserResultMess userResult = obj.get(0);
				if (!userResult.getCode().equals("0")){
					UIHelper.ToastMessage(Login.this, userResult.getMessage());
					mViewSwitcher.showPrevious();
					return;
				}
				User user =userResult.getUser();
				CurUser.userId=user.getUid();
				CurUser.userName=user.getName();
				CurUser.hospId=user.getHospId();
				ArrayList<GroupLoc> list= (ArrayList<GroupLoc>) user.getList();
				mViewSwitcher.showPrevious();
				UIHelper.showMain(Login.this,list);
			}
			 public void onFailure(Throwable trowable,String arg1) {
				 mViewSwitcher.showPrevious();
				 AppException.network((Exception)trowable).makeToast(Login.this);
			 }

    		
    		
    	});
    	
			
		
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK) {
    		this.onDestroy();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}