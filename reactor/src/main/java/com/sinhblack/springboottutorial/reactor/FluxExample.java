package com.sinhblack.springboottutorial.reactor;

import java.util.List;

import com.sinhblack.springboottutorial.reactor.domain.ToDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Configuration
public class FluxExample {

  static private Logger LOG = LoggerFactory.getLogger(FluxExample.class);

  public static void main(String[] args) {
		SpringApplication.run(FluxExample.class, args);
	}

  @Bean
  public CommandLineRunner runFluxExample() {
    return args -> {
      EmitterProcessor<ToDo> stream = EmitterProcessor.create();

      Mono<List<ToDo>> promise = stream
        .filter(s -> s.isCompleted())
        .doOnNext(s -> LOG.info("FLUX >>> ToDo: {}", s.getDescription()))
        .collectList()
        .subscribeOn(Schedulers.single());

      stream.onNext(new ToDo("11111", true));
      stream.onNext(new ToDo("222222", true));
      stream.onNext(new ToDo("33333"));
      stream.onNext(new ToDo("4444444", true));
      stream.onNext(new ToDo("5555555", true));

      stream.onComplete();

      promise.block();
    };
  }
}
