package com.huawei.service;
import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.nari.lamp.service.Constants;

/**
 * 
 * */
public class Authentication
{   
    @SuppressWarnings("resource")
	public static void main(String args[]) throws Exception
    {
    	
        HttpsUtil httpUtils = new HttpsUtil();
        httpUtils.initSSLConfigForTwoWay();  //Two-Way Authentication
    	
        String appId = Constants.APP_ID;  //please replace the appId, when you use the demo.
        String secret = Constants.SECERT;    //please replace the secret, when you use the demo.
        String url = Constants.URL + "/iocm/app/sec/v1.1.0/login";  //please replace the IP and Port, when you use the demo.
    	
        Map<String, String> param = new HashMap<String, String>();
        param.put("appId", appId);
        param.put("secret", secret);
        
        String body =  httpUtils.doPostFormUrlEncodedForString(url, param);        

        System.out.println(body);
    }
}
