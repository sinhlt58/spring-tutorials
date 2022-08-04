package com.sinhblack.springboottutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootTutorialApplication {

  private static Logger log = LoggerFactory.getLogger(SpringBootTutorialApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTutorialApplication.class, args);
	}

  @Value("${server.ip}")
  String serverIp;

  @Bean
  CommandLineRunner values() {
    return args -> {
      log.info(" > Ther Server IP is: " + serverIp);
    };
  }
}
