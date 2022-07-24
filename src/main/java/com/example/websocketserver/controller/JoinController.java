package com.example.websocketserver.controller;

import com.example.websocketserver.model.ChatMessage;
import com.example.websocketserver.model.OutputMessage;
import com.example.websocketserver.service.TimestampService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class JoinController {

    private final String SERVER_USERNAME = "Server";
    private final String JOIN_TEMPLATE = "%s has joined us";


    @MessageMapping("/joined")
    @SendTo("/topic/userJoined")
    public OutputMessage join(final String userName) {
        final String time = TimestampService.getMessageTimestamp();

        return new OutputMessage(SERVER_USERNAME, String.format(JOIN_TEMPLATE, userName), time);
    }


}