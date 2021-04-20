package com.eventpage.lactofit.controller.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventpage.lactofit.model.event.dto.EventGetDto;
import com.eventpage.lactofit.model.event.dto.EventPostDto;
import com.eventpage.lactofit.model.message.Message;
import com.eventpage.lactofit.model.xss.XssRequestDto;
import com.eventpage.lactofit.service.event.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    EventService eventService;

    // /api/home
    @GetMapping("/home")
    public String HomeApi() {
        return "{\"message\":\"home page\"}";
    }

    // /api/csrf
    @GetMapping("/csrf")
    public void CsrfApi() {
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
    public ResponseEntity<?> PostEventApi(@RequestBody EventPostDto eventPostDto){
        Message message = new Message();
        try{
            eventService.insertEventOneService(eventPostDto);
        }catch(Exception e){
            log.error("== ERROR PostEventApi => {} ==", "error");
            message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            message.setMessage("failure");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
