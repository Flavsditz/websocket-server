package com.example.websocketserver.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimestampServiceTest {

    @Test
    @DisplayName("Timestamp should be in HH:mm format")
    void timestampFormat() {
        String timestampString = TimestampService.getMessageTimestamp();

        Assertions.registerCustomDateFormat("HH:mm");


        assertThat(timestampString).matches(Pattern.compile("(2[0-3]|[01][0-9]):[0-5][0-9]"));

    }
}