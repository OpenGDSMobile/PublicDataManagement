package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.impl.MongoDBManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping (value="/selectOne/{name}", method = {RequestMethod.GET})
    public Object findFirstCollection(@PathVariable String name){

        return service.findFirstCollection(name);
    }

    /**
     *
     * @param name
     * @param request [query type(is, gt, gte, lt, lte):queryType, search field:field, value:value,
     *                (option) specific fields:sFields]
     * @return
     */
    @RequestMapping (value="/query/{name}", method = {RequestMethod.GET})
    public Object queryCollection(@PathVariable("name") String name, HttpServletRequest request){
        String queryType = request.getParameter("queryType");
        String field = request.getParameter("field");
        String value = request.getParameter("value");
        String sFields = request.getParameter("sFields");
        return service.queryWhereCollection(name, queryType, field, value, sFields);
    }
}
