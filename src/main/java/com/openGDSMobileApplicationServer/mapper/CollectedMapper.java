package com.openGDSMobileApplicationServer.mapper;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by intruder on 16. 8. 2.
 */
public interface CollectedMapper {
    /**
     *
         String name;
         String provider;
         String url;
         String ep;
         int time;
         boolean status;
         String comment;
         String keys;
     *
     */
    @Insert("INSERT INTO OPENDATACOLLECT(NAME, PROVIDER, URL, EP, TIME, STATUS, COMMENT, KEYS) " +
            "VALUES(#{name}, #{provider}, #{url}, #{ep}, #{int}, #{time}, #{status}, #{comment}, #{keys})")
    public void insertCollected(CollectVO collect) throws Exception;
}
