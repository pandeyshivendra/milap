package com.example.demo.businessLogic;

import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.entity.EventImage;

@Component
public class AutowareClass {

    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OtpRepo otpRepo;
    @Autowired
    private Environment env;
    @Autowired
    private LookupRepo lookup;
    @Autowired
    private EventImageRepo eventImg;
    @Autowired
    private EntityManagerRepo em;
    @Autowired
    private ScheduledAnnotationBeanPostProcessor scheduler;
    @Autowired
    private EventUserMapRepo eumRepo;
    @Autowired
    private DriveImageListRepo driveRepo;
    @Autowired
    private EmailServiceInterface emailService;
    @Autowired
    private WhatsAppImgRepo storeImage;
    @Autowired
    private WhatsAppTemplateMsgRepo appTemplateMsgRepo;
    @Autowired
    private CalculationAndLogicInterface calculationsLogic;
    @Autowired
    private ShareEventRepo shareEvent;


    public ShareEventRepo getShareEvent() {
        return shareEvent;
    }

    public void setShareEvent(ShareEventRepo shareEvent) {
        this.shareEvent = shareEvent;
    }

    public CalculationAndLogicInterface getCalculationsLogic() {
        return calculationsLogic;
    }

    public void setCalculationsLogic(CalculationAndLogicInterface calculationsLogic) {
        this.calculationsLogic = calculationsLogic;
    }

    public WhatsAppTemplateMsgRepo getAppTemplateMsgRepo() {
        return appTemplateMsgRepo;
    }

    public void setAppTemplateMsgRepo(WhatsAppTemplateMsgRepo appTemplateMsgRepo) {
        this.appTemplateMsgRepo = appTemplateMsgRepo;
    }

    public WhatsAppImgRepo getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(WhatsAppImgRepo storeImage) {
        this.storeImage = storeImage;
    }

    public EmailServiceInterface getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailServiceInterface emailService) {
        this.emailService = emailService;
    }

    public DriveImageListRepo getDriveRepo() {
        return driveRepo;
    }

    public void setDriveRepo(DriveImageListRepo driveRepo) {
        this.driveRepo = driveRepo;
    }

    public EventUserMapRepo getEumRepo() {
        return eumRepo;
    }

    public void setEumRepo(EventUserMapRepo eumRepo) {
        this.eumRepo = eumRepo;
    }

    public ScheduledAnnotationBeanPostProcessor getScheduler() {
        return scheduler;
    }

    public void setScheduler(ScheduledAnnotationBeanPostProcessor scheduler) {
        this.scheduler = scheduler;
    }

    public EntityManagerRepo getEm() {
        return em;
    }

    public void setEm(EntityManagerRepo em) {
        this.em = em;
    }

    public EventImageRepo getEventImg() {
        return eventImg;
    }

    public void setEventImg(EventImageRepo eventImg) {
        this.eventImg = eventImg;
    }

    public void setEventRepo(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void setOtpRepo(OtpRepo otpRepo) {
        this.otpRepo = otpRepo;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public void setLookup(LookupRepo lookup) {
        this.lookup = lookup;
    }

    public LookupRepo getLookup() {
        return lookup;
    }

    public Environment getEnv() {
        return env;
    }

    public EventRepo getEventRepo() {
        return eventRepo;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public OtpRepo getOtpRepo() {
        return otpRepo;
    }

	


}
