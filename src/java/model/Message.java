/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dbdtoan
 */
public class Message {
    private String id,sender,receiver,content, timestamp;

    public Message() {
    }

    public Message(String id, String sender, String receiver, String content, String timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }
    

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }
    
   
    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
