package com.sinhblack.springboottutorial.todorest.repository;

import org.springframework.data.repository.CrudRepository;

import com.sinhblack.springboottutorial.todorest.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
  
}
