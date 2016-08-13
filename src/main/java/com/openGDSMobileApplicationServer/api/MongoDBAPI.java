package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.impl.MongoDBManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by intruder on 16. 8. 13.
 */
@RestController
@RequestMapping("/api/MongoDB")
public class MongoDBAPI {

    @Autowired
    MongoDBManagementService service;

    @RequestMapping (value="/{name}", method = {RequestMethod.GET})
    public void findAllCollection(@PathVariable String name){
        service.findAllCollection(name);
    }


    @RequestMapping (value="/{name}/{fieldName}", method = {RequestMethod.GET})
    public void findAllCollection(@PathVariable String name, @PathVariable String fieldName){
        service.findFieldCollection(name, fieldName);
    }

}
