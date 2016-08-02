package com.openGDSMobileApplicationServer.service.impl;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by intruder on 16. 8. 1.
 */
@Repository("SeoulOpenDataDAO")
public class SeoulOpenDataCollectedDAO {

    URI uri;

    public JSONObject getOpenDataString(String path) throws URISyntaxException, IOException {
        uri = null;
        uri = new URI(path);
        JSONTokener tokener = new JSONTokener((uri.toURL().openStream()));
        JSONObject obj = new JSONObject(tokener);
        return obj;
    }


  /*

	URI url;

	SeoulOpenDataDAO(){
		url = null;
	}

	@Override
	public JSONObject getJSONPublicData(String path) {
		try {
			url = new URI(path);
			JSONTokener tokener = new JSONTokener(url.toURL().openStream());
			JSONObject jo = new JSONObject(tokener);
			return jo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Document getXMLPublicData(String path) {
		// TODO Auto-generated method stub
		return null;
	}

*/
}
