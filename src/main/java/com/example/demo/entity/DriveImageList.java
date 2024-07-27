package com.example.demo.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "drive_image_list")
public class DriveImageList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdDt = new Date();
    private String imageUrl;
    private Long eventId;
    private String driveFileId;
    @Column(name="trnf_to_s3")
    private Integer trnfToS3;
    @Column(name="image_url_s3")
    private String imageUrlS3;

    //-------------------------------------//

    public String getImageUrlS3() {
        return imageUrlS3;
    }

    public void setImageUrlS3(String imageUrlS3) {
        this.imageUrlS3 = imageUrlS3;
    }

    public Integer getTrnfToS3() {
        return trnfToS3;
    }

    public void setTrnfToS3(Integer trnfToS3) {
        this.trnfToS3 = trnfToS3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getDriveFileId() {
        return driveFileId;
    }

    public void setDriveFileId(String driveFileId) {
        this.driveFileId = driveFileId;
    }

    @Override
    public String toString() {
        return "DriveImageList{" +
                "id=" + id +
                ", createdDt=" + createdDt +
                ", imageUrl='" + imageUrl + '\'' +
                ", eventId=" + eventId +
                ", driveFileId='" + driveFileId + '\'' +
                ", trnfToS3=" + trnfToS3 +
                '}';
    }


}
