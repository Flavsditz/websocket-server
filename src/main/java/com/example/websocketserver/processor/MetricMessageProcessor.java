package com.example.websocketserver.processor;

import com.example.websocketserver.model.OutputMessage;
import org.springframework.stereotype.Component;

@Component
public class MetricMessageProcessor implements MessageProcessor {
    @Override
    public OutputMessage process(OutputMessage message) {
        System.out.println("The metrics! Read " + message.getText());

        return message;
    }
}
