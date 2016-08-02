package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.mapper.CollectedMapper;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by intruder on 16. 8. 2.
 */
@Repository
public class DataCollectedManagementDAO {

    @Autowired
    CollectedMapper mapper;

    public Boolean insertDataCollect(CollectVO collect) {
        int result = 0;
        try {
            result = mapper.insertCollected(collect);
        } catch (Exception e) {
           // e.printStackTrace();
        }
        System.out.println(result);
        return (result == 1) ? true : false;
    }
    public List<CollectVO> findAllCollect() throws Exception {
        return mapper.findAllCollected();
    }
}
