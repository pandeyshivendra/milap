package com.example.demo.businessLogic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.EventImage;
import com.example.demo.googledrive.DataContainer;
import com.example.demo.utility.AwsJave;
import com.example.demo.utility.CompareFaces;

@Service
public class CommonService implements CommonServiceInterface {

    Logger logger = LoggerFactory.getLogger(CommonService.class);
    @Autowired
    private AutowareClass ac;


    private boolean saveImage(String desc, String imageUrl) {
        boolean bool;
        try {
            EventImage eimg = new EventImage();
            eimg.setCreatedDt(new Date());
            eimg.setImage_url(imageUrl);
            eimg.setDescription(desc);
            ac.getEventImg().save(eimg);
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
            bool = true;
        }

        return bool;
    }

    
    
	@Override
	public String uploadImage(String path, MultipartFile file) {
		String name=file.getOriginalFilename();
		try {
		// File Name
	String filePath=path+File.separator+name;
	File f=new File(path);
	if(!f.exists()) {
		f.mkdir();
	}
	Files.copy(file.getInputStream(), Paths.get(filePath));
	DataContainer dataContainer= new DataContainer();
	dataContainer.setBucketName("ncs-milap-src");
	dataContainer.setFileName(name);
	dataContainer.setFilePath(filePath);
	 AwsJave.putS3Object(dataContainer);
	 saveImage("baseImage", name);
	}catch(Exception e){
		e.printStackTrace();
	}
	return name;
	}
	
	@Override
	public String uploadImages(String path, MultipartFile[] files) {
		for (MultipartFile file : files) {
			String name=file.getOriginalFilename();
			try {
				// File Name
			String filePath=path+File.separator+name;
			File f=new File(path);
			if(!f.exists()) {
				f.mkdir();
			}
			Files.copy(file.getInputStream(), Paths.get(filePath));
			DataContainer dataContainer= new DataContainer();
			dataContainer.setBucketName("ncs-milap-target");
			dataContainer.setFileName(name);
			dataContainer.setFilePath(filePath);
			 AwsJave.putS3Object(dataContainer);
			 saveImage("baseImage", name);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public List<byte []> imageMatching(String name) {
		String exceptionStr="";
		 List<byte []> finalImage=new ArrayList<>();
		try {
	DataContainer sourceobj= new DataContainer();
	sourceobj.setBucketName("ncs-milap-src");
	sourceobj.setFilePath(name);
	 List<EventImage> dataList = ac.getEventImg().findAll();
	 
	 finalImage.add(null);
	 for (EventImage eventImage : dataList) {
		 DataContainer targetObj= new DataContainer();
		 targetObj.setBucketName("ncs-milap-target");
		 targetObj.setFilePath(eventImage.getImage_url());
		List<byte[]> imagesCompair = CompareFaces.ImagesCompair(sourceobj, targetObj, exceptionStr);
		if(imagesCompair!=null) {
			finalImage.addAll(imagesCompair);
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return finalImage;
	}

	
	@Override
	public byte[] getImage(String name) {
		
		try {
	DataContainer sourceobj= new DataContainer();
	sourceobj.setBucketName("ncs-milap-src");
	sourceobj.setFilePath(name);
	return AwsJave.getImage(sourceobj);
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
	}

	
}
