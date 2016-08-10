package com.openGDSMobileApplicationServer.api;

import com.openGDSMobileApplicationServer.service.PublicDataCollected;
import com.openGDSMobileApplicationServer.service.impl.SeoulOpenDataCollectedService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016-08-11.
 */
@RestController
@RequestMapping("/api/Scheduler")
public class SchedulerAPI {

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @RequestMapping(value="/SeoulOpenData", method = RequestMethod.POST)
    public ResponseEntity<String> registerScheduler(){

        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> editScheduler(){
        return null;
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteScheduler(){
        try {
            schedulerFactory.getScheduler().deleteJob(JobKey.jobKey("test"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> searchScheduler(){
        return null;
    }


    @RequestMapping(value="/SeoulRequestTest", method={RequestMethod.GET})
    public ResponseEntity<String> test() {

        JobKey key = new JobKey("test");
        JobDetail job = JobBuilder.newJob(SeoulOpenDataCollectedService.class)
                .withIdentity(key).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("cron", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
     //   schedulerFactory.start();
        try {
            schedulerFactory.getScheduler().scheduleJob(job, trigger);
           // schedulerFactory.getScheduler().
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        //    seoul.requestData("TimeAverageAirQuality");

        return new ResponseEntity("OK", HttpStatus.OK);
    }
}
