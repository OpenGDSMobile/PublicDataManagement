package com.openGDSMobileApplicationServer.service;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;

import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
public interface DataCollectedManagement {
    Boolean insertCollected(CollectVO collect) throws Exception;
    List<CollectVO> allListCollected() throws Exception;
    CollectVO selectOneCollected(String name);
    Boolean editCollected(CollectVO collect);
    Boolean editCollected(String name, Boolean status);
    Boolean deleteCollected(String name);

}
