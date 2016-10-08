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
        scheduler.registerSchedule(collect.getName(), time);


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
            for (Map.Entry<String, Object> entry : searchMap.entrySet()){
                String key = entry.getKey();
                if ("time".equals(key)){
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
        newCollect = (CollectVO) this.convertMaptoObject(newDataMap, newCollect);
        newCollect.setStatus(changeStatus);

        if (searchResult.isStatus() != collect.isStatus()) {
            if (collect.isStatus() == true) {
                scheduler.resumeSchedule(collect.getName());
            } else {
                scheduler.stopSchedule(collect.getName());
            }
        }
        if (searchResult.getTime() != collect.getTime()) {
            scheduler.editTimeSchedule(collect.getName(), collect.getTime());
            log.info("Time Change");
        }
        return dao.updateDataCollect(newCollect);
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


    public Map convertObjectToMap(Object obj) {
        try{
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();
            for(int i=0; i<fields.length; i++) {
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object convertMaptoObject(Map map, Object objClass){
        String setMethodString = "set";
        Iterator itr = map.keySet().iterator();
        while(itr.hasNext()){
            String keyAttribute = (String) itr.next();
            String methodString = setMethodString+keyAttribute.substring(0,1).toUpperCase()+keyAttribute.substring(1);
            try {
                Method[] methods = objClass.getClass().getDeclaredMethods();
                for(int i=0;i<=methods.length-1;i++){
                    if(methodString.equals(methods[i].getName())){
                        methods[i].invoke(objClass, map.get(keyAttribute));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return objClass;
    }

}
