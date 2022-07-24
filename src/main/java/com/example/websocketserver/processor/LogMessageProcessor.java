package com.example.websocketserver.processor;

import com.example.websocketserver.model.OutputMessage;
import org.springframework.stereotype.Component;

@Component
public class LogMessageProcessor implements MessageProcessor {
    @Override
    public OutputMessage process(OutputMessage message) {
        System.out.println("The logger! Read "+message.getText());

        return message;
    }
}
