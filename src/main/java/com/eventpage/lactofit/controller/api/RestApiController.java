package com.eventpage.lactofit.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @GetMapping("/home")
    public String HomeApi(){
        return "{\"message\":\"home page\"}";
    }
}
