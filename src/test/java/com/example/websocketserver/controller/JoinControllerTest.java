package com.example.websocketserver.controller;

import com.example.websocketserver.model.ChatMessage;
import com.example.websocketserver.model.OutputMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JoinControllerTest {

    private static JoinController controller;
    private static ChatMessage goodMessage;

    private static final String JOIN_TEMPLATE = "%s has joined us";

    @BeforeAll
    static void setUp() {
        controller = new JoinController();
        goodMessage = new ChatMessage("John", "Hey there!");
    }

    @Test
    @DisplayName("Join method should return OutputMessage type object")
    void joinResultCorrectType() {
        OutputMessage outputMessage = controller.join(goodMessage.getFrom());

        assertInstanceOf(OutputMessage.class, outputMessage, "output is not of expected type");
    }

    @Test
    @DisplayName("Join method should return with a timestamp")
    void joinResultHasTimestamp() {
        OutputMessage outputMessage = controller.join(goodMessage.getFrom());

        assertNotNull(outputMessage.getTime(), "output should have a timestamp");
    }

    @Test
    @DisplayName("Join method should not modify incoming messages")
    void joinSendsStandardMessage() {
        OutputMessage outputMessage = controller.join(goodMessage.getFrom());

        assertEquals(outputMessage.getText(), String.format(JOIN_TEMPLATE, goodMessage.getFrom()), "Message TEXT should be equal to default");
    }
}