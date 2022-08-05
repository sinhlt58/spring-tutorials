package com.sinhblack.springboottutorial.reactor;

import java.time.Duration;

import com.sinhblack.springboottutorial.reactor.domain.ToDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Schedulers;

@Configuration
public class ReactorApplication {

  static private Logger LOG = LoggerFactory.getLogger(ReactorApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReactorApplication.class, args);
	}

  @Bean
  public CommandLineRunner runMonoExample(){
    return args -> {
      MonoProcessor<ToDo> promise = MonoProcessor.create();

      Mono<ToDo> result = promise
        .doOnSuccess(p -> LOG.info("Mono >> ToDo: {}", p.getDescription()))
        .doOnTerminate(() -> LOG.info("Mono >> Done"))
        .doOnError(t -> LOG.error(t.getMessage(), t))
        .subscribeOn(Schedulers.single());

        promise.onNext(new ToDo("Buy my ticket for Spring one"));

        result.block(Duration.ofMillis(1000));
      
    };
  }
}
