package com.sinhblack.springboottutorial.todowebflux.repository;

import java.util.Arrays;

import com.sinhblack.springboottutorial.todowebflux.domain.ToDo;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ToDoRepository {
  
  private Flux<ToDo> toDoFlux = Flux.fromIterable(Arrays.asList(
    new ToDo("11111"),
    new ToDo("22222", true),
    new ToDo("33333"),
    new ToDo("44444", true)
  ));

  public Mono<ToDo> findById(String id){
    return Mono.from(
      toDoFlux.filter(todo -> todo.getId().equals(id))
    );
  }

  public Flux<ToDo> findAll(){
    return toDoFlux;
  }
}
