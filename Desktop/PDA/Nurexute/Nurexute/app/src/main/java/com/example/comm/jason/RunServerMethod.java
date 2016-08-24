package com.example.comm.jason;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RunServerMethod {
//	final private  String baseuri="http://172.22.134.19/trakcarelive/trak/web/web.JSONBroker.cls";
	
	public RunServerMethod(){
		
	}
	
	
	public static void Get(AsyncHttpResponseHandler responseHandler ,final String className,final String methodName,final String ...jasonString){
	
	RequestParams params= new RequestParams();
	params.put("isobj", "0");

	
	params.put("classname", className);
	params.put("method", methodName);
	int parm=0;
	if (jasonString!=null){
		parm=jasonString.length;
	}
	params.put("parm", String.valueOf(parm));
	for(int i=0;i<parm;i++){
		params.put("parm"+(i+1), jasonString[i]);
	}
	
	TwitterRestClient.get("", params, responseHandler);
	
}
	public static void Post(AsyncHttpResponseHandler responseHandler ,final String className,final String methodName,final String ...jasonString){
		
		RequestParams params= new RequestParams();
		params.put("isobj", "0");
		
		
		params.put("classname", className);
		params.put("method", methodName);
		int parm=0;
		if (jasonString!=null){
			parm=jasonString.length;
		}
		params.put("parm", String.valueOf(parm));
		for(int i=0;i<parm;i++){
			params.put("parm"+(i+1), jasonString[i]);
		}

		TwitterRestClient.post("", params, responseHandler);
		
	}
	
	public static void GetRunQuery(AsyncHttpResponseHandler responseHandler ,final String className,final String methodName,final String ...jasonString){
	RequestParams params= new RequestParams();
	params.put("isobj", "2");
	params.put("classname", className);
	params.put("method", methodName);
	int parm=0;
	if (jasonString!=null){
		parm=jasonString.length;
	}
	params.put("parm", String.valueOf(parm));
	for(int i=0;i<parm;i++){
		params.put("parm"+(i+1), jasonString[i]);
	}
		TwitterRestClient.get("", params, responseHandler);
}
}
