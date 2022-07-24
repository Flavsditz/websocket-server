package com.example.websocketserver.controller;

import com.example.websocketserver.model.ChatMessage;
import com.example.websocketserver.model.OutputMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaveControllerTest {

    private static LeaveController controller;
    private static ChatMessage goodMessage;

    private static final String LEAVES_TEMPLATE = "%s has left the room";

    @BeforeAll
    static void setUp() {
        controller = new LeaveController();
        goodMessage = new ChatMessage("John", "Hey there!");
    }

    @Test
    @DisplayName("Leave method should return OutputMessage type object")
    void leaveResultCorrectType() {
        OutputMessage outputMessage = controller.leave(goodMessage.getFrom());

        assertInstanceOf(OutputMessage.class, outputMessage, "output is not of expected type");
    }

    @Test
    @DisplayName("Leave method should return with a timestamp")
    void leaveResultHasTimestamp() {
        OutputMessage outputMessage = controller.leave(goodMessage.getFrom());

        assertNotNull(outputMessage.getTime(), "output should have a timestamp");
    }

    @Test
    @DisplayName("Leave method should not modify incoming messages")
    void leaveSendsStandardMessage() {
        OutputMessage outputMessage = controller.leave(goodMessage.getFrom());

        assertEquals(outputMessage.getText(), String.format(LEAVES_TEMPLATE, goodMessage.getFrom()), "Message TEXT should be equal to default");
    }

}