package com.eventpage.lactofit.model.xss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class XssRequestDto {
    private String content;

    public XssRequestDto(String content){
        this.content=content;
    }
}
