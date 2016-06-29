package com.smartshell.common;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/*
 *
 * 
 */
public class power {
	
	private static final String TAG = "power";
	private FileDescriptor mPowerFd;
	  
	public power(Context context)
	{   
		//if(initpower(context)==1)
		if(init(context)==1)
		 {
				Log.e(TAG, "initpower is ok"); 

		 }
		else
		{
			Log.e(TAG, "initpower error");
		}
	}
   
	
	static {
		System.loadLibrary("smartshelljni");/*����JNI��*/
		}   
	   
	   
    public  native String bardatachieve(byte[] indata,int len);
    public  native String rfiddatachieve(byte[] indata,int len);
    public  native String tbardatachieve(byte[] indata,int len);  
    
    private  native int initpower(Context context);   
    private  native int init(Context context);
    public  native int getick();

   // public   native int send(String address,String cmd);   

}
