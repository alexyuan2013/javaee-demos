package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class QueryDeviceActivationStatus {

	public static void main(String args[]) throws Exception {
	 
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = "f4a8ecb5-cb2b-4955-b822-8fccdee07cc1";    //please replace the appId, when you use the demo.
        String deviceId = "77c26a9d-8e5c-46d2-b815-f701a60ad4d5";  //please replace the deviceId, when you use the demo.
        String urlDeviceActivationStatus = "https://122.96.38.144:8743/iocm/app/reg/v1.1.0/devices/"+deviceId;//please replace the IP and Port, when you use the demo.
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);

        String bodyDeviceActivationStatus = httpsUtil.doGetWithParasForString(urlDeviceActivationStatus, null, header);

        System.out.println(bodyDeviceActivationStatus);
    }
	
	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = "f4a8ecb5-cb2b-4955-b822-8fccdee07cc1"; // please replace the appId, when you use the demo.
		String secret = "74a42775d080921e1e7e"; // please replace the secret, when you use the demo.
		String urlLogin = "https://122.96.38.144:8743/iocm/app/sec/v1.1.0/login"; // please replace the IP and Port, when you use the demo.

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
