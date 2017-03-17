package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class QueryAsynCommands {


	public static void main(String args[]) throws Exception {

		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0";    //please replace the appId, when you use the demo.
        String deviceId = "882cff80-e6d9-4f48-bd48-548386c4e336";  //  please replace the deviceId, when you use the demo.
        String urlQueryDeviceCMD = "https://183.1.8.114:8743/iocm/app/cmd/v1.2.0/queryCmd";  //please replace the IP and Port, when you use the demo.
         
        Map<String, String> paramQueryDeviceCMD = new HashMap<String, String>();
        paramQueryDeviceCMD.put("deviceId", deviceId);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyQueryDeviceCMD = httpsUtil.doGetWithParasForString(urlQueryDeviceCMD, paramQueryDeviceCMD, header);


        System.out.println(bodyQueryDeviceCMD);
    }
	
	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		 String appId = "f4a8ecb5-cb2b-4955-b822-8fccdee07cc1";  //please replace the appId, when you use the demo.
	     String secret = "74a42775d08921e1e7e";    //please replace the secret, when you use the demo.
	     String url = "https://122.96.38.144:8843/iocm/app/sec/v1.1.0/login";  //please replace the IP and Port, when you use the demo.

		Map<String, String> paramLogin = new HashMap<String, String>();
		paramLogin.put("appId", appId);
		paramLogin.put("secret", secret);

		String bodyLogin = httpsUtil.doPostFormUrlEncodedForString(url,
				paramLogin);
		System.out.println(bodyLogin);

		Map<String, String> data = new HashMap<String, String>();
		data = JsonUtil.jsonString2SimpleObj(bodyLogin, data.getClass());
		String accessToken = data.get("accessToken");
		return accessToken;
	}
}
