package com.bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Note that a WebApplicationInitializer is only needed if you are building a war file and deploying it.
// If you prefer to run an embedded container then you won't need this at all.
@SpringBootApplication
public class BsApplication  /*uncomment for deploying extends SpringBootServletInitializer*/ {

	//https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html
/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BeeApplication.class);
	}*/

	/*uncomment for deploying
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<BeeApplication> applicationClass = BeeApplication.class;*/




	public static void main(String[] args) {
		SpringApplication.run(BsApplication.class, args);
	}
}
