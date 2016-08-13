package com.openGDSMobileApplicationServer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void findFieldCollection(String name, String fieldName){
        Object obj = dao.findFieldQuery(name, fieldName);
        log.info(obj.toString());

    }

}
