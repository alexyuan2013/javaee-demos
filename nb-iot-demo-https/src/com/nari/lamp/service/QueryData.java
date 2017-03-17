package com.nari.lamp.service;

import java.util.HashMap;
import java.util.Map;

import com.huawei.utils.HttpsUtil;

/**
 * 查询数据
 * @author alex
 *
 */
public class QueryData {
	/**
	 * 批量查询设备
	 * @param pageNo 查询页，0开始
	 * @param pageSize 每页条数
	 * @return 设备信息json字符串
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String queryDevices(Integer pageNo, Integer pageSize) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication
		String accessToken = Auth.getAccessToken();
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlQueryDevices = Constants.URL + "/iocm/app/dm/v1.1.0/devices";//please replace the IP and Port, when you use the demo.
        Map<String, String> paramQueryDevices = new HashMap<String, String>();
        paramQueryDevices.put("appId", appId);
        paramQueryDevices.put("pageNo", pageNo.toString());
        paramQueryDevices.put("pageSize", pageSize.toString());
 
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        String bodyQueryDevices = httpsUtil.doGetWithParasForString(urlQueryDevices, paramQueryDevices, header);

        System.out.println(bodyQueryDevices);
		return bodyQueryDevices;
	}
	/**
	 * 查询单个设备
	 * @param 设备id
	 * @return 设备信息json字符串
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String queryDevice(String deviceId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication
		String accessToken = Auth.getAccessToken();
		String appId = Constants.APP_ID;    //please replace the appId, when you use the demo.
        String urlQueryDevices = Constants.URL + "/iocm/app/dm/v1.1.0/devices/" + deviceId;//please replace the IP and Port, when you use the demo.
        Map<String, String> paramQueryDevices = new HashMap<String, String>();
        paramQueryDevices.put("appId", appId);

 
        Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        String bodyQueryDevices = httpsUtil.doGetWithParasForString(urlQueryDevices, paramQueryDevices, header);

        System.out.println(bodyQueryDevices);
		return bodyQueryDevices;
	}
	/**
	 * 查询设备激活状态
	 * @param deviceId 设备id 77c26a9d-8e5c-46d2-b815-f701a60ad4d5
	 * @return 设备激活状态json字符串 
	 * </br>示例：{"deviceId":"77c26a9d-8e5c-46d2-b815-f701a60ad4d5","activated":false,"name":null}
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public static String queryDeviceActivationStatus(String deviceId) throws Exception{
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication
		String accessToken = Auth.getAccessToken();
		String appId = Constants.APP_ID;
		String urlDeviceActivationStatus = Constants.URL + "/iocm/app/reg/v1.1.0/devices/"+deviceId;
		Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);

        String bodyDeviceActivationStatus = httpsUtil.doGetWithParasForString(urlDeviceActivationStatus, null, header);
		return bodyDeviceActivationStatus;
	}
	/**
	 * 查询设备能力
	 * @param deviceId 设备id 77c26a9d-8e5c-46d2-b815-f701a60ad4d5
	 * @return 设备能力描述json字符串
	 * </br>示例：{"deviceCapabilities":
	 * [{"deviceId":"77c26a9d-8e5c-46d2-b815-f701a60ad4d5",
	 * "serviceCapabilities":
	 * [{"serviceId":"BasicSet","serviceType":"BasicSet","option":"Mandatory","description":null,
	 * "commands":[{"commandName":"BASIC_SET","paras":
	 * [{"paraName":"mode","dataType":"string","required":false,"min":"0.0","max":"0.0","step":0.0,"maxLength":0,"unit":null,"enumList":["ON","OFF"]}],"responses":null}],"properties":[]}]}]}
	 * @throws Exception 
	 */
	public static String queryDeviceCapabilities(String deviceId) throws Exception{
		@SuppressWarnings("resource")
		HttpsUtil httpsUtil = new HttpsUtil();
		httpsUtil.initSSLConfigForTwoWay(); // Two-Way Authentication
		String accessToken = Auth.getAccessToken();
		String appId = Constants.APP_ID;
		String urlQueryDeviceCapabilities = Constants.URL + "/iocm/app/data/v1.1.0/deviceCapabilities";
		Map<String, String> paramQueryDeviceCapabilities = new HashMap<String, String>();
        paramQueryDeviceCapabilities.put("deviceId", deviceId);
        paramQueryDeviceCapabilities.put("gatewayId", deviceId); //直连设备gatewayId与deviceId相同
		Map<String, String> header = new HashMap<>();
        header.put("app_key", appId);
        header.put("Authorization", "Bearer " + accessToken);
        String bodyQueryDeviceCapabilities = httpsUtil.doGetWithParasForString(urlQueryDeviceCapabilities, paramQueryDeviceCapabilities, header);
		return bodyQueryDeviceCapabilities;
	}
	
	
	
	public static void main(String[] args) {
		try {
			queryDevices(0, 10);
			queryDevice("957bcedb-4991-4dd9-a016-3bfe049471e0");
			System.out.println(queryDeviceActivationStatus("79e62203-e431-409d-99b7-5ae080357c76"));
			System.out.println(queryDeviceCapabilities("79e62203-e431-409d-99b7-5ae080357c76"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
