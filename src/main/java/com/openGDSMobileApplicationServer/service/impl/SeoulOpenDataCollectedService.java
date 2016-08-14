package com.openGDSMobileApplicationServer.service.impl;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.openGDSMobileApplicationServer.service.PublicDataCollected;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by intruder on 16. 8. 1.
 */
@Service
public class SeoulOpenDataCollectedService extends QuartzJobBean implements PublicDataCollected {

    Logger log = LoggerFactory.getLogger(SeoulOpenDataCollectedService.class);

    @Autowired
    DataCollectedManagementDAO dao;

    @Autowired
    OpenDataCollectedDAO seoulDao;

    @Autowired
    MongoDBManagementDAO mongoDao;

    private String serviceURL = null;
    @Override
    public String requestData(String name) {
        log.info("requestData & save :" + name);

        CollectVO serviceInfo = dao.findOneCollect(name);
        serviceURL = serviceInfo.getEp() + serviceInfo.getKeys();
        if (serviceInfo.getName().equals("TimeAverageAirQuality")) {
            String curTime = this.getyyyyMMddHH00();
            serviceURL = serviceURL + curTime;
        } else if (serviceInfo.getName().equals("WPOSInformation")) {
            String prevTime = this.getPrevyyyyMMdd();
            serviceURL = serviceURL + prevTime;
        }

        try {
            JSONObject resultObj = seoulDao.getOpenDataJSON(serviceURL, "UTF-8");
            resultObj = resultObj.getJSONObject(serviceInfo.getName());
            resultObj.put("saveTime", this.getyyyyMMddHHMM());

            //log.info(resultObj.toString());

            mongoDao.createCollection(serviceInfo.getName(), "saveTime");
            DBObject dbObject = (DBObject) JSON.parse(resultObj.toString());

            mongoDao.insertData(serviceInfo.getName(), dbObject);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public String getPrevyyyyMMdd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }

    public String getyyyyMMddHHMM() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        return dateFormat.format(calendar.getTime());
    }
    public String getyyyyMMddHH00() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String resultTime = dateFormat.format(calendar.getTime());
        int date = Integer.parseInt(resultTime.substring(0, resultTime.length()-4));
        int hour = Integer.parseInt(resultTime.substring(resultTime.length()-4, resultTime.length()-2));
        int minute = Integer.parseInt(resultTime.substring(resultTime.length()-2, resultTime.length()));
        if (minute < 40) {
            if (hour == 0) {
                hour = 23;
                calendar.add(Calendar.DATE, -1);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
                date = Integer.parseInt(dateFormatter.format(calendar.getTime()));
            } else {
                hour--;
            }

        }
        String stringHour = (hour < 10) ? "0" + Integer.toString(hour) : Integer.toString(hour);
        return Integer.toString(date) + stringHour + "00";
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String dataName = jobExecutionContext.getJobDetail().getKey().getName();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String resultTime = dateFormat.format(calendar.getTime());
        log.info(resultTime);
        this.requestData(dataName);
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
