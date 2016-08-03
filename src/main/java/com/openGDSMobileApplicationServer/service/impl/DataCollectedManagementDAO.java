package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.mapper.CollectedMapper;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by intruder on 16. 8. 2.
 */
@Repository
public class DataCollectedManagementDAO {


    @Autowired
    CollectedMapper mapper;


    @Autowired
    @Qualifier("MyBatis_PostgreSQL_SqlSessionTemplate")
    SqlSessionTemplate sqlSessionTemplate;


    public Boolean insertDataCollect(CollectVO collect) {

        int result = sqlSessionTemplate.insert("OpenGDSMobileTable.Collected.InsertData", collect);
        try {
            //result = mapper.insertCollected(collect);
            //result = sqlSessionTemplate.insert("collected.insert", collect);

        } catch (Exception e) {
           // e.printStackTrace();
        }
        System.out.println(result);
        return (result == 1) ? true : false;
    }
    public List<CollectVO> findAllCollect() throws Exception {
      //  return mapper.findAllCollected();
        return null;
    }
}
