package com.example.mywebiste.Controller;

import com.example.mywebiste.Model.ScheduleRequest;
import com.example.mywebiste.Model.ScheduleResponse;
import com.example.mywebiste.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MeetingController {

    @Autowired
    protected MeetingService meetingService;



    @GetMapping("/schedule-meeting")
    public String scheduleMeeting() {
        return "index";
    }



    @PostMapping("/schedule-meeting")
    public ResponseEntity<ScheduleResponse> scheduleMeeting(@RequestBody ScheduleRequest request) {
        // Validate the request data
        if (request.getName() == null || request.getEmail() == null || request.getMeetingDate() == null || request.getMeetingTime() == null) {
            return ResponseEntity.badRequest().body(new ScheduleResponse("error", "All fields are required."));
        }

        // Call the service to handle the scheduling logic
        ScheduleResponse response = meetingService.schedule(request);

        // Return the response to the frontend
        return ResponseEntity.ok(response);
    }
}
