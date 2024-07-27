package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String name="Guest";
    @Column(unique = true)
    private String mobile;
    private String address;
    @Transient
    private String imageS;
    @Transient
    private String eventId;
    @Transient
    private MultipartFile userSelfie;
    @Transient
    private String qrCode;
    private String imageUrl;
    private Integer status;
    private Integer groupId = 3;
    private Date createdDt = new Date();
    private Date modifiedDt;

    // #####################################################################//


    public String getEventId() {
        return eventId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public MultipartFile getUserSelfie() {
        return userSelfie;
    }

    public void setUserSelfie(MultipartFile userSelfie) {
        this.userSelfie = userSelfie;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


    public String getImageS() {
        return imageS;
    }

    public void setImageS(String imageS) {
        this.imageS = imageS;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "User [userId=" + userId + ", name=" + name + ", mobile=" + mobile + ", address=" + address + ", imageS="
                + imageS + ", eventId=" + eventId + ", userSelfie=" + userSelfie + ", qrCode=" + qrCode + ", imageUrl="
                + imageUrl + ", status=" + status + ", groupId=" + groupId + ", createdDt=" + createdDt
                + ", modifiedDt=" + modifiedDt + "]";
    }

}
