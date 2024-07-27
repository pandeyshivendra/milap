package com.example.demo.cotroller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestClass {

    public static void main(String[] args) throws ParseException {
        String dd = "2023-10-20 23:57:15";
        String inputString = "2023-10-03 13:57:15";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate = dateFormat.parse(inputString);


        if (inputDate.after(new Date())) {
            System.out.println("before");
        }
    }

    public static void increment(int i) {
        i = i + 2;
    }
}
