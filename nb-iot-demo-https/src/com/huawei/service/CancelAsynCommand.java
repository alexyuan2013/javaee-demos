package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.nari.lamp.service.Constants;

public class CancelAsynCommand {

	public static void main(String args[]) throws Exception {
	
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication 
		
		String accessToken = login(httpsUtil); //Authentication, get token
		String appId = Constants.APP_ID;//please replace the appId, when you use the demo.
        String deviceId = "882cff80-e6d9-4f48-bd48-548386c4e336";//please replace the deviceId, when you use the demo.
        String urlCancelAsynCommand = Constants.URL + "/iocm/app/cmd/v1.2.0/cancelCmd";//please replace the IP and Port, when you use the demo.
                
        Map<String, Object> paramCancelAsynCommand = new HashMap<String, Object>();
        paramCancelAsynCommand.put("deviceId", deviceId);
                
        String jsonRequest = JsonUtil.jsonObj2Sting(paramCancelAsynCommand);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        HttpResponse bodyCancelAsynCommand = httpsUtil.doPostJson(urlCancelAsynCommand, header, jsonRequest);
        String responseBody = httpsUtil.getHttpResponseBody(bodyCancelAsynCommand);
        System.out.println(responseBody);
    }
    
	
	
	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0"; // please replace the appId, when you use the demo.
		String secret = "875eadb27b0fc4df5a6f"; // please replace the secret, when you use the demo.
		String urlLogin = "https://183.1.8.114:8743/iocm/app/sec/v1.1.0/login"; // please replace the IP and Port, when you use the demo.

		Map<String, String> paramLogin = new HashMap<String, String>();
		paramLogin.put("appId", appId);
		paramLogin.put("secret", secret);

		String bodyLogin = httpsUtil.doPostFormUrlEncodedForString(urlLogin,
				paramLogin);
		System.out.println(bodyLogin);

		Map<String, String> data = new HashMap<String, String>();
		data = JsonUtil.jsonString2SimpleObj(bodyLogin, data.getClass());
		String accessToken = data.get("accessToken");
		return accessToken;
	}
}
