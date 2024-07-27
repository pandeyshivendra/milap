package com.example.demo.businessLogic;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CommonServiceInterface {
    
    public String uploadImage(String path,MultipartFile file);

    public String uploadImages(String path, MultipartFile[] file);


	List<byte[]> imageMatching(String name);

	byte[] getImage(String name);


}

