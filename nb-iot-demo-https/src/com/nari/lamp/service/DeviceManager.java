package com.nari.lamp.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;
import com.huawei.utils.JsonUtil;

/**
 * 设备管理接口
 * @author alex
 *
 */
public class DeviceManager {
	/**
	 * 注册直连设备
	 * @param verifyCode
	 * @param nodeId
	 * @param EndUserId
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static void registerDirectlyConnectedDevice(String verifyCode, String nodeId, String EndUserId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID; // please replace the appId, when you use the demo.
		String urlReg = Constants.URL + "/iocm/app/reg/v1.1.0/devices";// please replace the IP and Port, when you use the demo.
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
	 * 删除直连设备
	 * @param deviceId 设备id号
	 * @throws Exception 
	 */
	public static void deleteDirectlyConnectedDevice(String deviceId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication
		String accessToken = Auth.getAccessToken();
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlDelete = Constants.URL + "/iocm/app/dm/v1.1.0/devices/"+deviceId; //please replace the IP and Port, when you use the demo.
        
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyDelete = httpsUtil.doDeleteForString(urlDelete, header);

        System.out.println(bodyDelete);
        
        
	}
	
	public static void main(String[] args) {
		
		try {
			//注册直连设备
			//registerDirectlyConnectedDevice("863703030016932", "863703030016932", "currentuser");
			//删除直连设备50c5c7b3-5b49-4b12-859c-2922000a7145
			deleteDirectlyConnectedDevice("20a43169-e9ab-40f9-9d9e-d3df56e9f813");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
