package com.example.mywebiste.service;


import com.example.mywebiste.Model.ScheduleRequest;
import com.example.mywebiste.Model.ScheduleResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MeetingService {

    public ScheduleResponse schedule(ScheduleRequest request) {
        // In a real application, you would integrate with a calendar API (e.g., Google Calendar API).
        // For this example, we'll simulate the process and generate mock data.

        try {
            // Simulate sending an email with a meeting invite
            System.out.println("Simulating email to: " + request.getEmail());
            System.out.println("Subject: Meeting Scheduled with " + request.getName());
            System.out.println("Body: Your meeting is scheduled for " + request.getMeetingDate() + " at " + request.getMeetingTime());
            System.out.println("Link: https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 10));

            // Create a mock response
            String meetingLink = "https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 10);
            return new ScheduleResponse("success", "Meeting successfully scheduled!", meetingLink);

        } catch (Exception e) {
            // Handle any exceptions during the scheduling or email process
            System.err.println("Failed to schedule meeting: " + e.getMessage());
            return new ScheduleResponse("error", "An error occurred during scheduling.");
        }
    }
}
