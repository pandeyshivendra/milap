package com.example.demo;

import com.example.demo.businessLogic.AutowareClass;
import com.example.demo.businessLogic.DriveDbThread;
import com.example.demo.entity.DriveImageList;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.entity.WhatsappImageStorage;
import com.example.demo.googledrive.DataContainer;
import com.example.demo.utility.CompareFaces;
import com.example.demo.utility.FlagVariables;
import com.example.demo.utility.WhatsAppMsg;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Test {
    Logger logger = LoggerFactory.getLogger(com.example.demo.utility.SchedulerTask.class);

    @Autowired
    private AutowareClass ac;

    @Scheduled(fixedRate = 1500000L, initialDelay = 5000L)
    public void startEvent() {
        if (FlagVariables.getFlagValue().intValue() == 0) {
            this.logger.info("Scheduler stopped now :::::::");
            return;
        }
        List<Event> eventList = this.ac.getEventRepo().findAll();
        for (Event event : eventList) {
            if (event.getDriveFileId() != null && event.getEventDate().after(new Date())) {
                DataContainer dc = new DataContainer();
                dc.setFileId(event.getDriveFileId());
                dc.setAc(this.ac);
                dc.setEvent(event);
                DriveDbThread dd = new DriveDbThread();
                dd.setCd(dc);
                Thread t = new Thread((Runnable)dd);
                t.setName(event.getDriveFileId());
                t.start();
                compairImages(event);
                continue;
            }
            this.logger.error("THE EVENT -- " + event.getName() + " NOT ELIGIBLE ");
        }
        this.logger.info("Scheduler completed session ::: ");
        sendWhatsAppMsg(null);
    }

    private void compairImages(Event e) {
        List<User> users = this.ac.getUserRepo().getlListByevent(e.getId());
        List<DriveImageList> driveImageLists = this.ac.getDriveRepo().findByEventId(e.getId());
        try {
            if (driveImageLists != null && driveImageLists.size() > 0 && users != null && users.size() > 0) {
                for (DriveImageList driveImageObj : driveImageLists) {
                    DataContainer imgDc = new DataContainer();
                    imgDc.setFileId(driveImageObj.getDriveFileId());
                    imgDc.setFilePath("event_image/" + driveImageObj.getImageUrlS3());
                    if (driveImageObj.getTrnfToS3().intValue() == 1) {
                        for (User u : users) {
                            Boolean checkMapping = this.ac.getEm().checkDriveImage_usermap(u.getUserId(), driveImageObj.getId());
                            if (!checkMapping.booleanValue()) {
                                String image = u.getMobile() + "_user.jpg";
                                DataContainer dc = new DataContainer();
                                dc.setFilePath("user-pic/" + image);
                                this.logger.info("START IMAGE COMPARISION  :::: ");
                                String exceptionStr = "";
                                Boolean result = Boolean.valueOf(CompareFaces.ImageCompair(dc, imgDc, exceptionStr));
                                this.ac.getEm().mapDriveImageUser(u.getUserId(), driveImageObj.getId(), e.getId().longValue(), result.booleanValue() ? 1 : 0, exceptionStr);
                                if (result.booleanValue()) {
                                    this.logger.info("Image TRUE Sending to whatsAPP  ::::: User " + u.getMobile());
                                    DataContainer container = new DataContainer();
                                    container.setBucketName("clickpic-whatsapp-image");
                                    container.setFilePath("event_image/" + driveImageObj.getImageUrlS3());
                                    container.setMobileNo(u.getMobile());
                                    container.setAc(this.ac);
                                    saveimageInThread(container);
                                    continue;
                                }
                                this.logger.info("Image Recognizatio FALSE :::: ");
                                continue;
                            }
                            this.logger.info("Image Already Processed FOR  UserId -:" + u.getUserId() + " DriveImageId - :" + driveImageObj.getId());
                        }
                        continue;
                    }
                    this.logger.info("Image Not TRANSFER TO S3 " + driveImageObj.getImageUrl());
                }
            } else {
                this.logger.info("Not executed FOR event -- >>" + e.getName());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private static void saveimageInThread(DataContainer dc) {
        try {
            if (dc.getMobileNo() != null && dc.getMobileNo().length() == 10) {
                WhatsappImageStorage wis = new WhatsappImageStorage();
                wis.setIsSent(Integer.valueOf(0));
                wis.setUserMobile(dc.getMobileNo());
                wis.setS3Url("https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/" + dc.getFilePath());
                dc.getAc().getStoreImage().save(wis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWhatsAppMsg(DataContainer dcObj) {
        try {
            List<WhatsappImageStorage> dataList = this.ac.getStoreImage().findByIsSent(Integer.valueOf(0));
            DataContainer dc = new DataContainer();
            this.logger.info("WHats App to be sent ::::::::  " + dataList.size());
            for (WhatsappImageStorage obj : dataList) {
                if (obj.getIsSent().intValue() == 0) {
                    dc.setMobileNo(obj.getUserMobile());
                    dc.setMessage(obj.getS3Url());
                    WhatsAppMsg.messageSent(dc, "Image");
                    WhatsappImageStorage objNew = new WhatsappImageStorage();
                    objNew.setId(obj.getId());
                    objNew.setS3Url(obj.getS3Url());
                    objNew.setIsSent(Integer.valueOf(1));
                    objNew.setUserMobile(obj.getUserMobile());
                    this.ac.getStoreImage().save(objNew);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
