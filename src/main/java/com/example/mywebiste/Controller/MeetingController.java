package com.example.mywebiste.Controller;

import com.example.mywebiste.Model.Meeting;
import com.example.mywebiste.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MeetingController {




    @Autowired
    private MeetingRepository meetingRepository;

    @GetMapping("/meetings")
    public String showMeetings(Model model) {
        List<Meeting> meetings = meetingRepository.findAll();
        model.addAttribute("meetings", meetings);
        model.addAttribute("newMeeting", new Meeting());
        return "zoom";
    }


        @PostMapping("/meetings")
        public String createMeeting(@ModelAttribute Meeting meeting, Model model) {
            // Generate a mock Zoom link (you can replace this with real integration later)
            meeting.setZoomLink("https://zoom.us/j/" + (int)(Math.random() * 1_000_000_000));

            // Save the meeting to database (assuming JPA repository is autowired)
            meetingRepository.save(meeting);

            // Add the meeting to the model if you want to show it on next page
            model.addAttribute("meeting", meeting);

            // Redirect to a page that confirms booking or shows zoom link
            return "redirect:/zoom";  // or return "zoom.html" if you're rendering directly
        }


    @GetMapping("/delete/{id}")
    public String deleteMeeting(@PathVariable Long id) {
        meetingRepository.deleteById(id);
        return "redirect:/zoom";
    }
}
