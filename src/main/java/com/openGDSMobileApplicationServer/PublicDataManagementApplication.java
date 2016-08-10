package com.openGDSMobileApplicationServer;

import com.openGDSMobileApplicationServer.api.DataCollectedAPI;
import com.openGDSMobileApplicationServer.service.impl.SeoulOpenDataCollectedService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.openGDSMobileApplicationServer"})
@EnableScheduling
public class PublicDataManagementApplication {

	public static void main(String[] args) throws SchedulerException {

		SpringApplication.run(PublicDataManagementApplication.class, args);

		JobDetail job = JobBuilder.newJob(DataCollectedAPI.class)
				.withIdentity("job1", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("cron", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();

		Scheduler sch = new StdSchedulerFactory().getScheduler();
		sch.start();
		sch.scheduleJob(job, trigger);
	}
}
