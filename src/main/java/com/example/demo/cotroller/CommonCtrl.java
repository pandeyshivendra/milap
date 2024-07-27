package com.example.demo.cotroller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.businessLogic.AutowareClass;
import com.example.demo.businessLogic.CommonServiceInterface;
import com.example.demo.entity.DocsFile;
import com.example.demo.entity.Event;
import com.example.demo.entity.FilterCriteria;
import com.example.demo.entity.Otp;
import com.example.demo.entity.RequestPayload;
import com.example.demo.entity.User;
import com.example.demo.entity.WhatsappImageStorage;
import com.example.demo.googledrive.DataContainer;
import com.example.demo.utility.AwsJave;
import com.example.demo.utility.FlagVariables;
import com.example.demo.utility.ResponseObj;
import com.example.demo.utility.StrConst;
import com.example.demo.utility.Utility;
import com.example.demo.utility.WhatsAppMsg;
import com.google.common.io.ByteSource;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CommonCtrl {

    Logger logger = LoggerFactory.getLogger(CommonCtrl.class);
    @Autowired
    private AutowareClass ac;
    @Autowired
    private CommonServiceInterface service;
    @Value("${product.images}")
    private String path;

    // add thread pool

    static boolean isFollowAlternativePath = false;
    static ExecutorService executorcommonpacket_consumerClouddb = null;

    static {

        executorcommonpacket_consumerClouddb = Executors.newFixedThreadPool(10);
    }

    public void shutdown() {
        logger.info("Application Shutdown Started!");
        isFollowAlternativePath = true;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeThreadPools();
        while (!isThreadPoolClosed()) {
            logger.info("Application Shutdown Completed");
        }

    }

    private void closeThreadPools() {
        logger.info("Stopping Thread Pools!");

        try {
            executorcommonpacket_consumerClouddb.shutdown();
            executorcommonpacket_consumerClouddb.awaitTermination(30, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            logger.error("Error while closing thread pools: ", e);
            e.printStackTrace();
        }

    }

    private boolean isThreadPoolClosed() {
        try {
            Object shutdown = new Object();
            synchronized (shutdown) {
                shutdown.wait(10);
            }
        } catch (Exception e) {
            logger.error("Error while waiting for shutdown: ", e);
        }
        if (executorcommonpacket_consumerClouddb.isShutdown()) {
            return true;
        }

        return false;
    }

   
    @PostMapping("upload/foudImage")
    public ResponseEntity<?> foudImage(@RequestParam("image")MultipartFile image ) {
    	String imagePath=service.uploadImage(path, image);
    	return ResponseEntity.status(200).body(imagePath);
    }
    
    @PostMapping("upload/lostImages")
    public ResponseEntity<?> uploadImages(@RequestParam("image")MultipartFile[] image ) {
    	service.uploadImages(path, image);
    	 ResponseObj resp =new ResponseObj();
         resp.setStatusCode("200");
         resp.setStatusMsg("Images uploaded sucessfully");
    	return ResponseEntity.status(200).body(resp);
    }
    
    @GetMapping("/getImages")
    public ResponseEntity<ByteArrayResource> getImages() {
    	byte[] imageMatching = service.getImage("fifth.jpg");
    	ByteArrayResource res=new ByteArrayResource(imageMatching) ;
        
    	return ResponseEntity.ok().header("content-type", "application/octet-stream")
    			.body(res);
    }
    
    @GetMapping("/getImagess")
    public ResponseEntity<List<ByteArrayResource>> getImagess(@RequestParam("imagePath")String imagePath ) {
    	List<byte[]> imageMatching = service.imageMatching(imagePath);
    	List<ByteArrayResource> resource=new ArrayList<>();
    	for (byte[] imag : imageMatching) {
    		if(imag!=null) {
    		resource.add(new ByteArrayResource(imag) );
    		}
		}
    	return ResponseEntity.ok().header("content-type", "application/octet-stream")
    			.body(resource);
    }
}
