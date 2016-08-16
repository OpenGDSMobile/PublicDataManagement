package com.openGDSMobileApplicationServer.service.impl;

import org.json.JSONObject;
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


    public Object queryWhereCollection(String name, String queryType, String field, String value, String sFields){
        String[] result = null;
        String[] queryTypes = queryType.split(",");
        String[] searchFields = field.split(",");
        String[] values = value.split(",");

        JSONObject query = new JSONObject();


/*
        for (String)*/

/*
        for (String v : queryTypes){

        }*/


        if (sFields != null){
            result = sFields.split(",");
        }
/*
        if (queryTypes.length == 1){
            return dao.findWhereQuery(name, queryType, field, value, result);
        } else {
            return dao.findWhereQuery(name, queryType, field, value, result);
        }
*/
    return null;
    }

    public Object queryWhereMulti(String name, String q, String fields){
        String resultQuery = null;


        return dao.findWhereMultiQuery(name, resultQuery);
    }
    public Object findValuesCollection(String name, String key){
        return dao.findValueSearchQuery(name, key);

    }

/*
    public Object findWhereIsCollection(String name, String whereField, String isQuery, String queryType){
        return dao.findWhereIsQuery(name, whereField, isQuery, queryType);
    }
*/

}
