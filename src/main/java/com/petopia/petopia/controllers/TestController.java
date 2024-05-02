package com.petopia.petopia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@EnableAutoConfiguration
public class TestController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
