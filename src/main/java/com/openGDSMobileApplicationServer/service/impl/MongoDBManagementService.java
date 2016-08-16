package com.openGDSMobileApplicationServer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intruder on 16. 8. 13.
 */
@Service
public class MongoDBManagementService {
    Logger log = LoggerFactory.getLogger(MongoDBManagementDAO.class);

    @Autowired
    MongoDBManagementDAO dao;

    public List<Object> findAllCollection(String name){
        return dao.findAll(name);

    }

    public List<Object> findFieldCollection(String name, String fieldName){
        return dao.findFieldQuery(name, fieldName);

    }

    public Object findFirstCollection(String name){
        return dao.findFirstQuery(name);
    }


    public Object queryWhereCollection(String name, String queryType, String field, String value, String fields){
        String[] result = null;
        if (fields != null){
            result = fields.split(",");
        }

        return dao.findWhereQuery(name, queryType, field, value, result);
    }

    public Object queryWhereMulti(String name, String q, String fields){
        String resultQuery = null;



        return dao.findWhereMultiQuery(name, resultQuery);
    }

/*
    public Object findWhereIsCollection(String name, String whereField, String isQuery, String queryType){
        return dao.findWhereIsQuery(name, whereField, isQuery, queryType);
    }
*/

}
