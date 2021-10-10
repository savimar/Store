package ru.savimar.storedemo.entity;

//for kafka


public class Letter {
    private int id;
    private String to;
    private String message;

    public Letter() {
    }

    @Override
    public String toString() {
        return "Letter{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Letter(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public Letter(int id, String to, String message) {
        this.id = id;
        this.to = to;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
