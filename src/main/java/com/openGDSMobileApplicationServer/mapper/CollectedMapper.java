package com.openGDSMobileApplicationServer.mapper;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by intruder on 16. 8. 2.
 */
@Mapper
public interface CollectedMapper {
    /*
         String name;         String provider;         String url;         String ep;         int time;
         boolean status;         String comment;         String keys;
     */
    @Insert("INSERT INTO \"OPENDATACOLLECT\"(\"NAME\", \"PROVIDER\", \"URL\", \"EP\", \"TIME\", \"STATUS\", \"COMMENT\", \"KEYS\") " +
            "VALUES(#{name}, #{provider}, #{url}, #{ep}, #{time}, #{status}, #{comment}, #{keys})")
    int insertCollected(CollectVO collect);

    @Select("SELECT * FROM \"OPENDATACOLLECT\"")
    List<CollectVO> findAllCollected();
}
