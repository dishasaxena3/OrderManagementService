package org.clearchannel.ordermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.clearchannel.ordermanagementservice"})
public class OrderManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementServiceApplication.class, args);
	}

}
