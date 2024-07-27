package com.example.demo.utility;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.WhatsappTemplateStatus;
import com.example.demo.googledrive.DataContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WhatsAppMsg {


    private static BodyPublisher getPostBody(DataContainer dc, String type) throws JsonProcessingException {
        String bodyStr = getJsonString(dc, type);
        return HttpRequest.BodyPublishers.ofString(bodyStr);
    }

    public static void messageSent(DataContainer dc, String type) {
        System.out.println("SEnding ...."+dc.getMobileNo());
        //"{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"<TARGET PHONE NUMBER>\", \"type\": \"text\", \"text\": { \"preview_url\": false, \"body\": \"This is an example of a text message\" } }"

        if (type.equals(StrConst.whatsAppTemplate)) {
            Boolean bool = dc.getAc().getCalculationsLogic().checkWhatsAppStatus(dc.getMobileNo());
            if (bool)
                return;  // not sending template message As it is already sent once within 24h
        }

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/141133392407555/messages"))
                    .header("Authorization", "Bearer EAAECkyHTQcgBOzSIlkRhH0vcsg9BLoxs0h5FqHraP5C7k6S17lUKiUVGgJRnEBWQAZBJhEfxUnOve84hsZAumFitiI5OeA7IJGaTiJlhS5e3hQjShtGyWP6ZBctNFTV5P2KzT16R01ESbUHnZCYzarPJhlYhh3QHVskknUZBP1MVvjfqWRwoaVVwZCDZBGgZB1JD")
                    .header("Content-Type", "application/json")
                    .POST(getPostBody(dc, type)).build();
            //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"8745028441\", \"type\": \"image\", \"image\": { \"link\": \"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/IMG_20190113_071809.jpg\"} }"))


            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            if (type.equals(StrConst.whatsAppTemplate)) {
                WhatsappTemplateStatus wts = new WhatsappTemplateStatus();
                wts.setReplyStatus(0);
                wts.setUserMobile(dc.getMobileNo());
                dc.getAc().getAppTemplateMsgRepo().save(wts);   // storing into template msg table to track whether template msg sent or not later

            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void testSending(DataContainer dc) {
        System.out.println("SEnding ....");
        try {

            String msgBody = "{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"8745028441\", \"type\": \"image\", \"image\": { \"link\": \"https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/IMG_20190113_071809.jpg\"} }";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/141133392407555/messages"))
                    .header("Authorization", "Bearer EAAECkyHTQcgBOzSIlkRhH0vcsg9BLoxs0h5FqHraP5C7k6S17lUKiUVGgJRnEBWQAZBJhEfxUnOve84hsZAumFitiI5OeA7IJGaTiJlhS5e3hQjShtGyWP6ZBctNFTV5P2KzT16R01ESbUHnZCYzarPJhlYhh3QHVskknUZBP1MVvjfqWRwoaVVwZCDZBGgZB1JD")
                    .header("Content-Type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"8745028441\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" }} }"))
                    .POST(HttpRequest.BodyPublishers.ofString(msgBody))

                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static String getJsonString(DataContainer dc, String flag) throws JsonProcessingException {
        //"{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"<TARGET PHONE NUMBER>\", \"type\": \"text\", \"text\": { \"preview_url\": false, \"body\": \"This is an example of a text message\" } }"

        Map<String, String> childeObj = null;
        Map<String, Object> data = new LinkedHashMap();
        data.put("messaging_product", "whatsapp");
        data.put("recipient_type", "individual");
        data.put("to", dc.getMobileNo());
        if (flag.equals(StrConst.whatsAppImage)) {
            childeObj = new LinkedHashMap();
            childeObj.put("link", dc.getMessage());
            data.put("type", "image");
            data.put("image", childeObj);
        } else if (flag.equals(StrConst.whatsAppTemplate)) {
            childeObj = new LinkedHashMap();
            childeObj.put("name", "clickpic_wellcome");
            childeObj.put("language", "{ \"code\": \"en\" }");
            data.put("type", "template");
            data.put("template", childeObj);
        } else if (flag.equals(StrConst.whatsAppMsg)) {
            childeObj = new LinkedHashMap();
            childeObj.put("preview_url", "false");
            //childeObj.put("language", "{ \"code\": \"en_US\" }");
            childeObj.put("body", dc.getMessage());
            data.put("type", "text");
            data.put("text", childeObj);
        }else if(flag.equals(StrConst.whatsAppMsgCode)){
            /*childeObj = new LinkedHashMap();
            childeObj.put("name", "clickpic_code");
            childeObj.put("language", "{ \"code\": \"en\" }");
            childeObj.put("components","[{\"type\":\"body\",\"parameters\":[{\"type\":\"text\",\"text\":"+dc.getMessage()+"}]}]");
            data.put("type", "template");
            data.put("template", childeObj);*/
            String datastr = "{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \""+dc.getMobileNo()+"\", \"type\": \"template\", \"template\": { \"name\": \"clickpic_code\", \"language\": { \"code\": \"en\" }, \"components\": [{" +
                    "\"type\": \"body\",  \"parameters\": [{ \"type\": \"text\", \"text\": \""+dc.getMessage()+"\"}]}]}}";
            return datastr;
        }else if(flag.equals(StrConst.whatsAppMsgDriveLink)){
           String dataStr =  "{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \""+dc.getMobileNo()+"\", \"type\": \"template\", \"template\": { \"name\": \"clickpic_photo_store\", \"language\": { \"code\": \"en\" }, \"components\": [{" +
                    "\"type\": \"body\",  \"parameters\": [{ \"type\": \"text\", \"text\": \""+dc.getMessage()+"\"}]}]}}";
             return dataStr;
        }


        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(data);
        System.out.println("Result -- >> " + jacksonData);
        return jacksonData;
    }

    public static void main(String[] args) throws JsonProcessingException {
        DataContainer dc = new DataContainer();
        dc.setMobileNo("8745028441");
        dc.setMessage("https://clickpic-whatsapp-image.s3.ap-south-1.amazonaws.com/to-be-sent/IMG_20190412_164147.jpg");
       //dc.setMessage("1234");
        //getJsonString(dc, "Message");
        sendWhatAppCode(dc.getMobileNo(),dc.getMessage());

        //testSending(dc);
    }


    public static Boolean sendWhatAppCode(String mobile,String code) {
        try {
            DataContainer dcObj = new DataContainer();
            dcObj.setMessage(code);
            dcObj.setMobileNo(mobile);
            String reqJson = getJsonString(dcObj,StrConst.whatsAppMsgCode);
            System.out.println(reqJson);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/141133392407555/messages"))
                    .header("Authorization", "Bearer EAAECkyHTQcgBOzSIlkRhH0vcsg9BLoxs0h5FqHraP5C7k6S17lUKiUVGgJRnEBWQAZBJhEfxUnOve84hsZAumFitiI5OeA7IJGaTiJlhS5e3hQjShtGyWP6ZBctNFTV5P2KzT16R01ESbUHnZCYzarPJhlYhh3QHVskknUZBP1MVvjfqWRwoaVVwZCDZBGgZB1JD")
                    .header("Content-Type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"8745028441\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" }} }"))
                    .POST(HttpRequest.BodyPublishers.ofString(reqJson))
                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return true;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean sendWhatAppDriveLInk(String mobile, String link) {
        try {
            DataContainer dcObj = new DataContainer();
            dcObj.setMessage(link);
            dcObj.setMobileNo(mobile);
            String reqJson = getJsonString(dcObj,StrConst.whatsAppMsgDriveLink);
            System.out.println("--->>>>"+reqJson);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/141133392407555/messages"))
                    .header("Authorization", "Bearer EAAECkyHTQcgBOzSIlkRhH0vcsg9BLoxs0h5FqHraP5C7k6S17lUKiUVGgJRnEBWQAZBJhEfxUnOve84hsZAumFitiI5OeA7IJGaTiJlhS5e3hQjShtGyWP6ZBctNFTV5P2KzT16R01ESbUHnZCYzarPJhlYhh3QHVskknUZBP1MVvjfqWRwoaVVwZCDZBGgZB1JD")
                    .header("Content-Type", "application/json")
                    //.POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"8745028441\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" }} }"))
                    .POST(HttpRequest.BodyPublishers.ofString(reqJson))
                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());



            return true;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
