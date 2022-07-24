package com.example.websocketserver.controller;

import com.example.websocketserver.model.OutputMessage;
import com.example.websocketserver.service.TimestampService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LeaveController {

    private final String SERVER_USERNAME = "Server";
    private final String LEAVES_TEMPLATE = "%s has left the room";

    @MessageMapping("/left")
    @SendTo("/topic/userLeft")
    public OutputMessage leave(final String userName) {
        final String time = TimestampService.getMessageTimestamp();

        return new OutputMessage(SERVER_USERNAME, String.format(LEAVES_TEMPLATE, userName), time);
    }

}