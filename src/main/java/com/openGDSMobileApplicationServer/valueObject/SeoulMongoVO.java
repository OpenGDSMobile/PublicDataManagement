package com.openGDSMobileApplicationServer.valueObject;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by intruder on 16. 8. 13.
 */
public class SeoulMongoVO {
    @Id
    private String name;

    @Indexed
    private String time;

    private JSONObject obj;

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public JSONObject getObj() {
        return obj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "SeoulMongoVO{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", obj=" + obj +
                '}';
    }
}
