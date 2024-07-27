package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.utility.CustomDateDeserializer;
import com.example.demo.utility.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String token;
    private String venue;
    private String userId;
    private String type;
    private String description;
    private String qrcode;
    @Column(name = "event_profile_pic")
    private String eventProfilePic;
    private String drivePath;
    private String driveFileId;
    private Date eventDate;
    private Date createdDt;
    private Date modifiedDt;

    @Transient
    private String eventDateS;
    @Transient
    private MultipartFile eventProfilePicMultipart;
    @Transient
    private List<String> emailIdsForAccess;
    @Transient
    private List<String> mobileNoForAccess;
    @Transient
    private String organizorName;

    public String getOrganizorName() {
        return organizorName;
    }

    public void setOrganizorName(String organizorName) {
        this.organizorName = organizorName;
    }

    public List<String> getMobileNoForAccess() {
        return mobileNoForAccess;
    }

    public void setMobileNoForAccess(List<String> mobileNoForAccess) {
        this.mobileNoForAccess = mobileNoForAccess;
    }

    public List<String> getEmailIdsForAccess() {
        return emailIdsForAccess;
    }

    public void setEmailIdsForAccess(List<String> emailIdsForAccess) {
        this.emailIdsForAccess = emailIdsForAccess;
    }

    public String getDriveFileId() {
        return driveFileId;
    }

    public void setDriveFileId(String driveFileId) {
        this.driveFileId = driveFileId;
    }


    public String getDrivePath() {
        return drivePath;
    }

    public void setDrivePath(String drivePath) {
        this.drivePath = drivePath;
    }

    public MultipartFile getEventProfilePicMultipart() {
        return eventProfilePicMultipart;
    }

    public void setEventProfilePicMultipart(MultipartFile eventProfilePicMultipart) {
        this.eventProfilePicMultipart = eventProfilePicMultipart;
    }

    public String getEventProfilePic() {
        return eventProfilePic;
    }

    public void setEventProfilePic(String eventProfilePic) {
        this.eventProfilePic = eventProfilePic;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDateS() {
        return eventDateS;
    }

    public void setEventDateS(String eventDateS) {
        this.eventDateS = eventDateS;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }


    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", token=" + token + ", venue=" + venue + ", userId=" + userId
                + ", type=" + type + ", description=" + description + ", qrcode=" + qrcode + ", eventProfilePic="
                + eventProfilePic + ", eventDate=" + eventDate + ", createdDt=" + createdDt + ", modifiedDt="
                + modifiedDt + ", eventDateS=" + eventDateS + ", eventProfilePicMultipart=" + eventProfilePicMultipart
                + "]";
    }

}
