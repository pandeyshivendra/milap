package com.example.demo.businessLogic;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.WhatsappTemplateStatus;

@Service
public class CalculationAndLogic implements CalculationAndLogicInterface {

    Logger logger = LoggerFactory.getLogger(CalculationAndLogic.class);

    @Autowired
    private AutowareClass ac;

    @Override
    public Boolean checkWhatsAppStatus(String mobile) {
        logger.info("checkWhatsAppStatus ::: " + mobile);
        try {
            List<WhatsappTemplateStatus> data = ac.getAppTemplateMsgRepo().getLatest(mobile);
            if (data != null && data.size() > 0) {
                WhatsappTemplateStatus dataObj = data.get(0);
                Long diffInMin = (((new Date().getTime() - dataObj.getCreatedDt().getTime()) / 60000));
                logger.info("checkWhatsAppStatus ::: Diffrence " + diffInMin);
                return diffInMin <= 1439;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
