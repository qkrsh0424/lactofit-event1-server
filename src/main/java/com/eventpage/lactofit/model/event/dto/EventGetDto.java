package com.eventpage.lactofit.model.event.dto;

import lombok.Data;

@Data
public class EventGetDto {
    private String name;
    private String phone;
    private String imageUrl;
    private String imageName;
    private boolean agreePrivacy;
    private boolean agreeConsignment;
    private boolean agreeNotice;
}
