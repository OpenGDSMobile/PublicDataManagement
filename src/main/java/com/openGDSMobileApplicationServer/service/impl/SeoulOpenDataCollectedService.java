package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.PublicDataCollected;

/**
 * Created by intruder on 16. 8. 1.
 */
public class SeoulOpenDataCollectedService implements PublicDataCollected {

    private String serviceName = "TimeAverageAirQuality";
    private String serviceURL = null;
    private String[] resultJSONKeys = null;
    @Override
    public String requestData() {

        return null;
    }

/*


	private String serviceName = "TimeAverageAirQuality";
	private String serviceURL = null;
	private String[] resultJSONKeys = null;
	Logger log = LogManager.getLogger("org.springframework");

	@Override
	public String requestPublicData(JSONObject data) {
		Iterator<?> it = data.keySet().iterator();
		serviceName = null;
		log.info(data);
		while(it.hasNext()){
			String tmp = (String) it.next();
			if(tmp.equals("serviceName")){
				serviceName = String.valueOf(data.get(tmp));
			}
		}
		if(serviceName.equals("TimeAverageAirQuality") || serviceName.equals("RealtimeRoadsideStation")){
			String baseURL = "http://openapi.seoul.go.kr:8088/";
			String[] urlOrder =
					new String []{"serviceKey", "returnType", "serviceName", "amount", "dateTimeValue"};
			serviceURL=processServiceURL(data, urlOrder, baseURL);
			JSONObject jsonObj = publicDataobj.getJSONPublicData(serviceURL);

			resultJSONKeys = new String[]{"MSRSTE_NM", (String) data.get("envType") };

			return processJSONbySeoulData(jsonObj, resultJSONKeys).toString();
		}
		return "";
	}

	public String processServiceURL(JSONObject data, String[] urlOrder,String baseURL){
		String url = baseURL;
		int order = 0;
		for (int i=0; i<urlOrder.length; i++){
			Iterator<?> it = data.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				if (urlOrder[order].equals(key)){
					url += data.get(key) + "/";
					break;
				}
			}
			order++;
		}
		log.info(url);
		return url;
	}

	public JSONObject processJSONbySeoulData(JSONObject data, String[] keys){
		log.info(data);
		JSONObject source = (JSONObject) data.get(serviceName);
		JSONArray rowList = (JSONArray) source.get("row");
		log.info(rowList);
		JSONObject result = new JSONObject();
		JSONArray list = new JSONArray();
		for(int i=0; i<rowList.length(); i++){
			JSONObject contents = (JSONObject) rowList.get(i);
			JSONObject obj = new JSONObject();
			for(int j=0; j<keys.length; j++){
				obj.put(keys[j], contents.get(keys[j]));
			}
			list.put(obj);
		}
		result.put("row", list);
		log.info(source);
		log.info(result);
		return result;
	}
*/

}
