package com.sinhblack.springboottutorial.todomongo.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import com.sinhblack.springboottutorial.todomongo.domain.ToDo;
import com.sinhblack.springboottutorial.todomongo.domain.ToDoBuilder;
import com.sinhblack.springboottutorial.todomongo.repository.ToDoRepository;
import com.sinhblack.springboottutorial.todomongo.validation.ToDoValidationError;
import com.sinhblack.springboottutorial.todomongo.validation.ToDoValidationErrorBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api")
public class ToDoController {
  
  private ToDoRepository toDoRepository;

  public ToDoController(ToDoRepository repository) {
    this.toDoRepository = repository;
  }

  @GetMapping("/todo")
  public ResponseEntity<Iterable<ToDo>> getToDos(){
    return ResponseEntity.ok(toDoRepository.findAll());
  }

  @GetMapping("/todo/{id}")
  public ResponseEntity<ToDo> getToDoById(@PathVariable String id){
    Optional<ToDo> toDo = toDoRepository.findById(id);
    if (toDo.isPresent()) {
      return ResponseEntity.ok(toDo.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/todo/update/{id}")
  public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
    Optional<ToDo> toDo = toDoRepository.findById(id);

    if(!toDo.isPresent())
      return ResponseEntity.notFound().build();

    ToDo result = toDo.get();
    result.setCompleted(true);
    toDoRepository.save(result);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    .buildAndExpand(result.getId()).toUri();
    
    return ResponseEntity.ok().header("Location",location.toString()).build();
  }

  @RequestMapping(value="/todo", method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
    }

    ToDo result = toDoRepository.save(toDo);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(result.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @RequestMapping("/todo/{id}")
  public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
    toDoRepository.delete(ToDoBuilder.create().withId(id).build());
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/todo")
  public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo){
    toDoRepository.delete(toDo);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ToDoValidationError handleException(Exception exception) {
    return new ToDoValidationError(exception.getMessage());
  }

}
