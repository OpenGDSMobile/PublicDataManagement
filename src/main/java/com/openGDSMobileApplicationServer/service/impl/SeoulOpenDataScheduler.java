package com.openGDSMobileApplicationServer.service.impl;

import com.openGDSMobileApplicationServer.service.SchedulerManagement;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Repository;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created by intruder on 16. 8. 11.
 */
@Repository("SeoulScheduler")
public class SeoulOpenDataScheduler implements SchedulerManagement {

    Logger log = LoggerFactory.getLogger(SeoulOpenDataScheduler.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    @Override
    public void registerSchedule(String key, int time) {

        JobKey jobKey = new JobKey(key);
        TriggerKey triggerKey = new TriggerKey(key);

        JobDetail job = JobBuilder.newJob(SeoulOpenDataCollectedService.class)
                .withIdentity(jobKey).build();

        //0/10 * * * * ?
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(simpleSchedule().withIntervalInMinutes(time).repeatForever()).build();

        try {

            schedulerFactory.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeSchedule(String key){

        try {
            schedulerFactory.getScheduler().resumeJob(JobKey.jobKey(key));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopSchedule(String key){
        try {

            schedulerFactory.getScheduler().pauseJob(JobKey.jobKey(key));
            //log.info(ttt.getKey().getName());
            /*schedulerFactory.getScheduler().unscheduleJob(TriggerKey.triggerKey(key));*/


        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchSchedule() {

    }

    @Override
    public void deleteSchedule(String key){
        try {
            schedulerFactory.getScheduler().deleteJob(JobKey.jobKey(key));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editTimeSchedule(String key, int time) {
        try {
            Trigger oldTrigger = schedulerFactory.getScheduler().getTrigger(TriggerKey.triggerKey(key));
            TriggerBuilder tb = oldTrigger.getTriggerBuilder();

            Trigger newTrigger = tb.withSchedule(simpleSchedule().withIntervalInMinutes(time).repeatForever()).build();
            schedulerFactory.getScheduler().rescheduleJob(oldTrigger.getKey(), newTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
