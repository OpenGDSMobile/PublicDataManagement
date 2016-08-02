package com.openGDSMobileApplicationServer.service;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;

import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
public interface DataCollectedManagement {
    void insertCollected(CollectVO collect);
    List<CollectVO> allListCollected();
    CollectVO selectCollected(String name);
    void runStopCollected(String name);
    void deleteCollected(String name);
    void editCollected(String name, CollectVO collect);

}
