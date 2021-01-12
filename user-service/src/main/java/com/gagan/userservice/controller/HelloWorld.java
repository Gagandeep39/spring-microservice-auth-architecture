package com.gagan.userservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@CrossOrigin
public class HelloWorld {

  @GetMapping
  public String helloWorld(@RequestHeader String userId) {
    System.out.println(userId);
    return "Hello World";
  }
  
}
