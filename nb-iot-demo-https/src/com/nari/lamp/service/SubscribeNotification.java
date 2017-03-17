package com.nari.lamp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

/**
 * 订阅设备通知
 * @author alex
 *
 */
public class SubscribeNotification {
	
	@SuppressWarnings("resource")
	public static void subscribe(String notifyType, String callbackUrl) throws Exception{
		
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlSubscribe = Constants.URL + "/iocm/app/sub/v1.1.0/subscribe";  //please replace the IP and Port, when you use the demo.
        Map<String, Object> paramSubscribe = new HashMap<String, Object>();
        paramSubscribe.put("notifyType", notifyType);
        paramSubscribe.put("callbackurl", callbackUrl);        
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramSubscribe);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        HttpResponse httpResponse = httpsUtil.doPostJson(urlSubscribe, header, jsonRequest);
        String bodySubscribe = httpsUtil.getHttpResponseBody(httpResponse);
        
        System.out.println(bodySubscribe);
	}
	
	public static void main(String[] args) {
		try {
			//设备激活通知
			subscribe("bindDevice", "http://www.smartnari.com:3009/api/notification");
			//添加新设备
			subscribe("deviceAdded", "http://www.smartnari.com:3009/api/notification");
			//deviceInfoChanged——设备信息变化
			//deviceDataChanged（设备数据变化）
			subscribe("deviceDataChanged", "http://www.smartnari.com:3009/api/notification");
			//deviceDeleted（删除设备）
			//deviceEvent（设备事件）
			//messageConfirm（消息确认）
			//commandRsp（响应命令）
			//serviceInfoChanged（设备信息）
			//ruleEvent（规则事件）
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
