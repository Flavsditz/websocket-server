package com.example.websocketserver.model;

public class ChatMessage {

    private final String from;
    private final String text;

    public ChatMessage(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }

    public boolean isValid() {
        return this.from != null && this.text != null;
    }
}