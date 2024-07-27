package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ShareEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long eventId;
    private Long userIdSharedBy;
    private Long userIdSharedTo;
    private Date createdDt=new Date();

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

    public Long getUserIdSharedBy() {
        return userIdSharedBy;
    }

    public void setUserIdSharedBy(Long userIdSharedBy) {
        this.userIdSharedBy = userIdSharedBy;
    }

    public Long getUserIdSharedTo() {
        return userIdSharedTo;
    }

    public void setUserIdSharedTo(Long userIdSharedTo) {
        this.userIdSharedTo = userIdSharedTo;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }
}
