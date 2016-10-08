package com.openGDSMobileApplicationServer.config;

import com.openGDSMobileApplicationServer.api.SchedulerAPI;
import com.openGDSMobileApplicationServer.service.impl.DataCollectedManagementDAO;
import com.openGDSMobileApplicationServer.service.impl.SeoulOpenDataCollectedService;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import static org.quartz.SimpleScheduleBuilder.*;

import java.util.List;

/**
 * Created by intruder on 16. 8. 11.
 */
@Component
public class AutoSetup implements ApplicationListener<ContextRefreshedEvent>{
    Logger log = LoggerFactory.getLogger(AutoSetup.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Autowired
    private DataCollectedManagementDAO dao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<CollectVO> items = dao.findAllCollect();
        for (int i=0; i<items.size(); i++){
            String key = items.get(i).getName();
            int time = items.get(i).getTime();

            JobKey jobKey = new JobKey(key);
            TriggerKey triggerKey = new TriggerKey(key);

            JobDetail job = JobBuilder.newJob(SeoulOpenDataCollectedService.class)
                    .withIdentity(jobKey).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(simpleSchedule().withIntervalInMinutes(time).repeatForever()).build();
                    /*.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();*/

            try {
                schedulerFactory.getScheduler().scheduleJob(job, trigger);
                if (items.get(i).isStatus() == false) {
                    schedulerFactory.getScheduler().pauseJob(jobKey);
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }


/*
   */
}
