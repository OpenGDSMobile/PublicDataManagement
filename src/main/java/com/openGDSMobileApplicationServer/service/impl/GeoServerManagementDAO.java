package com.openGDSMobileApplicationServer.service.impl;


import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
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

    static String RESTURL = "http://localhost:8080/geoserver";
    static String RESTUSER = "admin";
    static String RESTRW = "geoserver";

    GeoServerRESTReader reader;
    GeoServerRESTPublisher publisher;
    GeoServerRESTManager manager;

    /*GeoServerRESTStoreManager storeManager;
    GeoServerRESTStyleManager styleManager;*/

    /**
     * GeoServer Management DAO
     * @throws MalformedURLException
     */
    public GeoServerManagementDAO() throws MalformedURLException {
        reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTRW);
        publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTRW);
        manager = new GeoServerRESTManager(new URL(RESTURL), RESTUSER, RESTRW);
        /*storeManager = new GeoServerREST*/
    }

    /**
     * createWorkspace
     * @param name : workspace name
     * @return
     */
    public Boolean createWorkspace(String name) {
        return publisher.createWorkspace(name, URI.create("http://" + name + ".org"));
    }

    /**
     * createStyle
     * @param file : style file
     * @return
     */
    public Boolean createStyle(File file){
        return publisher.publishStyle(file);
    }

    /**
     * publishShpBasedFile
     * @param zip : compress shpfile
     * @param workspace : import workspace
     * @param storeName : import store name
     * @param epsg : file EPSG number
     * @return Boolean
     */
    public Boolean publishShpBasedFile(File zip,String workspace, String storeName, String epsg){
        try {
            return publisher.publishShp(workspace, storeName, zip.getName(), zip, epsg);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * getWSLayers
     * @param workspace : workspace name
     * @return Object : layer list
     */
    public Object getWSLayers(String workspace){
        RESTFeatureTypeList list = reader.getFeatureTypes(workspace);
        return list;
    }

    //http://suite.opengeo.org/docs/latest/dataadmin/pgGettingStarted/shp2pgsql.html
    //https://github.com/geosolutions-it/geoserver-manager/wiki
    //http://docs.geoserver.org/2.8.x/en/user/rest/api/index.html
}
