package com.example.demo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class WebAppApplication extends SpringBootServletInitializer  {
	
	private final static Logger log = LoggerFactory.getLogger(WebAppApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder WebAppApplication) {
        return WebAppApplication.sources(WebAppApplication.class);
    }
 
    
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(WebAppApplication.class, args);
		log.info("Active profiles: " + Arrays.toString(ctx.getEnvironment().getActiveProfiles()));

	}
}
