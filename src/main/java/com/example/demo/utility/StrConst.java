package com.example.demo.utility;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;

public final class StrConst {

    public static final String ec2_img_path = System.getProperty("user.dir") + "//image//";
    public static final Logger logger = LoggerFactory.getLogger(StrConst.class);
    //public static final String bucket = "ncs-clickpic-bucket";
    public static final String awsImgPath = "user-pic/";

    public static final String bucket2 = "clickpic-whatsapp-image";
    public static final String awsImgPath2 = "to-be-sent/";
    public static final String awsEventName = "event_image/";

    public static final String bucket2_s3_url = "https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/";

    public static final String awsAccessKey = "AKIAWF22NAZKYZLRT5FL";
    public static final String awsSecreatKey = "clNXgi3qA9WEzowYSYKSicy3vXhTshiDFTLDjjmU";


    //####################google Drive ###########################//

    public static final String tcdEmail = "deepak.dash@tcdtech.org";
    public static final String APPLICATION_NAME = "clickpic";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    public static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    public static final String CREDENTIALS_FILE_PATH = "clickpic-399304-f575789ef36c.json";
    public static final String gDrivefilesearch = "fileSearch";
    public static final String gDriveGetfile = "getFile";
    public static final String gDriveDelfile = "deleleFile";
    public static final String gDriveEventFolderId = "1we1nLSQYjjHbtSF5FOMXbVMu2VvEC8Uu";
    // Add more constants as needed

    //################whats app ##################################//
    public static final String whatsAppTemplate = "Template";
    public static final String whatsAppImage = "Image";
    public static final String whatsAppMsg = "Message";
    public static final String whatsAppMsgCode = "userCode";
    public static final String whatsAppMsgDriveLink = "driveLink";

}