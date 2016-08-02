package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.mapper.CollectedMapper;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Created by intruder on 16. 8. 2.
 */
@Repository
public class DataCollectedManagementDAO {

    @Autowired
    CollectedMapper mapper;

    public Boolean insertDataCollect(CollectVO collect) throws Exception {
        //mapper.insertCollected(collect);
        System.out.println(mapper.findAllCollected().toString());
        return true;
    }

}
