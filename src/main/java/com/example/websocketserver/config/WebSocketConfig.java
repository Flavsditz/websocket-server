package com.example.websocketserver.config;

import com.example.websocketserver.interceptors.ClientInboundMessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    ClientInboundMessageInterceptor clientInboundMessageInterceptor;

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);

        registration.interceptors(clientInboundMessageInterceptor);
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        // Registry the same path 2 times: for websocket connections and for SockJS fallback
        registry.addEndpoint("/websocket-chat")
                .setAllowedOriginPatterns("*");

        registry.addEndpoint("/websocket-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

}