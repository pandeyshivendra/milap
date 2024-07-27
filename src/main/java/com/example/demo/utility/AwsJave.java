package com.example.demo.utility;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.demo.entity.WhatsappImageStorage;
import com.example.demo.googledrive.DataContainer;
import com.example.demo.googledrive.SearchFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

public class AwsJave {
    private static final Logger logger = LoggerFactory.getLogger(AwsJave.class);

    public static AmazonS3 amazonS3ClientBuilder() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(StrConst.awsAccessKey, StrConst.awsSecreatKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .enableForceGlobalBucketAccess()
                .build();
    }

    public static Bucket getBucket(String bucket_name) {
        //this.amazonS3Client =

        final AmazonS3 s3 = amazonS3ClientBuilder();//AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        System.out.println("No.of bucket ---->> " + buckets.size());
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public static Bucket createBucket(String bucket_name) {
        final AmazonS3 s3 = amazonS3ClientBuilder();//AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        Bucket b = null;
        if (s3.doesBucketExistV2(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
            b = getBucket(bucket_name);
        } else {
            try {
                b = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }

    public static void getObj(String bucketName) {
        final AmazonS3 s3 = amazonS3ClientBuilder();
        ObjectListing listObjectsV2Request = s3.listObjects(bucketName);
        //ListObjectsV2Response listObjectsV2Response = s3.listObjectsV2(listObjectsV2Request);

        //List<S3Object> contents = listObjectsV2Response.contents();

        System.out.println("Number of objects in the bucket: " + listObjectsV2Request.getBucketName());
        //contents.stream().forEach(System.out::println);
    }


    public static void getFIleListFromBucket() throws IOException {
        String bucket_name = StrConst.bucket2;
        String key = "Your file.png";

        final AmazonS3 s3 = amazonS3ClientBuilder();
        ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os : objects) {
            System.out.println("* " + os.getKey());
        }

        byte[] buffer = new byte[4096];
        int bytesRead = -1;


    }


    public static void getImageS3Object(DataContainer dc) {
        String bucketName = dc.getBucketName();
        String keyName = dc.getFilePath();// The name of the object in S3
        // String destinationPath = objectPath; // Path to save the downloaded file

        try {
            // Create an S3 client
            AmazonS3 clints3 = amazonS3ClientBuilder();

            // Create a GetObjectRequest with the necessary parameters
            GetObjectRequest request = new GetObjectRequest(bucketName, keyName);

            // Download the object from S3
            S3Object object = clints3.getObject(request);
            S3ObjectInputStream objectInputStream = object.getObjectContent();
            byte[] byt = objectInputStream.readAllBytes();
            dc.setByteArr(byt);
            System.out.println("BYte Arr -->>> " + byt.length);

            objectInputStream.close();
            return;
        } catch (AmazonServiceException e) {
            // Handle AWS service errors
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Handle errors that occur within the client library
            e.printStackTrace();
        } catch (IOException e) {
            // Handle I/O errors
            e.printStackTrace();
        }
    }


    public static  byte[] getImage(DataContainer dc) {
        String bucketName = dc.getBucketName();
        String keyName = dc.getFilePath();// The name of the object in S3
        // String destinationPath = objectPath; // Path to save the downloaded file

        try {
            // Create an S3 client
            AmazonS3 clints3 = amazonS3ClientBuilder();

            // Create a GetObjectRequest with the necessary parameters
            GetObjectRequest request = new GetObjectRequest(bucketName, keyName);

            // Download the object from S3
            S3Object object = clints3.getObject(request);
            S3ObjectInputStream objectInputStream = object.getObjectContent();
            byte[] byteArray = IOUtils.toByteArray(objectInputStream);

            return byteArray;
        } catch (AmazonServiceException e) {
            // Handle AWS service errors
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Handle errors that occur within the client library
            e.printStackTrace();
        } catch (IOException e) {
            // Handle I/O errors
            e.printStackTrace();
        }
		return null;
    }


    public static void putS3Object(DataContainer dc) {
        logger.info("\n\nputS3Object ::::: " + dc);
        new Thread(new Runnable() {

            public void run() {
                try {
                    logger.info("\n\nInside THREAD putS3Object  ::::: ");
                    AmazonS3 clints3 = amazonS3ClientBuilder();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentType("image/jpeg");
                    PutObjectRequest request = new PutObjectRequest(dc.getBucketName(), dc.getFileName(), new File(dc.getFilePath()))
                            .withMetadata(metadata);

                    clints3.putObject(request);
                    logger.info("\n\nSuccessfully placed  into bucket :::::: ");

                    Utility.deleteFile(dc.getFilePath());

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            }
        }).start();


    }


    public static S3Object getS3Object(String bucketNm, String filepath) {


        AmazonS3 clints3 = amazonS3ClientBuilder();

        // Create a GetObjectRequest with the necessary parameters
        GetObjectRequest request = new GetObjectRequest(bucketNm, filepath);

        // Download the object from S3
        S3Object object = clints3.getObject(request);


        return object;

    }

    public static boolean putBYteArrayTos3Obj(DataContainer dcObj){
        try {
            AmazonS3 clints3 = amazonS3ClientBuilder();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            InputStream is = new ByteArrayInputStream(dcObj.getByteArr());
            clints3.putObject(dcObj.getBucketName(), dcObj.getFilePath()+ dcObj.getFileName(), is, metadata);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
       DataContainer dc = new DataContainer();
       dc.setFileName("testImage_hk.jpg");
       dc.setFileId("1zv3xN52q9xCAF3uZLpmQXmfgP-x0BJeO");
        try {
            SearchFile.searchFile(StrConst.gDriveGetfile, dc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

       System.out.println(putBYteArrayTos3Obj(dc));
    }
}
