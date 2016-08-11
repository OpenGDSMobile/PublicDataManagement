package com.openGDSMobileApplicationServer;

import com.openGDSMobileApplicationServer.service.impl.DataCollectedManagementDAO;
import com.openGDSMobileApplicationServer.service.impl.SeoulOpenDataCollectedService;
import com.openGDSMobileApplicationServer.valueObject.CollectVO;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by intruder on 16. 8. 11.
 */
@Component
public class AutoSetup implements ApplicationListener<ContextRefreshedEvent>{


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
            Boolean status = items.get(i).isStatus();
            String cron = "0/10 * * * * ?";

            JobKey jobKey = new JobKey(key);
            TriggerKey triggerKey = new TriggerKey(key);

            JobDetail job = JobBuilder.newJob(SeoulOpenDataCollectedService.class)
                    .withIdentity(jobKey).build();

            //0/10 * * * * ?
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

            try {
                schedulerFactory.getScheduler().scheduleJob(job, trigger);
                if (items.get(i).isStatus() == false) {
                    schedulerFactory.getScheduler().pauseJob(jobKey);
                }

            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }


/*
   */
}
