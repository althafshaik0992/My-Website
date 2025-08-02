package com.example.mywebiste.Model;

public class ScheduleResponse {


    private String status;
    private String message;
    private String meetingLink;

    public ScheduleResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ScheduleResponse(String status, String message, String meetingLink) {
        this.status = status;
        this.message = message;
        this.meetingLink = meetingLink;
    }

    // Getters
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public String getMeetingLink() { return meetingLink; }
}

