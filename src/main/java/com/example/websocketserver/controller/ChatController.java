package com.example.websocketserver.controller;

import com.example.websocketserver.model.ChatMessage;
import com.example.websocketserver.model.OutputMessage;
import com.example.websocketserver.service.TimestampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.InvalidPropertiesFormatException;

@Controller
public class ChatController {

    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void send(final ChatMessage message) {
        final String time = TimestampService.getMessageTimestamp();

        OutputMessage outputMessage = new OutputMessage(message.getFrom(), message.getText(), time);

        if(message.isValid()){
            this.template.convertAndSend("/topic/messages", outputMessage);
        }

    }

}