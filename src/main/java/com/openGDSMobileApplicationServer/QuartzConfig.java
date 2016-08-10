package com.openGDSMobileApplicationServer;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * Created by intruder on 16. 8. 10.
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    List<Trigger> triggers;

    @Bean
    public JobFactory jobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setJobFactory(jobFactory());
        if(triggers != null && !triggers.isEmpty()) {
            scheduler.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
        }
        return scheduler;
    }
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        return schedulerFactoryBean.getObject();
    }
}
