package com.openGDSMobileApplicationServer.service.impl;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.openGDSMobileApplicationServer.service.PublicDataCollected;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        if ("TimeAverageAirQuality".equals(serviceInfo.getName())) {
            String curTime = this.getyyyyMMddHH00();
            serviceURL = serviceURL + curTime;
        } else if ("WPOSInformation".equals(serviceInfo.getName())) {
            String prevTime = this.getPrevyyyyMMdd();
            serviceURL = serviceURL + prevTime;
        }

        try {
            JSONObject resultObj = seoulDao.getOpenDataJSON(serviceURL, "UTF-8");
            resultObj = resultObj.getJSONObject(serviceInfo.getName());
            resultObj.put("saveTime", this.getyyyyMMddHHMM());

            mongoDao.createCollection(serviceInfo.getName(), "saveTime");
            DBObject dbObject = (DBObject) JSON.parse(resultObj.toString());

            mongoDao.insertData(serviceInfo.getName(), dbObject);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return null;
    }


    /**
     * getPrevyyyyMMdd
     * @return String "YYYYMMDD"
     */
    public String getPrevyyyyMMdd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * getyyyyMMddHHMM
     * @return String yyyyMMddHHmm
     */
    public String getyyyyMMddHHMM() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * getyyyyMMddHH00
     * @return String yyyyMMddHH00
     */
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
}
