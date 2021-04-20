package com.eventpage.lactofit.model.event.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "event")
public class EventPureEntity {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "event_phone")
    private String eventPhone;
    @Column(name = "event_image_url")
    private String eventImageUrl;
    @Column(name = "event_image_name")
    private String eventImageName;
    @Column(name = "event_agree_privacy")
    private boolean eventAgreePrivacy;
    @Column(name = "event_agree_consignment")
    private boolean eventAgreeConsignment;
    @Column(name = "event_agree_notice")
    private boolean eventAgreeNotice;
    @Column(name = "event_created_at")
    private Date eventCreatedAt;
    @Column(name = "event_updated_at")
    private Date eventUpdatedAt;
    @Column(name = "event_deleted")
    private int eventDeleted;
}
