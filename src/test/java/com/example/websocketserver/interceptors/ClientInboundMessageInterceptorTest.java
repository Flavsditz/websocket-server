package com.example.websocketserver.interceptors;

import com.example.websocketserver.model.OutputMessage;
import com.example.websocketserver.processor.MessageProcessor;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

class ClientInboundMessageInterceptorTest {

    private static final OutputMessage goodMessage = new OutputMessage("John", "Hey there!", "00:00");

    @Test
    @DisplayName("Processing should happen on a good message case")
    void processingShouldHappen() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("simpMessageType", SimpMessageType.MESSAGE);

        byte[] bytes = new Gson().toJson(goodMessage).getBytes(StandardCharsets.UTF_8);
        Message<?> message = new GenericMessage<>(bytes, new MessageHeaders(headers));

        ClientInboundMessageInterceptor inboundMessageInterceptor = new ClientInboundMessageInterceptor();

        MessageProcessor mockProcessor = mock(MessageProcessor.class);
        inboundMessageInterceptor.interceptors = new ArrayList<>();
        inboundMessageInterceptor.interceptors.add(mockProcessor);


        MessageChannel channel = mock(MessageChannel.class);
        inboundMessageInterceptor.preSend(message, channel);

        verify(mockProcessor, times(1)).process(any());
    }

    @Test
    @DisplayName("Processing should NOT happen on a non OutputMessage case")
    void processingShouldNotHappen() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("simpMessageType", SimpMessageType.MESSAGE);

        byte[] bytes = new Gson().toJson("this is a string").getBytes(StandardCharsets.UTF_8);
        Message<?> message = new GenericMessage<>(bytes, new MessageHeaders(headers));

        ClientInboundMessageInterceptor inboundMessageInterceptor = new ClientInboundMessageInterceptor();

        MessageProcessor mockProcessor = mock(MessageProcessor.class);
        inboundMessageInterceptor.interceptors = new ArrayList<>();
        inboundMessageInterceptor.interceptors.add(mockProcessor);


        MessageChannel channel = mock(MessageChannel.class);
        inboundMessageInterceptor.preSend(message, channel);

        verify(mockProcessor, times(0)).process(any());
    }
}