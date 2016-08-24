package com.example.comm.jason;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class TwitterRestClient {
	
	//private static final String BASE_URL = "http://172.76.83.11/trakcarelive/trak/web/web.JSONBroker.cls";
//	private static final String BASE_URL = "http://172.22.134.19/trakcarelive/trak/web/web.JSONBroker.cls";
//	private static final String BASE_URL = "http://172.77.67.100/trakcarelive/trak/web/web.JSONBroker.cls";
	//	private static final String BASE_URL = "http://172.77.67.100/trakcarelive/trak/web/web.JSONBroker.cls";
	private static final String BASE_URL = "http://10.88.67.131/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.74.252.34/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.74.252.74/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.22.77.67.1/trakcarelive/trak/web/web.JSONBroker.cls";
//	private static final String BASE_URL = "http://172.22.6.10/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.80.19.1/trakcareli9ve/trak/web/web.JSONBroker.cls";
//	private static final String BASE_URL = "http://172.22.6.10/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.74.17.49/trakcarelive/trak/web/web.JSONBroker.cls";
	//private static final String BASE_URL = "http://172.74.130.129/trakcarelive/trak/web/web.JSONBroker.cls";
    //028666 1234
	  private static AsyncHttpClient client = new AsyncHttpClient();
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	  client.setTimeout(240000);
		  client.get(getAbsoluteUrl(url), params, responseHandler);
		  
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		  client.setTimeout(240000);
		  client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }


}
