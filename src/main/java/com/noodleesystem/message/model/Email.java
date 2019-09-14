package com.noodleesystem.message.model;

public class Email {
    private String to;
    private String subcjet;
    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubcjet() {
        return subcjet;
    }

    public void setSubcjet(String subcjet) {
        this.subcjet = subcjet;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
