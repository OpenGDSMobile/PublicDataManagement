package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
@Service
public class DataCollectedManagementService implements DataCollectedManagement {

    @Autowired
    DataCollectedManagementDAO dao;

    @Override
    public Boolean insertCollected(CollectVO collect) throws Exception {
        System.out.println(collect.toString());
        dao.insertDataCollect(collect);
        return true;
    }

    @Override
    public List<CollectVO> allListCollected() {
        return null;
    }

    @Override
    public CollectVO selectCollected(String name) {
        return null;
    }

    @Override
    public void runStopCollected(String name) {

    }

    @Override
    public void deleteCollected(String name) {

    }

    @Override
    public void editCollected(String name, CollectVO collect) {

    }
}
