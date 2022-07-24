package com.example.websocketserver.processor;

import com.example.websocketserver.model.OutputMessage;

public interface MessageProcessor {
    default OutputMessage process(OutputMessage message){
        return message;
    };
}
