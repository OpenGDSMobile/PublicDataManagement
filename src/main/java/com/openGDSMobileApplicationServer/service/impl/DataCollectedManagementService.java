package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import com.openGDSMobileApplicationServer.service.SchedulerManagement;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by intruder on 16. 8. 2.
 */
@Service
public class DataCollectedManagementService implements DataCollectedManagement {


    Logger log = LoggerFactory.getLogger(DataCollectedManagement.class);

    @Autowired
    DataCollectedManagementDAO dao;

    @Autowired
    @Qualifier("SeoulScheduler")
    SchedulerManagement scheduler;

    @Override
    public Boolean insertCollected(CollectVO collect){

        Boolean insertResult =dao.insertDataCollect(collect);
        int time = collect.getTime();
        int hour = time / 60;
        int minute = time % 60;
        log.info("hour: " + hour + " minute: " + minute);


        scheduler.registerSchedule(collect.getName(), "0/10 * * * * ?");

        return insertResult;
    }

    @Override
    public Boolean editCollected(CollectVO collect) {
        CollectVO searchResult = this.selectOneCollected(collect.getName());
        Map<String, Object> searchMap = convertObjectToMap(searchResult);
        Map<String, Object> changeDataMap = convertObjectToMap(collect);
        Map<String, Object> newDataMap = new HashMap<>();
        CollectVO newCollect = new CollectVO();
        Boolean changeStatus = collect.isStatus();
        searchMap.remove("status");
        changeDataMap.remove("status");
        if (searchResult != null) {
            for (String key : searchMap.keySet()) {
                if (key.equals("time")){
                    if (collect.getTime() == 0) {
                        newDataMap.put(key, searchMap.get(key));
                    } else {
                        newDataMap.put(key, changeDataMap.get(key));
                    }
                    continue;
                }
                if (!searchMap.get(key).equals(changeDataMap.get(key)) && !changeDataMap.get(key).equals("") ) {
                    newDataMap.put(key, changeDataMap.get(key));
                } else {
                    newDataMap.put(key, searchMap.get(key));
                }
            }
        }
        newCollect = (CollectVO) this.convertMapToObject(newDataMap, newCollect);
        newCollect.setStatus(changeStatus);

        if (searchResult.isStatus() != collect.isStatus()) {
            if (collect.isStatus() == true) {
                scheduler.resumeSchedule(collect.getName());
            } else {
                scheduler.stopSchedule(collect.getName());
                log.info("test");
            }
        }
        return dao.updateDataCollect(newCollect);
    }



    @Override
    public Boolean editCollected(String name, Boolean status) {
        CollectVO collect = new CollectVO(name, status);
        return dao.updateStatusCollect(collect);
    }

    @Override
    public List<CollectVO> allListCollected(){
        return dao.findAllCollect();
    }

    @Override
    public CollectVO selectOneCollected(String name) {
        return dao.findOneCollect(name);
    }


    @Override
    public Boolean deleteCollected(String name) {
        return dao.deleteCollect(name);

    }


    public static Map convertObjectToMap(Object obj) {
        try{
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for(int i=0; i<fields.length; i++) {
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object convertMapToObject(Map map, Object objClass){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        while(itr.hasNext()){
            keyAttribute = (String) itr.next();
            methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Method[] methods = objClass.getClass().getDeclaredMethods();
                for(int i=0;i<=methods.length-1;i++){
                    if(methodString.equals(methods[i].getName())){
                        methods[i].invoke(objClass, map.get(keyAttribute));
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return objClass;
    }

}
