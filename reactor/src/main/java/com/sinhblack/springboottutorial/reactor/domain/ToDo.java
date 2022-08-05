package com.sinhblack.springboottutorial.reactor.domain;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ToDo {

  private String id;
  private String description;
  private LocalDateTime created;
  private LocalDateTime modified;
  private boolean completed;

  public ToDo(){
  }

  public ToDo(String description){
    this();
    this.description = description;
  }

  public ToDo(String description, boolean completed){
    this();
    this.description = description;
    this.completed = completed;
  }
}
