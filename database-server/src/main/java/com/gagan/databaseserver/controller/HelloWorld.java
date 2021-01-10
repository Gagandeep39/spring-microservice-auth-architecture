package com.gagan.databaseserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

  @Value("${server.port}")
  private String serverPort;
  @Value("${spring.application.name}")
  private String applicationName;

  @GetMapping
  public String helloWorld() {
    return "Hello from " + applicationName + " running at " + serverPort;
  }

}