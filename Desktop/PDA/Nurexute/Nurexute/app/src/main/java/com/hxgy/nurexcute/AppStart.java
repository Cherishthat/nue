package com.hxgy.nurexcute;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxgy.nurexcute.api.ApiClient;
import com.hxgy.nurexcute.common.DialogTool;
import com.hxgy.nurexcute.common.UIHelper;
import com.hxgy.nurexcute.dto.VersonDTO;
import com.hxgy.nurexcute.ui.Login;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class AppStart extends Activity {
	public ProgressDialog pBar;
	final String SAVENAME="nurexcute.apk";
	final String UPDATE_SERVER="http://172.77.67.23/";
	private Handler handler = new Handler();
	Dialog dialog=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.start, null);
		setContentView(view);
//		update();
//		checkServierVerson(view);
		redirectTo();
    }
    
    private void doNewVersionUpdate(final View v) {

		dialog=DialogTool.createConfirmDialog(this, "提示", "有新版本是否更新系统!", "更新", "取消", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				  pBar = new ProgressDialog(AppStart.this);
                  pBar.setTitle("正在下载");
                  pBar.setMessage("请稍候...");
                  pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                  downFile(UPDATE_SERVER+SAVENAME);
			}

		}, new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				show(v);
			}

		}, DialogTool.NO_ICON);
		dialog.show();
    }
    
    private void checkServierVerson(View v) {
    	ApiClient.getServerVerCode(new ServierVersonHandle(this,v));
    }
    class ServierVersonHandle extends AsyncHttpResponseHandler {
		Context context =null;
		View v;
	    public ServierVersonHandle(Context context,View v) {
	    	this.context =context;
	    	this.v=v;
	    }
		@Override
		public void onSuccess(String result) {
			super.onSuccess(result);
			try {
			Gson gson = new Gson();
			List<VersonDTO> p = gson.fromJson(result,new TypeToken<List<VersonDTO>>(){}.getType() );
			VersonDTO o = p.get(0);
			int serverVerson= Integer.parseInt(o.getVersonCode());
			int localVerson=getVerCode(context);
			if(serverVerson>localVerson) {
				doNewVersionUpdate(v);
			}else{
				redirectTo();
			}
			 
			
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
    public  int getVerCode(Context context) {
		int verCode = -1;
		try {
			 verCode = context.getPackageManager().getPackageInfo(
					 "com.hxgy.nurexcute", 0).versionCode;
		} catch (NameNotFoundException e) {
			
		}
		 return verCode;
		}
	
    
    private void show(View view ){
    	AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}

		});
		
    }
    
    /**
     */
    private void redirectTo(){        
        //Intent intent = new Intent(this, Login.class);
    	Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

	@Override
	protected void onDestroy() {
//		pBar.dismiss();
//		 dialog.dismiss();
		super.onDestroy();


	}

	void downFile(final String url) {
        pBar.show();
        new Thread() {
                public void run() {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet get = new HttpGet(url);
                        HttpResponse response;
                        try {
                                response = client.execute(get);
                                HttpEntity entity = response.getEntity();
                                long length = entity.getContentLength();
                                InputStream is = entity.getContent();
                                FileOutputStream fileOutputStream = null;
                                if (is != null) {
                                        File file = new File(
                                                        Environment.getExternalStorageDirectory(),
                                                        SAVENAME);
                                        fileOutputStream = new FileOutputStream(file);
                                        byte[] buf = new byte[1024];
                                        int ch = -1;
                                        int count = 0;
                                        while ((ch = is.read(buf)) != -1) {
                                                fileOutputStream.write(buf, 0, ch);
                                                count += ch;
                                                if (length > 0) {
                                                }
                                        }

                               }
                                fileOutputStream.flush();
                                if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                }
							dialog.dismiss();
							pBar.dismiss();
                               down();

                       } catch (ClientProtocolException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }

               }

      }.start();


}



void down() {
        handler.post(new Runnable() {
                public void run() {
                        pBar.cancel();
                        update();

               }

       });

}
void update() {

	try {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), SAVENAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}catch (Exception ex){
		Log.e("ddddd",ex.getMessage().toString());
	}
} 
    
    
}