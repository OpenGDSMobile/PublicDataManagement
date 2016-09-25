package com.openGDSMobileApplicationServer.service.impl;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStoreManager;
import it.geosolutions.geoserver.rest.manager.GeoServerRESTStyleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016-08-18.
 */
@Service
public class GeoServerManagementService {

    @Autowired
    GeoServerManagementDAO dao;

    public Object getDSLayers(String workspace){
        return dao.getWSLayers(workspace);
    }
}
