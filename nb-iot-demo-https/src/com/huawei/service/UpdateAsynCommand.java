package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

public class UpdateAsynCommand {

	public static void main(String args[]) throws Exception {

		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = "c5999872-f6e0-4663-8946-c2ff8b2baae0";    //please replace the appId, when you use the demo.
        String deviceId = "882cff80-e6d9-4f48-bd48-548386c4e336";//please replace the deviceId, when you use the demo.
        String commandId = "6704283ef85845a4a61dc580d73d8e42";// please replace the commandId, when you use the demo.
        String urlUpdateAsynCommand = "https://183.1.8.114:8743/iocm/app/cmd/v1.2.0/devices/%s/commands/%s"; //please replace the IP and Port, when you use the demo.
        urlUpdateAsynCommand = String.format(urlUpdateAsynCommand, deviceId, commandId);
        
        String resultCode = "CANCELED"; //  please replace the resulCode, when you use the demo.
        ObjectNode resultDetail = null; //  please replace the resultDetail, when you use the demo.
              
        Map<String, Object> paramUpdateAsynCommand = new HashMap<String, Object>();
        paramUpdateAsynCommand.put("resultCode", resultCode);
        paramUpdateAsynCommand.put("resultDetail", resultDetail);  
        
        Map<String, Object> paramPostAsynCmd = new HashMap<String, Object>();
        paramPostAsynCmd.put("result", paramUpdateAsynCommand);
                   
        String jsonRequest = JsonUtil.jsonObj2Sting(paramUpdateAsynCommand);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyUpdateAsynCommand = httpsUtil.doPutJsonForString(urlUpdateAsynCommand, header, jsonRequest);

        System.out.println(bodyUpdateAsynCommand);
        
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
