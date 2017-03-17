package com.nari.lamp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

/**
 * 鉴权
 * @author alex
 *
 */
public class Auth {
	private static String accessToken = "";
	private static String refreshToken = "";
	private static Date updateTime = null;
	/**
	 * 获取accessToken
	 * @return
	 */
	public static String getAccessToken(){
		//判断token是否获取过
		if("".equals(accessToken)){
			try {
				System.out.println("Get new accesstoken!");
				login();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//判断token是否过期
		if(updateTime != null && (new Date().getTime() - updateTime.getTime()> 40000000)){
			try {
				System.out.println("Refresh accesstoken!");
				refreshToken();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return accessToken;
	}
	/**
	 * 登录，获取token
	 * @return token
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	private static void login() throws Exception{
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
        
        Map<String, String> data = new HashMap<String, String>();
		data = JsonUtil.jsonString2SimpleObj(body, data.getClass());
		accessToken = data.get("accessToken");
		refreshToken = data.get("refreshToken");
		updateTime = new Date();
	}
	/**
	 * 刷新Token
	 * @throws Exception 
	 */
	private static void refreshToken() throws Exception{
		HttpsUtil httpUtils = new HttpsUtil();
        httpUtils.initSSLConfigForTwoWay();  //Two-Way Authentication
    	
        String appId = Constants.APP_ID;  //please replace the appId, when you use the demo.
        String secret = Constants.SECERT;    //please replace the secret, when you use the demo.
		//刷新token接口
		String urlRefreshToken = Constants.URL + "/iocm/app/sec/v1.1.0/refreshToken";
		
		Map<String, Object> param_reg = new HashMap<String, Object>();
        param_reg.put("appId", appId);
        param_reg.put("secret", secret);
        param_reg.put("refreshToken", refreshToken);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        String bodyRefreshToken = httpUtils.doPostJsonForString(urlRefreshToken, jsonRequest);

        System.out.println(bodyRefreshToken);
        Map<String, String> data = new HashMap<String, String>();
		data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken, data.getClass());
		accessToken = data.get("accessToken");
		refreshToken = data.get("refreshToken");
		updateTime = new Date();
	}
	
	public static void main(String args[]){
		try {
			Auth.login();
			System.out.println(Auth.getAccessToken());
			Auth.refreshToken();
			System.out.println(Auth.getAccessToken());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
