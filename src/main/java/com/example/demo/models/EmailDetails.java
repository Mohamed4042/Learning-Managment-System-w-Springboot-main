package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    // #1st change request(setters & Getters)
    public String getRecipient() { return recipient; }
    public String getMsgBody() { return msgBody; }
    public String getSubject() { return subject; }
    public String getAttachment() { return attachment; }

}
