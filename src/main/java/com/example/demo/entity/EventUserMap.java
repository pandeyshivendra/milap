package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class EventUserMap {
    //user_id, event_id, whatsapp_status
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long eventId, userId;
    private Integer whatsAppStatus = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getWhatsAppStatus() {
        return whatsAppStatus;
    }

    public void setWhatsAppStatus(Integer whatsAppStatus) {
        this.whatsAppStatus = whatsAppStatus;
    }


}
