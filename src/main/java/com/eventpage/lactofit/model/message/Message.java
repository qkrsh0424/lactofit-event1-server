package com.eventpage.lactofit.model.message;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Message {
    private HttpStatus status;
    private String message;
    private Object data;
    private String memo;

    public Message() {
        this.status = HttpStatus.BAD_REQUEST;
        this.data = null;
        this.message = null;
        this.memo = null;
    }
}
