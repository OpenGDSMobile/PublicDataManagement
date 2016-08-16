package com.openGDSMobileApplicationServer.service.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by intruder on 16. 8. 13.
 */
@Repository
public class MongoDBManagementDAO {

    Logger log = LoggerFactory.getLogger(MongoDBManagementDAO.class);

    @Autowired
    MongoOperations mongoOperations;

    public Boolean createCollection(String name){
        if (!mongoOperations.getCollectionNames().contains(name)){
            mongoOperations.createCollection(name);
            return true;
        }
        return false;
    }
    public Boolean createCollection(String name, String indexes){
        if (!mongoOperations.getCollectionNames().contains(name)){
            mongoOperations.createCollection(name);
            mongoOperations.getCollection(name).createIndex(indexes);
            return true;
        }
        return false;
    }
    public Boolean dropCollection(String name){
        if (mongoOperations.getCollectionNames().contains(name)){
            mongoOperations.dropCollection(name);
            return true;
        }
        return false;
    }
    public Boolean insertData(String name, Object obj){
        mongoOperations.insert(obj, name);
        return true;
    }

    public Boolean chkCollection(String name){
        if (!mongoOperations.getCollectionNames().contains(name)){
            return false;
        }
        return true;
    }
    public List<Object> findAll(String name) {
        return mongoOperations.findAll(Object.class, name);
    }


        //BasicQuery query = new BasicQuery("{}, {_id:0, " + q + ":1}");
    public List<Object> findFieldQuery(String name, String q){
        Query query = new Query();
        query.fields().include(q);
        return mongoOperations.find(query, Object.class, name);
    }


    public Object findFirstQuery(String name){
        Query query = new Query();
        return mongoOperations.findOne(query, Object.class, name);
    }

    public List<Object> findWhereQuery(String name, String queryType, String field, String q, String[] sFields){
        Query query = new Query();
        if (sFields !=null){
            for (String value : sFields){
                query.fields().include(value);
            }
        }
        query = queryExec(query, queryType, field, q);
        return mongoOperations.find(query, Object.class, name);
    }

    public List<Object> findWhereMultiQuery(String name, String q){
        Query query = new BasicQuery(q);
        return mongoOperations.find(query, Object.class, name);
    }

    public Query queryExec(Query query, String queryType, String field, String q){

        if (queryType.equals("=")){
            query.addCriteria(Criteria.where(field).is(q));
        } else if (queryType.equals(">=")){
            query.addCriteria(Criteria.where(field).gte(q));
        } else if (queryType.equals(">")){
            query.addCriteria(Criteria.where(field).gt(q));
        } else if (queryType.equals("<")){
            query.addCriteria(Criteria.where(field).lt(q));
        } else if (queryType.equals("<=")){
            query.addCriteria(Criteria.where(field).lte(q));
        }
        return query;
    }



/*
    public List<Object> findWhereIsQuery(String name, String whereField, String q, String queryType) {
        Query query = new Query();
        if (queryType.equals("is")){
            query.addCriteria(Criteria.where(whereField).is(q));
        } else if (queryType.equals("gt")){
            query.addCriteria(Criteria.where(whereField).gte(q));
        }
        return mongoOperations.find(query, Object.class, name);
    }
    */

}
