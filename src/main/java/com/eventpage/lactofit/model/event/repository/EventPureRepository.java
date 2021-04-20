package com.eventpage.lactofit.model.event.repository;

import com.eventpage.lactofit.model.event.entity.EventPureEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPureRepository extends JpaRepository<EventPureEntity, Long>{
    
}
