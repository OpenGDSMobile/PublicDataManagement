package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.mapper.CollectedMapper;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


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
        System.out.println(result);
        return (result == 1) ? true : false;
    }
    public List<CollectVO> findAllCollect(){
        List<CollectVO> list = sqlSessionTemplate.selectList("OpenGDSMobileTable.Collected.findAllCollected");
        return list;
    }
    public CollectVO findOneCollect(String name) {
        return sqlSessionTemplate.selectOne("OpenGDSMobileTable.Collected.findOneCollected", name);
    }
    public Boolean updateStatusCollect(CollectVO collect) {
        int result = sqlSessionTemplate.update("OpenGDSMobileTable.Collected.updateStatus", collect);
        return (result == 1) ? true : false;
    }
    public Boolean deleteCollect(String name){
        int result = sqlSessionTemplate.delete("OpenGDSMobileTable.Collected.deleteData", name);
        System.out.println(result);
        return true;
    }

}
