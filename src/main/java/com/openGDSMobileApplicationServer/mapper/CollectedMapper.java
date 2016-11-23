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

    /**
     * insertCollected
     * @param collect
     * @return
     */
    @Insert("INSERT INTO \"OPENDATACOLLECT\"(\"NAME\", \"PROVIDER\", \"URL\", \"EP\", \"TIME\", \"STATUS\", \"COMMENT\", \"KEYS\", \"TYPE\") " +
            "VALUES(#{name}, #{provider}, #{url}, #{ep}, #{time}, #{status}, #{comment}, #{keys}, #{type})")
    int insertCollected(CollectVO collect);

    /**
     * findAllCollected
     * @return
     */
    @Select("SELECT * FROM \"OPENDATACOLLECT\"")
    List<CollectVO> findAllCollected();
}
