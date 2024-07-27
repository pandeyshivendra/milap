package com.example.demo.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseObj {

    private String statusCode;
    private String statusMsg;
    private Object data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static String getJson(ResponseObj resp) {
        String jsonString = "";
        try {
            ObjectMapper Obj = new ObjectMapper();
            return Obj.writeValueAsString(resp);
        } catch (Exception e) {
            e.printStackTrace();
            jsonString = resp.toString();
        }
        return jsonString;
    }

    @Override
    public String toString() {
        return "ResponseObj{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
