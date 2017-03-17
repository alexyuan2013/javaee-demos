package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class RegisterDirectlyConnectedDevice {

	public static void main(String args[]) throws Exception {

		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = "f4a8ecb5-cb2b-4955-b822-8fccdee07cc1"; // please replace the appId, when you use the demo.
		String urlReg = "https://122.96.38.144:8743/iocm/app/reg/v1.1.0/devices";// please replace the IP and Port, when you use the demo.
		String verifyCode = "abcdefghi222"; //   please replace the verifyCode, when you use the demo.
		String nodeId = "abcdefghi222"; // please replace the nodeId, when you use the demo.
		String EndUserId = "currentuser"; // please replace the currentuser, when you use the demo.

		Map<String, Object> paramReg = new HashMap<String, Object>();
		paramReg.put("verifyCode", verifyCode.toUpperCase());
		paramReg.put("nodeId", nodeId.toUpperCase());
		paramReg.put("endUserId", EndUserId);
		paramReg.put("timeout", 0); //   please replace the timeout, when you use the demo.
  
		String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);

		Map<String, String> header = new HashMap<>();
		header.put("app_key", appId);
		header.put("Authorization", "Bearer " + accessToken);

		String bodyReg = httpsUtil.doPostJsonForString(urlReg, header,
				jsonRequest);

		System.out.println(bodyReg);
	}

	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = "f4a8ecb5-cb2b-4955-b822-8fccdee07cc1"; // please replace the appId, when you use the demo.
		String secret = "74a42775d080921e1e7e"; // please replace the secret, when you use the demo.
		String urlLogin = "https://122.96.38.144:8743/iocm/app/sec/v1.1.0/login"; //please replace the IP and Port, when you use the demo.

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
