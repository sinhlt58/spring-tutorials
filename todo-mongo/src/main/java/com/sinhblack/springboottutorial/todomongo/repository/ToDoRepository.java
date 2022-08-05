package com.sinhblack.springboottutorial.todomongo.repository;

import com.sinhblack.springboottutorial.todomongo.domain.ToDo;

import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
  
}
