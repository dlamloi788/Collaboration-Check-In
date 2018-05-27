package com.dlamloi.MAD.model;

/**
 * Created by Don on 8/05/2018.
 */

public class ChatMessage {

    private String id;
    private String timeSent;
    private String message;
    private String composer;

    public ChatMessage() {
        //Empty constructor required for firebase
    }

    /**
     * Use this constructor for sending to the repository manager
     *
     * @param timeSent time the message was sent
     * @param composer the user that sent the message
     */
    public ChatMessage(String timeSent, String composer, String message) {
        this.timeSent = timeSent;
        this.composer = composer;
        this.message = message;
    }

    public ChatMessage(String id, String timeSent, String composer, String message) {
        this.id = id;
        this.timeSent = timeSent;
        this.composer = composer;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }
}
