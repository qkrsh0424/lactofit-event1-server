package com.eventpage.lactofit.controller.api;

import java.util.HashMap;
import java.util.Map;

import com.eventpage.lactofit.model.event.dto.EventGetDto;
import com.eventpage.lactofit.model.event.dto.EventPostDto;
import com.eventpage.lactofit.model.xss.XssRequestDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class RestApiController {
    // /api/home
    @GetMapping("/home")
    public String HomeApi() {
        return "{\"message\":\"home page\"}";
    }

    // // /api/xss
    // @PostMapping("/xss")
    // public Map<String, Object> xss(@RequestBody XssRequestDto xssRequestDto) {
    //     log.info("requestDto={}", xssRequestDto);
    //     Map<String, Object> resultMap = new HashMap<>();
    //     resultMap.put("data", xssRequestDto.getContent());
    //     return resultMap;
    // }

    // // /api/xss/form
    // @PostMapping("/xss/form")
    // public @ResponseBody String xssform(XssRequestDto requestDto) {
    //     log.info("requestDto={}", requestDto);
    //     return requestDto.getContent();
    // }

    // @GetMapping(value = "/responseXss")
    // Map<String, Object> responseXss() {
    //     Map<String, Object> resultMap = new HashMap<>();

    //     resultMap.put("htmlTdTag", "<td></td>");
    //     resultMap.put("htmlTableTag", "<table>");

    //     return resultMap;
    // }

    // /api/event/1
    @PostMapping("/event/1")
    public EventPostDto PostEventApi(@RequestBody EventPostDto eventPostDto){
        System.out.println(eventPostDto);
        return eventPostDto;
    }
}
