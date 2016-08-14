package com.openGDSMobileApplicationServer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by intruder on 16. 8. 13.
 */
@Service
public class MongoDBManagementService {
    Logger log = LoggerFactory.getLogger(MongoDBManagementDAO.class);

    @Autowired
    MongoDBManagementDAO dao;

    public void findAllCollection(String name){
        Object obj = dao.findAll(name);
        log.info(obj.toString());

    }

    public List<Object> findFieldCollection(String name, String fieldName){
        return dao.findFieldQuery(name, fieldName);

    }

    public Object findWhereIsCollection(String name, String whereField, String isQuery){
        return dao.findWhereIsQuery(name, whereField, isQuery);
    }
}
