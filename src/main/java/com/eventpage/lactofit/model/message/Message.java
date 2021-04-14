package com.eventpage.lactofit.model.message;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;
    private String memo;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
        this.memo = null;
    }
}
