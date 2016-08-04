package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
@Service
public class DataCollectedManagementService implements DataCollectedManagement {

    @Autowired
    DataCollectedManagementDAO dao;

    @Override
    public Boolean insertCollected(CollectVO collect){
        return dao.insertDataCollect(collect);
    }

    @Override
    public Boolean editCollected(CollectVO collect) {
        ArrayList<String> collectKeys = new ArrayList<String>(
                Arrays.asList("provider", "url", "ep", "time", "status", "comment", "keys")
        );
        CollectVO searchResult = this.selectOneCollected(collect.getName());
        System.out.println(collect);
        System.out.println(searchResult);
        if (searchResult != null) {

        }
        /*
        if (searchResult != null) {
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getEp().equals("")){
                searchResult.setEp(collect.getEp());
            }
            if (collect.getTime() != collect.getTime()){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }
            if (!collect.getUrl().equals("")){
                searchResult.setUrl(collect.getUrl());
            }

        }*/
        return null;
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

}
