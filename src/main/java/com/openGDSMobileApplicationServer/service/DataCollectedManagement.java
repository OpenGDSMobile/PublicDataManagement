package com.openGDSMobileApplicationServer.service;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;

import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
public interface DataCollectedManagement {
    /**
     * insertCollected
     * @param collect
     * @return
     */
    Boolean insertCollected(CollectVO collect);

    /**
     * allListCollected
     * @return
     */
    List<CollectVO> allListCollected();

    /**
     * selectOneCollected
     * @param name
     * @return
     */
    CollectVO selectOneCollected(String name);

    /**
     * editCollected
     * @param collect
     * @return
     */
    Boolean editCollected(CollectVO collect);

    /**
     * deleteCollected
     * @param name
     * @return
     */
    Boolean deleteCollected(String name);

}
