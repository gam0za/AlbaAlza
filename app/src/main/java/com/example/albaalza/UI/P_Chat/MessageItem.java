package com.example.albaalza.UI.P_Chat;

/**
 * Created by jinyoungkim on 2018. 5. 16..
 */

public class MessageItem {

    String message_writer;
    String message_time;
    String message_text;
    String message_writer_image;

    public MessageItem(String message_writer, String message_text) {
        this.message_writer = message_writer;
        this.message_text = message_text;
    }

    public String getMessage_writer() {
        return message_writer;
    }

    public String getMessage_time() {
        return message_time;
    }

    public String getMessage_text() {
        return message_text;
    }

    public String getMessage_writer_image() {
        return message_writer_image;
    }


}
