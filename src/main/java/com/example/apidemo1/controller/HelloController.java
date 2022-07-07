package com.example.apidemo1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("")
    ResponseEntity<String> hello() {
        return  ResponseEntity.ok("Hello World!");
//        return new ResponseEntity<>("hello",HttpStatus.OK);
    }
}
