package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by intruder on 16. 8. 1.
 */
@RestController
@RequestMapping("/api/Collected")
public class DataCollectedAPI {
    /**
     * 201 Created : 생성이 이상없이 되었을 때
     * 404 Not Found : 정보가 없을 경우
     * 400 Bad Request : 요청 정보가 정확하지 않을 경우
     * 401 UNAUTHORIZED : 인증 실패
     */
    @Autowired
    DataCollectedManagement dataCollectService;


    @RequestMapping (method={RequestMethod.POST})
    ResponseEntity<String> registerDataAPI(@RequestBody CollectVO collect) throws Exception {
        Boolean result = dataCollectService.insertCollected(collect);
        return new ResponseEntity(HttpStatus.CREATED);
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCollectNotFound(){
        System.out.println("TTt");
    }

}
