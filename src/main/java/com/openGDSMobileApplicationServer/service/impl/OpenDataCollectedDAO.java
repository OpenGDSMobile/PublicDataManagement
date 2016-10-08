package com.openGDSMobileApplicationServer.service.impl;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.HTTPTokener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by intruder on 16. 8. 1.
 */
@Repository("SeoulOpenDataDAO")
public class OpenDataCollectedDAO {


    public JSONObject getOpenDataJSON(String path, String encoding) throws URISyntaxException, IOException {
        URI uri = new URI(path);
        InputStreamReader is = new InputStreamReader(uri.toURL().openStream(), encoding);
        JSONTokener tokener = new JSONTokener(is);
        JSONObject obj = new JSONObject(tokener);
        return obj;
    }

    public Document getOpenDataXML(String path) throws IOException, JDOMException {
        URL url = new URL(path);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(url);
        return doc;

    }
}
