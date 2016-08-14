package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.impl.MongoDBManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by intruder on 16. 8. 13.
 */
@RestController
@RequestMapping("/api/MongoDB")
public class MongoDBAPI {

    @Autowired
    MongoDBManagementService service;

    @RequestMapping (value="/{name}", method = {RequestMethod.GET})
    public List<Object> findAllCollection(@PathVariable String name){
        return service.findAllCollection(name);
    }


    @RequestMapping (value="/{name}/{fieldName}", method = {RequestMethod.GET})
    public List<Object> findAllFieldCollection(@PathVariable String name, @PathVariable String fieldName){
        return service.findFieldCollection(name, fieldName);
    }

    @RequestMapping (value="/{name}/{fieldName}/{queryType}/{query}", method = {RequestMethod.GET})
    public Object findWhereCollection(@PathVariable String name, @PathVariable String fieldName, @PathVariable String queryType, @PathVariable String query){
        return service.findWhereIsCollection(name, fieldName, query, queryType);
    }

    @RequestMapping (value="/{name}/fieldName}/gt/{gt}", method={RequestMethod.GET})
    public List<Object> findWhereGtCollections(@PathVariable String name, @PathVariable String fieldName, @PathVariable String gt){
        return null;
    }


    @RequestMapping (value="/selectOne/{name}", method = {RequestMethod.GET})
    public Object findFirstCollection(@PathVariable String name){
        return service.findFirstCollection(name);
    }

}
