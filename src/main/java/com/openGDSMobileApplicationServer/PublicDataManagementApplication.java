package com.openGDSMobileApplicationServer;

import com.openGDSMobileApplicationServer.service.impl.SeoulOpenDataCollectedService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.openGDSMobileApplicationServer"})
public class PublicDataManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(PublicDataManagementApplication.class, args);

	}
}
