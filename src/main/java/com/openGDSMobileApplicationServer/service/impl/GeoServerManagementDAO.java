package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTFeatureTypeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Administrator on 2016-08-18.
 */
@Repository
public class GeoServerManagementDAO {
    Logger log = LoggerFactory.getLogger(GeoServerManagementDAO.class);

    String RESTURL = "http://localhost:8080/geoserver";
    String RESTUSER = "admin";
    String RESTRW = "geoserver";

    GeoServerRESTReader reader;
    GeoServerRESTPublisher publisher;
    GeoServerRESTManager manager;

    /*GeoServerRESTStoreManager storeManager;
    GeoServerRESTStyleManager styleManager;*/

    public GeoServerManagementDAO() throws MalformedURLException {
        reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTRW);
        publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTRW);
        manager = new GeoServerRESTManager(new URL(RESTURL), RESTUSER, RESTRW);
        /*storeManager = new GeoServerREST*/
    }

    public Boolean createWorkspace(String name) {
        return publisher.createWorkspace(name, URI.create("http://" + name + ".org"));
    }
    public Boolean createStyle(File file){
        return publisher.publishStyle(file);
    }

    public Boolean publishShpBasedFile(File zip,String workspace, String storeName, String epsg){
        try {
            return publisher.publishShp(workspace, storeName, zip.getName(), zip, epsg);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return false;
    }
    public Object getWSLayers(String workspace){
        RESTFeatureTypeList list = reader.getFeatureTypes(workspace);
        return list;
    }

    //http://suite.opengeo.org/docs/latest/dataadmin/pgGettingStarted/shp2pgsql.html
    //https://github.com/geosolutions-it/geoserver-manager/wiki
    //http://docs.geoserver.org/2.8.x/en/user/rest/api/index.html
}
