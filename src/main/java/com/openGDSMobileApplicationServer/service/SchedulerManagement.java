package com.openGDSMobileApplicationServer.service;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * Created by intruder on 16. 8. 11.
 */
public interface SchedulerManagement {

    void registerSchedule(String key, String cron);
    void resumeSchedule(String key);
    void stopSchedule(String key);
    void searchSchedule();
    void deleteSchedule(String key);
    void editTimeSchedule();

}
