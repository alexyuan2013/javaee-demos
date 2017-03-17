package com.nari.lamp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.nari.lamp.service.Auth;
import com.nari.lamp.service.Constants;

public class PostAsynCommand {
	
	public static void post(String src, String deviceId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); //  Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlPostAsynCmd = Constants.URL + "/iocm/app/cmd/v1.2.0/devices/%s/commands";//please replace the IP and Port, when you use the demo.
        urlPostAsynCmd = String.format(urlPostAsynCmd, deviceId);
        

        String serviceId = "RawData";  //  please replace the serviceId, when you use the demo.
        String method = "RawData";  //  please replace the method, when you use the demo.
        ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"rawData\":\"" + src + "\"}");//  please replace the paras, when you use the demo.
        String callbackUrl = "http://139.196.227.69:9080/narilamp/api/postFunc";//  please replace the callbackUrl, when you use the demo.
      
        Map<String, Object> paramCommand = new HashMap<String, Object>();
        paramCommand.put("serviceId", serviceId);
        paramCommand.put("method", method);
        paramCommand.put("paras", paras);      
        
        Map<String, Object> paramPostAsynCmd = new HashMap<String, Object>();
        paramPostAsynCmd.put("command", paramCommand);
        paramPostAsynCmd.put("callbackUrl", callbackUrl);
        paramPostAsynCmd.put("expireTime",0);//   please replace the expireTime, when you use the demo.
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        HttpResponse httpResponse = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);

        String responseBody = httpsUtil.getHttpResponseBody(httpResponse);

        System.out.println(responseBody);
	}

	public static void main(String args[]) throws Exception {

		post("aA8AaAGAAAACBAACIBICABAAAG9+Fg==", "957bcedb-4991-4dd9-a016-3bfe049471e0");
    }
}
