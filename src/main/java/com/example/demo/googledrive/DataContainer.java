package com.example.demo.googledrive;

import java.util.Arrays;

import com.example.demo.businessLogic.AutowareClass;
import com.example.demo.entity.Event;
import com.example.demo.utility.StrConst;

public class DataContainer {
    private String fileName;
    private String fileId;
    private Object[] objArr;
    private byte[] byteArr;
    private AutowareClass ac;
    private Event event;
    private String bucketName = StrConst.bucket2;
    private String filePath;
    private String mobileNo;
    private String message;


    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public AutowareClass getAc() {
        return ac;
    }

    public void setAc(AutowareClass ac) {
        this.ac = ac;
    }

    public byte[] getByteArr() {
        return byteArr;
    }

    public void setByteArr(byte[] byteArr) {
        this.byteArr = byteArr;
    }

    public Object[] getObjArr() {
        return objArr;
    }

    public void setObjArr(Object[] objArr) {
        this.objArr = objArr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


}
