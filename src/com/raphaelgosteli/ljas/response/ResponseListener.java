package com.raphaelgosteli.ljas.response;

abstract public class ResponseListener {
    public void beforeSend() {

    }

    public String beforeSend(String content) {
        return content;
    }

    public void afterSend() {

    }
}
