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
	/**
	 * 更改应用信息
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void modifyApplicationInfo() throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); //Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlModifyAppInfo = Constants.URL + "/iocm/app/am/v1.1.0/applications/"+appId;//please replace the IP and Port, when you use the demo.
        Long abnormalTime= 10L;//please replace the value, when you use the demo.
        Long offlineTime = 10L;//please replace the value, when you use the demo.
                
        Map<String, Object> deviceStatusTimeConfig = new HashMap<String, Object>();
        deviceStatusTimeConfig.put("abnormalTime", abnormalTime.toString());
        deviceStatusTimeConfig.put("offlineTime", offlineTime.toString());        
        
        Map<String, Object> paramModifyAppInfo = new HashMap<String, Object>();
        paramModifyAppInfo.put("deviceStatusTimeConfig", deviceStatusTimeConfig);
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyAppInfo);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyModifyAppInfo = httpsUtil.doPutJsonForString(urlModifyAppInfo, header, jsonRequest);

        System.out.println(bodyModifyAppInfo);
	}
	/**
	 * 更改设备信息
	 * @param deviceId 设备id
	 * @throws Exception
	 */
	public static void modifyDeviceInfo(String deviceId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); //Two-Way Authentication

		String accessToken = Auth.getAccessToken(); //Authentication，get token
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlModifyDeviceInfo = Constants.URL + "/iocm/app/dm/v1.1.0/devices/"+deviceId;  //please replace the IP and Port, when you use the demo.
        String manufacturerId= "narilamp";  //  please replace the manufacturerId, when you use the demo.
        String manufacturerName = "narilamp";  //  please replace the manufacturerName, when you use the demo.
        String deviceType = "lamp";  //please replace the deviceType, when you use the demo.
        String model = "roadlamp";  // please replace the model, when you use the demo.
        
        //以下信息与profile文件严格对应
        Map<String, Object> paramModifyDeviceInfo = new HashMap<String, Object>();
        paramModifyDeviceInfo.put("manufacturerId", manufacturerId);
        paramModifyDeviceInfo.put("manufacturerName", manufacturerName);
        paramModifyDeviceInfo.put("deviceType", deviceType);
        paramModifyDeviceInfo.put("model", model);
        paramModifyDeviceInfo.put("protocolType", "CoAP");
        
        String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);
                
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        
        String bodyModifyDeviceInfo = httpsUtil.doPutJsonForString(urlModifyDeviceInfo, header, jsonRequest);

        System.out.println(bodyModifyDeviceInfo);
	}
	public static void main(String[] args) {
		
		try {
			//设置设备信息,目前已注册了四个设备，下边是对应的deviceId -- IMEM
			//3e1bbe1b-6636-41ea-a370-dc896a2f0408  --  863703030016906
			//f8d281a4-c18c-4022-94a4-2ab887df1f0a  --  863703030016914
			//d995fa0b-7872-4eec-bb75-6ff27b6c85fa  --  863703030016823
			//096ce1f3-b435-4bbd-a028-9137b5a5d0dc  --  863703030014877
			//注册直连设备
			//registerDirectlyConnectedDevice("863703030016932", "863703030016932", "currentuser");
			//删除直连设备50c5c7b3-5b49-4b12-859c-2922000a7145
			//deleteDirectlyConnectedDevice("20a43169-e9ab-40f9-9d9e-d3df56e9f813");
			//modifyDeviceInfo("79e62203-e431-409d-99b7-5ae080357c76");
			modifyApplicationInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
