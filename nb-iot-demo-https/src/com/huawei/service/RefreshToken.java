package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class RefreshToken {

	public static void main(String args[]) throws Exception {
	

		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String refreshToken = getRefreshToken(httpsUtil); //get refreshToken
		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0";    //please replace the appId, when you use the demo.
		String secret = "875eadb27b0fc4df5a6f";    // please replace the secret, when you use the demo.
        String urlRefreshToken = "https://183.1.8.114:8743/iocm/app/sec/v1.1.0/refreshToken";//please replace the IP and Port, when you use the demo.
        
        Map<String, Object> param_reg = new HashMap<String, Object>();
        param_reg.put("appId", appId);
        param_reg.put("secret", secret);
        param_reg.put("refreshToken", refreshToken);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        String bodyRefreshToken = httpsUtil.doPostJsonForString(urlRefreshToken, jsonRequest);

        System.out.println(bodyRefreshToken);
    }
	
	/**
	 * get refreshToken
	 * */
    @SuppressWarnings("unchecked")
	public static String getRefreshToken(HttpsUtil httpsUtil) throws Exception
    {
        String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0";   //please replace the appId, when you use the demo.
        String secret = "875eadb27b0fc4df5a6f";    // please replace the secret, when you use the demo.
        String urlLogin = "https://183.1.8.114:8743/iocm/app/sec/v1.1.0/login";  //please replace the IP and Port, when you use the demo.
    	
        Map<String, String> paramLogin = new HashMap<String, String>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);
        
        String bodyLogin =  httpsUtil.doPostFormUrlEncodedForString(urlLogin, paramLogin);        
        System.out.println(bodyLogin);
        
        Map<String, String> data = new HashMap<String, String>();         
        data = JsonUtil.jsonString2SimpleObj(bodyLogin, data.getClass());    
        String refreshToken = data.get("refreshToken");
        return refreshToken;
    }
}
