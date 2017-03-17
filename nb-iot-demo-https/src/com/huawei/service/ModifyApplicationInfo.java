package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class ModifyApplicationInfo {

	public static void main(String args[]) throws Exception {
	
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); //Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0";    //please replace the appId, when you use the demo.
        String urlModifyAppInfo = "https://183.1.8.114:8743/iocm/app/am/v1.1.0/applications/"+appId;//please replace the IP and Port, when you use the demo.
        Long abnormalTime= 10000L;//please replace the value, when you use the demo.
        Long offlineTime = 10000L;//please replace the value, when you use the demo.
                
        Map<String, Object> deviceStatusTimeConfig = new HashMap<String, Object>();
        deviceStatusTimeConfig.put("abnormalTime", abnormalTime.toString());
        deviceStatusTimeConfig.put("offlineTime", offlineTime.toString());        
        
        Map<String, Object> paramModifyAppInfo = new HashMap<String, Object>();
        paramModifyAppInfo.put("deviceStatusTimeConfig", deviceStatusTimeConfig);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyAppInfo);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyModifyAppInfo = httpsUtil.doPutJsonForString(urlModifyAppInfo, header, jsonRequest);

        System.out.println(bodyModifyAppInfo);
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
