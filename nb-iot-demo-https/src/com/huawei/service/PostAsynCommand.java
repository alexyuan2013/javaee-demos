package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.nari.lamp.service.Auth;
import com.nari.lamp.service.Constants;

public class PostAsynCommand {

	public static void main(String args[]) throws Exception {

		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); //  Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String deviceId = "241266ab-f304-4ea3-bb31-0879513187f1";  //please replace the deviceId, when you use the demo.
        String urlPostAsynCmd = Constants.URL + "/iocm/app/cmd/v1.2.0/devices/%s/commands";//please replace the IP and Port, when you use the demo.
        urlPostAsynCmd = String.format(urlPostAsynCmd, deviceId);
        

        String serviceId = "RawData";  //  please replace the serviceId, when you use the demo.
        String method = "RawData";  //  please replace the method, when you use the demo.
        ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"rawData\":\"aA8AaAGAACACAAAAAAAAAAEAAAAAFg==\"}");//  please replace the paras, when you use the demo.
        String callbackUrl = "http://139.196.227.69:9080/narilamp/api/postFunc";//  please replace the callbackUrl, when you use the demo.
      
        Map<String, Object> paramCommand = new HashMap<String, Object>();
        paramCommand.put("serviceId", serviceId);
        paramCommand.put("method", method);
        paramCommand.put("paras", paras);      
        
        Map<String, Object> paramPostAsynCmd = new HashMap<String, Object>();
        paramPostAsynCmd.put("command", paramCommand);
        paramPostAsynCmd.put("callbackUrl", callbackUrl);
        paramPostAsynCmd.put("expireTime",110);//   please replace the expireTime, when you use the demo.
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        HttpResponse httpResponse = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);

        String responseBody = httpsUtil.getHttpResponseBody(httpResponse);

        System.out.println(responseBody);
    }
	
	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0"; // please replace the appId, when you use the demo.
		String secret = "875eadb27b0fc4df5a6f"; //   please replace the secret, when you use the demo.
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
