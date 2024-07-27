package com.example.demo.businessLogic;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.utility.AwsJave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.cotroller.CommonCtrl;
import com.example.demo.entity.DriveImageList;
import com.example.demo.googledrive.DataContainer;
import com.example.demo.googledrive.SearchFile;
import com.example.demo.utility.StrConst;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DriveDbThread implements Runnable {


    private DataContainer cd;
    private static final Logger logger = LoggerFactory.getLogger(DriveDbThread.class);

    @Override
    public void run() {

        try {
            this.updateDriveImageTable();
        } catch (Exception e) {
            e.printStackTrace();

            logger.error("STOPPING ");
        }


    }

    private boolean trnfFromGdriveTos3(DriveImageList dil){
        try {
            DataContainer dcNew = new DataContainer();
            dcNew.setFileId(dil.getDriveFileId());
            dcNew = SearchFile.searchFile(StrConst.gDriveGetfile, dcNew);
            dcNew.setFileName(dil.getImageUrlS3());
            dcNew.setFilePath(StrConst.awsEventName);
           return  AwsJave.putBYteArrayTos3Obj(dcNew);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateDriveImageTable() throws IOException, GeneralSecurityException {
        this.cd = SearchFile.searchFile(StrConst.gDrivefilesearch, this.cd);
        Integer listSize = this.cd.getObjArr().length;
        int count = 0;
        for (Object obj : this.cd.getObjArr()) {
            Map<String, String> dataMap = convertJsonTomap(obj.toString());
            boolean bool = this.cd.getAc().getDriveRepo().existsByDriveFileId(dataMap.get("id"));
            logger.info("is exist ==== " + bool);
            if (!bool) {
                count++;
                DriveImageList dil = new DriveImageList();
                dil.setDriveFileId(dataMap.get("id"));
                dil.setImageUrl(dataMap.get("name"));
                dil.setEventId(this.cd.getEvent().getId());
                dil.setTrnfToS3(1);
                dil.setImageUrlS3(this.cd.getEvent().getId()+"_"+dataMap.get("id")+"_"+dataMap.get("name"));
                    if(trnfFromGdriveTos3(dil)){
                     logger.info("Success Transfer From G-Drive TO S3");
                    }else{
                        logger.error("Failed Transfer From G-Drive TO S3");
                        dil.setTrnfToS3(-1);
                        dil.setImageUrlS3("Failed");
                    }

                this.cd.getAc().getDriveRepo().save(dil);
            }
        }

        logger.info("EVENT Name =" + this.cd.getEvent().getName() + "Total file =" + listSize + " Records update into db =" + count);

        return true;
    }

    public DataContainer getCd() {
        return cd;
    }

    public void setCd(DataContainer cd) {
        this.cd = cd;
    }



    private static Map<String, String> convertJsonTomap(String jsonStr) {
        //String jsonString = "Your JSON string";
        HashMap<String, String> map = new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, String>>() {
        }.getType());

        return map;
    }

}
