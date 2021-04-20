package com.eventpage.lactofit.service.event;

import com.eventpage.lactofit.model.event.dto.EventPostDto;
import com.eventpage.lactofit.model.event.entity.EventPureEntity;
import com.eventpage.lactofit.model.event.repository.EventPureRepository;
import com.eventpage.lactofit.service.handler.DateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    EventPureRepository eventPureRepository;

    @Autowired
    DateService dateService;
    public void insertEventOneService(EventPostDto eventPostDto){

        System.out.println(eventPostDto);
        EventPureEntity entity = convEventEntityByPostDto(eventPostDto);
        eventPureRepository.save(entity);
    }

    private EventPureEntity convEventEntityByPostDto(EventPostDto eventPostDto){
        EventPureEntity entity = new EventPureEntity();
        
        entity.setEventName(eventPostDto.getName());
        entity.setEventPhone(eventPostDto.getPhone());
        entity.setEventImageUrl(eventPostDto.getImageUrl());
        entity.setEventImageName(eventPostDto.getImageName());
        entity.setEventAgreePrivacy(eventPostDto.isAgreePrivacy());
        entity.setEventAgreeConsignment(eventPostDto.isAgreeConsignment());
        entity.setEventAgreeNotice(eventPostDto.isAgreeNotice());
        entity.setEventCreatedAt(dateService.getCurrentDate());
        entity.setEventUpdatedAt(dateService.getCurrentDate());
        return entity;
    }
}
