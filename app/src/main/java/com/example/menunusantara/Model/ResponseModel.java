package com.example.menunusantara.Model;

public class ResponseModel<T> {
    private boolean success;
    private String message;
    private T data;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    public T getData() {
        return data;
    }

    public void setData(T data) { this.data = data; }
}