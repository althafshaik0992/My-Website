package com.example.mywebiste.Controller;

import com.example.mywebiste.Model.Contact;
import com.example.mywebiste.Model.ScheduleRequest;
import com.example.mywebiste.Model.ScheduleResponse;
import com.example.mywebiste.service.EmailService;
import com.example.mywebiste.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MeetingController {

    @Autowired
    protected MeetingService meetingService;



    @GetMapping("/schedule-meeting")
    public String scheduleMeeting() {
        return "index";
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-contact-email")
    public ResponseEntity<Map<String, String>> handleContactForm(@RequestBody Contact contactForm) {
        try {
            emailService.sendContactEmail(contactForm);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Message sent successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to send message.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

//    @PostMapping("/schedule-meeting")
//    public ResponseEntity<Map<String, String>> handleScheduleForm(@RequestBody ScheduleRequest meetingForm){
//        try {
//            String meetingLink = "https://meet.google.com/" + UUID.randomUUID().toString().substring(0, 12);
//            emailService.sendMeetingEmail(, meetingLink);
//
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "Meeting successfully scheduled!");
//            response.put("meetingLink", meetingLink);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Failed to schedule meeting.");
//            return ResponseEntity.status(500).body(errorResponse);
//        }
//    }

//    @PostMapping("/schedule-meeting")
//    public ResponseEntity<ScheduleResponse> scheduleMeeting(@RequestBody ScheduleRequest request) {
//        // Validate the request data
//        if (request.getName() == null || request.getEmail() == null || request.getMeetingDate() == null || request.getMeetingTime() == null) {
//            return ResponseEntity.badRequest().body(new ScheduleResponse("error", "All fields are required."));
//        }
//
//        // Call the service to handle the scheduling logic
//        ScheduleResponse response = meetingService.schedule(request);
//
//        // Return the response to the frontend
//        return ResponseEntity.ok(response);
//    }



    @PostMapping("/schedule-meeting")
    public ResponseEntity<Map<String, String>> handleScheduleForm(@RequestBody ScheduleRequest meetingForm) {
        try {
            // Generate a mock Zoom meeting link.
            // For Microsoft Teams, you could use a similar URL pattern:
            // "https://teams.microsoft.com/l/meetup-join/..."
            String meetingLink = "https://zoom.us/j/" + UUID.randomUUID().toString().replace("-", "");
            emailService.sendMeetingEmail(meetingForm, meetingLink);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Meeting successfully scheduled!");
            response.put("meetingLink", meetingLink);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to schedule meeting.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
