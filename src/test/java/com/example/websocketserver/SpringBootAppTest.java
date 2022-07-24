package com.example.websocketserver;

import com.example.websocketserver.controller.ChatController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringBootAppTest {

    @LocalServerPort
    private int port;
    @Autowired
    private ChatController controller;

    @Test
    @DisplayName("context gets loaded correctly")
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

}