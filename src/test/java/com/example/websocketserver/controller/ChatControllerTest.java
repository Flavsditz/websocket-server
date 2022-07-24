package com.example.websocketserver.controller;

import com.example.websocketserver.model.ChatMessage;
import com.example.websocketserver.model.OutputMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

class ChatControllerTest {

    private static ChatController controller;
    private static ChatMessage goodMessage;
    private static ChatMessage badMessage;
    private static SimpMessagingTemplate mockTemplate;

    @BeforeAll
    static void setUp() {
        controller = new ChatController();
        mockTemplate = mock(SimpMessagingTemplate.class);
        controller.template = mockTemplate;
        goodMessage = new ChatMessage("John", "Hey there!");
        badMessage = new ChatMessage(null, null);
    }

    @Test
    @DisplayName("Send method forwards message if is valid")
    void sendResultCorrectType() {
        controller.send(goodMessage);

        verify(mockTemplate, times(1)).convertAndSend(anyString(), any(OutputMessage.class));
    }

    @Test
    @DisplayName("Send method does not forwards message if it is NOT valid")
    void sendDoesNotModify() {
        controller.send(badMessage);

        verify(mockTemplate, times(0)).convertAndSend(anyString(), any(OutputMessage.class));
    }
}