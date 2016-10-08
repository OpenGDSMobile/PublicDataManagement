package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.DataCollectedManagement;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by intruder on 16. 8. 1.
 */
@RestController
@RequestMapping("/api/Collected")
public class DataCollectedAPI {
    /**
     * 201 Created : 생성이 이상없이 되었을 때
     * 417 Expectation Failed : 실행이 제대로 되지 않았을 때
     * 404 Not Found : 정보가 없을 경우
     * 400 Bad Request : 요청 정보가 정확하지 않을 경우
     * 401 UNAUTHORIZED : 인증 실패
     */
    @Autowired
    DataCollectedManagement dataCollectService;


    /**
     *
     * @param collect
     * @return
     */
    @RequestMapping (method={RequestMethod.POST})
    public ResponseEntity<String> registerDataAPI(@RequestBody CollectVO collect) {
        Boolean result = dataCollectService.insertCollected(collect);
        if (result == true) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    /**
     *
     * @param collect
     * @return
     */
    @RequestMapping (method={RequestMethod.PUT})  //Management (Edit)
    public ResponseEntity<String> managementDataAPI(@RequestBody CollectVO collect) {
        Boolean result= dataCollectService.editCollected(collect);
        if (result == true) {
            return new ResponseEntity("EDIT OK", HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }

    /**
     *
     * @return
     */
    @RequestMapping (method = {RequestMethod.GET})
    public List<CollectVO> queryAllData() {
        return dataCollectService.allListCollected();
    }

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping (value="/{name}", method = {RequestMethod.GET})
    public CollectVO queryNameData(@PathVariable String name) {
        return dataCollectService.selectOneCollected(name);
    }

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping (value="/{name}", method={RequestMethod.DELETE})
    public ResponseEntity<String> deleteData(@PathVariable String name) {
        Boolean result = dataCollectService.deleteCollected(name);
        if (result == true) {
            return new ResponseEntity("DELETE OK", HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
}
