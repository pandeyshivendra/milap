package com.example.demo.utility;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.googledrive.DataContainer;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.amazonaws.util.IOUtils;
import com.example.demo.cotroller.CommonCtrl;

public class CompareFaces {

    Logger logger = LoggerFactory.getLogger(CompareFaces.class);

    public static void main(String[] args) {
        try {

           // boolean bool = ImageCompair(getBytes("image//src2.jpg"),getBytes("image//clickpic_3.jpg"),"");


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean ImageCompair(DataContainer sourceImageDc, DataContainer targetImageDc, String exceptionStr) throws Exception {
        Float similarityThreshold = 70F;

        AmazonRekognition rekognitionClient = setupRekognitionClient();// AmazonRekognitionClientBuilder.defaultClient();
        System.out.println("testing  --- >>>> " + rekognitionClient);
        try {
        	 List<byte []> finalImage=new ArrayList<>();
            S3Object sourceS3obj = new S3Object().withBucket(sourceImageDc.getBucketName()).withName(sourceImageDc.getFilePath());
            Image source = new Image().withS3Object(sourceS3obj);
            //Image source = new Image().withBytes(sourceImageBytes);

            
            try (InputStream inputStream = new FileInputStream(new File(source.getS3Object().getName()))) {
                byte[] sourceByteArray = IOUtils.toByteArray(inputStream);
                finalImage.add(sourceByteArray);
            }
          
            S3Object targetS3obj = new S3Object().withBucket(targetImageDc.getBucketName()).withName(targetImageDc.getFilePath());
            Image target = new Image().withS3Object(targetS3obj);
            //Image targets = new Image().withBytes(target);
            try (InputStream inputStream = new FileInputStream(new File(source.getS3Object().getName()))) {
                byte[] targetByteArray = IOUtils.toByteArray(inputStream);
                finalImage.add(targetByteArray);
            }
            CompareFacesRequest request = new CompareFacesRequest().withSourceImage(source).withTargetImage(target)
                    .withSimilarityThreshold(similarityThreshold);

            System.out.println("before call operations ---- >>>>>>>>>>>>>>>  ");
            // Call operation
            CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);

            // Display results
            List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            float confidenceLevel = 0;
            for (CompareFacesMatch match : faceDetails) {
                ComparedFace face = match.getFace();
                BoundingBox position = face.getBoundingBox();
                confidenceLevel = match.getSimilarity();
                System.out.println("Face at " + position.getLeft().toString() + " " + position.getTop()
                        + " matches with " + match.getSimilarity().toString() + "% confidence.");

            }
            List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

            if (confidenceLevel > 70) {
                StrConst.logger.info("recognized Confidence Level :::::: " + confidenceLevel);
                return true;
            }
            StrConst.logger.info("not recognized Confidence Level :::::: " + confidenceLevel);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            exceptionStr = e.getMessage();
            return false;
        }

    }

    public static List<byte []> ImagesCompair(DataContainer sourceImageDc, DataContainer targetImageDc, String exceptionStr) throws Exception {
        Float similarityThreshold = 70F;

        AmazonRekognition rekognitionClient = setupRekognitionClient();// AmazonRekognitionClientBuilder.defaultClient();
        System.out.println("testing  --- >>>> " + rekognitionClient);
        try {
        	 List<byte []> finalImage=new ArrayList<>();
            S3Object sourceS3obj = new S3Object().withBucket(sourceImageDc.getBucketName()).withName(sourceImageDc.getFilePath());
            Image source = new Image().withS3Object(sourceS3obj);
            //Image source = new Image().withBytes(sourceImageBytes);
                finalImage.add(AwsJave.getImage(sourceImageDc));
                finalImage.add(AwsJave.getImage(targetImageDc));
            S3Object targetS3obj = new S3Object().withBucket(targetImageDc.getBucketName()).withName(targetImageDc.getFilePath());
            Image target = new Image().withS3Object(targetS3obj);
            //Image targets = new Image().withBytes(target);
           
            CompareFacesRequest request = new CompareFacesRequest().withSourceImage(source).withTargetImage(target)
                    .withSimilarityThreshold(similarityThreshold);

            System.out.println("before call operations ---- >>>>>>>>>>>>>>>  ");
            // Call operation
            CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);

            // Display results
            List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            float confidenceLevel = 0;
            for (CompareFacesMatch match : faceDetails) {
                ComparedFace face = match.getFace();
                BoundingBox position = face.getBoundingBox();
                confidenceLevel = match.getSimilarity();
                System.out.println("Face at " + position.getLeft().toString() + " " + position.getTop()
                        + " matches with " + match.getSimilarity().toString() + "% confidence.");

            }
            List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

            if (confidenceLevel > 70) {
                StrConst.logger.info("recognized Confidence Level :::::: " + confidenceLevel);
                return finalImage;
            }
            StrConst.logger.info("not recognized Confidence Level :::::: " + confidenceLevel);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            exceptionStr = e.getMessage();
            return null;
        }

    }
    private static AmazonRekognition setupRekognitionClient() {

        AWSCredentialsProvider provider = getAwsCredentialProvider();

        return AmazonRekognitionClientBuilder.standard().withCredentials(provider).withRegion(Regions.AP_SOUTH_1).build();

    }

    public static AWSCredentialsProvider getAwsCredentialProvider() {
        return new AWSCredentialsProvider() {
            public AWSCredentials getCredentials() {
                return new BasicAWSCredentials(StrConst.awsAccessKey, StrConst.awsSecreatKey);
            }

            public void refresh() {

            }
        };
    }

    public static ByteBuffer getBytes(String image) {
        ByteBuffer imageBytes = null;
        try (InputStream inputStream = new FileInputStream(new File(image))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
            // System.out.println("sourceImgByte -- >>> "+sourceImageBytes);
        } catch (Exception e) {
            System.out.println("Failed to load source image " + image);
            System.exit(1);
        }

        return imageBytes;
    }

}


