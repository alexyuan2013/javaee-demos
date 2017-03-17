package com.huawei.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;
import com.nari.lamp.service.Constants;
/**
 * 查询设备
 * @author alex
 *
 */
public class QueryDevices {

	public static void main(String args[]) throws Exception {

        
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = login(httpsUtil); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlQueryDevices = Constants.URL + "/iocm/app/dm/v1.1.0/devices";//please replace the IP and Port, when you use the demo.
        Integer pageNo = 0;   //  please replace the pageNo, when you use the demo.
        Integer pageSize = 10;  //   please replace the pageSize, when you use the demo.

        Map<String, String> paramQueryDevices = new HashMap<String, String>();
        paramQueryDevices.put("appId", appId);
        paramQueryDevices.put("pageNo", pageNo.toString());
        paramQueryDevices.put("pageSize", pageSize.toString());
 
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        String bodyQueryDevices = httpsUtil.doGetWithParasForString(urlQueryDevices, paramQueryDevices, header);

        System.out.println(bodyQueryDevices);
    }
	
	/**
	 * Authentication，get token
	 * */
	@SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {

		String appId = Constants.APP_ID; // please replace the appId, when you use the demo.
		String secret = Constants.SECERT; //  please replace the secret, when you use the demo.
		String urlLogin = Constants.URL + "/iocm/app/sec/v1.1.0/login"; //please replace the IP and Port, when you use the demo.

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
