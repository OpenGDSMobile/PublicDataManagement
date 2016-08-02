package com.openGDSMobileApplicationServer.valueObject;

import java.io.Serializable;

/**
 * Created by intruder on 16. 8. 2.
 */
public class CollectVO{
    String name;
    String provider;
    String url;
    String ep;
    int time = 0;
    boolean status = false;
    String comment;
    String keys;

    public CollectVO() {
    }

    public CollectVO(String name) {
        this.name = name;
        this.provider = "";
        this.url = "";
        this.ep = "";
        this.time = 1;
        this.status = true;
        this.comment = "";
        this.keys = "";
    }

    public CollectVO(String name, String provider, String url, String ep, int time,
                     boolean status, String comment, String keys) {
        this.name = name;
        this.provider = provider;
        this.url = url;
        this.ep = ep;
        this.time = time;
        this.status = status;
        this.comment = comment;
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public String getUrl() {
        return url;
    }

    public String getEp() {
        return ep;
    }

    public int getTime() {
        return time;
    }

    public boolean isStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public String getKeys() {
        return keys;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "CollectVO [" +
                "name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", url='" + url + '\'' +
                ", ep='" + ep + '\'' +
                ", time=" + time +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", keys='" + keys + '\'' +
                "]";
    }
}
