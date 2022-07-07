package com.example.apidemo1.models;


import org.springframework.http.HttpStatus;

public class ResponseObject {

    private HttpStatus status;
    private String messagge;
    private Object data;

    public ResponseObject() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessagge() {
        return messagge;
    }

    public void setMessagge(String messagge) {
        this.messagge = messagge;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseObject(String messagge, Object data) {

        this.messagge = messagge;
        this.data = data;
    }
    public ResponseObject(HttpStatus status,String messagge, Object data) {
        this.status = status;
        this.messagge = messagge;
        this.data = data;
    }
}
