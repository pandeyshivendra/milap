package com.example.demo.utility;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import com.amazonaws.util.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class Utility {
    private static final Logger logger = LoggerFactory.getLogger(Utility.class);
    public static String getToken() {
        int n = 20;
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
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


    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static String generateOTP(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        String otpStr = String.copyValueOf(otp);
        //System.out.println("OTP -->> "+otpStr);
        return otpStr;
    }

    public static Date addMinutsToJavaUtilDate(Date date, int minuts) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuts);
        return calendar.getTime();
    }

    public static Date getDate(String dateS) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
        //formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date = null;
        try {
            date = formatter.parse(dateS);
        } catch (ParseException e) {
            e.printStackTrace();
            //date = new Date();
        }
        return date;
    }
    public static Date minusDaysFromDt(int days) {
        Date date = new Date(); // Or where ever you get it from
        Date daysAgo = new DateTime(date).minusDays(days).toDate();
        return daysAgo;

    }

    public static Boolean deleteFile(String filePath) {
        boolean bool = false;
        File fileToDelete = new File(filePath);

        // Check if the file exists before attempting to delete it
        if (fileToDelete.exists()) {
            // Attempt to delete the file
            if (fileToDelete.delete()) {
                logger.info("\n\n File deleted successfully :::::: ");
                bool = true;
            } else {
                logger.info("\n\nFailed to delete the file :::::");
            }
        } else {
            logger.info("\n\nThe file does not exist :::::: ");
        }
        return bool;
    }


    // Driver code
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String s = "6515";
        //System.out.println("Your HashCode Generated by MD5 is: " + getMd5(s));
        //System.out.println("generate otp -->> "+generateOTP(4));
        System.out.println(addMinutsToJavaUtilDate(new Date(), 10));
        System.out.println(minusDaysFromDt(30));
    }


}
