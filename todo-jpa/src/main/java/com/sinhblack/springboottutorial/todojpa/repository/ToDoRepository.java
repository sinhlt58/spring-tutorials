package com.sinhblack.springboottutorial.todojpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.sinhblack.springboottutorial.todojpa.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, String> {
  
}
