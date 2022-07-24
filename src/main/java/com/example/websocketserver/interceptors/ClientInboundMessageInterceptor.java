package com.example.websocketserver.interceptors;

import com.example.websocketserver.processor.MessageProcessor;
import com.example.websocketserver.model.OutputMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * This Interceptor is used to intercept messages being passed to a specific channel.
 * The channel in question is configured in {@link com.example.websocketserver.config.WebSocketConfig}.
 *
 * In this implementation we are using it to intercept the messages (of type {@link OutputMessage})
 * coming in from the users of the chat and processing them using a predefined interface and
 * self defined classes.
 */
@Component
public class ClientInboundMessageInterceptor implements ChannelInterceptor {

    @Autowired
    protected List<MessageProcessor> interceptors;

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        SimpMessageType messageType = (SimpMessageType) message.getHeaders().get("simpMessageType");

        switch (Objects.requireNonNull(messageType)) {
            case MESSAGE:
                byte[] payloadBytes = (byte[]) message.getPayload();


                Message<?> maybeModifiedMessage;
                try {
                    OutputMessage interceptedMessage = new Gson().fromJson(new String(payloadBytes), OutputMessage.class);

                    for (MessageProcessor interceptor : interceptors) {
                        interceptedMessage = interceptor.process(interceptedMessage);
                    }

                    // Create a new message with the same headers to be passed along the process
                    // it is in bytes since this is the way STOMP communicates, the way it was received by the
                    // function and we want to be minimally invasive
                    byte[] bytes = new Gson().toJson(interceptedMessage).getBytes(StandardCharsets.UTF_8);
                    maybeModifiedMessage = new GenericMessage<>(bytes, message.getHeaders());
                } catch (JsonSyntaxException e) {
                    logger.warn("Message sent through ClientInboundChannel was not of OutputMessage class: " +
                            e.getMessage());

                    maybeModifiedMessage = message;
                }


                return ChannelInterceptor.super.preSend(maybeModifiedMessage, channel);

            case CONNECT:
            case DISCONNECT:
            case CONNECT_ACK:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
            case HEARTBEAT:
            case DISCONNECT_ACK:
            case OTHER:
                break;
        }

        return ChannelInterceptor.super.preSend(message, channel);
    }
}
