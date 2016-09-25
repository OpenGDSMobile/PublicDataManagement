package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.impl.GeoServerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016-08-18.
 */
@RestController
@RequestMapping("/api/GeoServer")
public class GeoServerManagerAPI {

    @Autowired
    GeoServerManagementService service;

    @RequestMapping(value="/{workspace}", method = {RequestMethod.GET})
    public Object getDSLayersNames(@PathVariable String workspace){

        return service.getDSLayers(workspace);
    }
}
