package com.example.demo.utility;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.businessLogic.AutowareClass;
import com.example.demo.businessLogic.DriveDbThread;
import com.example.demo.entity.DriveImageList;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.entity.WhatsappImageStorage;
import com.example.demo.googledrive.DataContainer;

@Component
public class SchedulerTask {

    Logger logger = LoggerFactory.getLogger(SchedulerTask.class);
    @Autowired
    private AutowareClass ac;

    @Scheduled(fixedRate = 600000, initialDelay = 7000)
    public void startEvent() {
        if (FlagVariables.getFlagValue() == 0) {
            logger.info("Scheduler is not enabled now :::::::");
            return;
        }
        logger.info("\n\n\n\nScheduler Started :::::::");
        List<Event> eventList = ac.getEventRepo().findAll();
        for (Event event : eventList) {
            if (event.getDriveFileId() != null && event.getEventDate().after(Utility.minusDaysFromDt(30))) {
                DataContainer dc = new DataContainer();
                dc.setFileId(event.getDriveFileId());
                dc.setAc(ac);
                dc.setEvent(event);
                DriveDbThread dd = new DriveDbThread();
                dd.setCd(dc);
                // dd.run();
                Thread t = new Thread(dd);
                t.setName(event.getId()+"_"+event.getName());
                t.start();
                compairImages(event);
            } else {
                logger.error("THE EVENT -- " + event.getName() + " Is Passed 30 days");
            }

        }


        logger.info("Scheduler completed session ::: ");

        sendWhatsAppMsg(ac);
    }

    private void compairImages(Event e) {
        List<User> users = this.ac.getUserRepo().getlListByevent(e.getId());
        List<DriveImageList> driveImageLists = this.ac.getDriveRepo().findByEventId(e.getId());
        try {
            logger.info("\n\n ImageCompare started ::: ");
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

                                }
                                this.logger.info("Image Recognizatio FALSE :::: ");

                            }
                            this.logger.info("Image Already Processed FOR  UserId -:" + u.getUserId() + " DriveImageId - :" + driveImageObj.getId());
                        }

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
                wis.setIsSent(0);
                wis.setUserMobile(dc.getMobileNo());
                wis.setS3Url(StrConst.bucket2_s3_url + dc.getFilePath());
                dc.getAc().getStoreImage().save(wis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendWhatsAppMsg(AutowareClass ac) {
        try {
            List<WhatsappImageStorage> dataList = ac.getStoreImage().findByIsSent(0);
            DataContainer dc = new DataContainer();
            logger.info("WHats App to be sent ::::::::  " + dataList.size());
            Set<String> uniqueMobileno = new HashSet<>();
            for (WhatsappImageStorage obj : dataList) {
                boolean isTemplateMsgSent = ac.getCalculationsLogic().checkWhatsAppStatus(obj.getUserMobile());
                if (isTemplateMsgSent &&  obj.getIsSent() == 0) {
                    dc.setMobileNo(obj.getUserMobile());
                    dc.setMessage(obj.getS3Url());
                    WhatsAppMsg.messageSent(dc, StrConst.whatsAppImage);

                    WhatsappImageStorage objNew = new WhatsappImageStorage();
                    objNew.setId(obj.getId());
                    objNew.setS3Url(obj.getS3Url());
                    objNew.setIsSent(1);
                    objNew.setUserMobile(obj.getUserMobile());

                    ac.getStoreImage().save(objNew);
                }else{
                    uniqueMobileno.add(obj.getUserMobile().strip());
                }
            }

            for(String ss : uniqueMobileno){
                DataContainer dcTemplatemsg = new DataContainer();
                dcTemplatemsg.setMobileNo(ss);
                dcTemplatemsg.setAc(ac);
                WhatsAppMsg.messageSent(dcTemplatemsg, StrConst.whatsAppTemplate);    //----sending Template msg
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
