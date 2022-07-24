package com.example.websocketserver.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampService {
    private final static String PATTERN = "HH:mm";
    public static String getMessageTimestamp(){
        return new SimpleDateFormat(PATTERN).format(new Date());
    }
}
