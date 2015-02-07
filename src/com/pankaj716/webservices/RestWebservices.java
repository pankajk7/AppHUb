package com.pankaj716.webservices;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.pankaj716.apphub.R;
import com.pankaj716.utils.TransparentProgressDialog;
import com.restservice.Http;
import com.restservice.HttpFactory;
import com.restservice.HttpResponse;
import com.restservice.NetworkError;
import com.restservice.ResponseHandler;

public class RestWebservices {

	Context context;
	String baseURL;
	String urlSuffix;
	String resourceName;
//	ProcessLoaderIndication process;
	TransparentProgressDialog transparentProgressDialog;
	String cookieString;

	// String authorizationString;

	public RestWebservices(Context context) {
		baseURL = Constants.BASE_URL;
		urlSuffix = Constants.SUFFIX_URL;
		this.context = context;
		transparentProgressDialog = new TransparentProgressDialog(context,
				R.drawable.load_2arrow);
	}

	private String getServiceURL(String resourceName, String extraParameters) {
		return baseURL + urlSuffix + resourceName + extraParameters;
	}

	public void onComplete() {
		if (transparentProgressDialog.isShowing()) {
			transparentProgressDialog.dismiss();
		}
	}

	public void onFailure(NetworkError error) {

	}

	public void onError(String message, HttpResponse response) {

	}

	public void onSuccess(String message, String success) {

	}

	public void onSuccess(Object object, HttpResponse response) {
	}

	public void onSuccess(Bitmap object, HttpResponse response) {
	}
	
	public void onSuccess(Object[] object, HttpResponse response) {
	}

	public void onSuccess(String data, HttpResponse response) {

	}

	public void serviceCall(String resourceName, String extraParameters) {
		String url = getServiceURL(resourceName, extraParameters);
		
		if(resourceName.equalsIgnoreCase(Constants.PRODUCTS)){
			getCall(url);
		}

	}

	public void serviceCall(String resourceName, String extraParameters,
			Object object) {
		String url = getServiceURL(resourceName, extraParameters);

		
	}
	
	private void getCall(String url){
		transparentProgressDialog.show();
		Http http = HttpFactory.create(this.context);
		http.get(url)
				.timeout(Constants.TIMEOUT)
				.handler(new ResponseHandler<String>() {
					@Override
					public void success(String data, HttpResponse response) {
						onSuccess(data, response);
						super.success(data, response);
					}

					@Override
					public void complete() {
						onComplete();
						super.complete();
					}

					@Override
					public void failure(NetworkError error) {
						onFailure(error);
						super.failure(error);
					}

					@Override
					public void error(String message, HttpResponse response) {
						onError(message, response);
						super.error(message, response);
					}
				}).send();
	}
	
}
