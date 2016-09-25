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
public class PublicDataManagementApplication {

	public static void main(String[] args) throws SchedulerException {

		SpringApplication.run(PublicDataManagementApplication.class, args);

	}
}
