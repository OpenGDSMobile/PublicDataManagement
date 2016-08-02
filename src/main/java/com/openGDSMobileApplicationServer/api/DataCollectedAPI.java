package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by intruder on 16. 8. 1.
 */
@RestController
@RequestMapping("/api/Collected")
public class DataCollectedAPI {
    /**
     * 404 Not Found : 사용자 정보가 없을 경우
     * 400 Bad Request : 요청 정보가 정확하지 않을 경우
     * 401 UNAUTHORIZED : 인증 실패
     */
    @RequestMapping (method={RequestMethod.POST})
    void registerDataAPI(@RequestBody CollectVO collect) {
        System.out.println(collect.toString());
    }

    @RequestMapping (value="/{name}", method={RequestMethod.PUT})  //Management (Start/Stop/Edit)
    void managementDataAPI() {

    }

    @RequestMapping (method = {RequestMethod.GET})
    void queryData() {

    }

    @RequestMapping (value="/{name}", method={RequestMethod.DELETE})
    void deleteData() {

    }

}
