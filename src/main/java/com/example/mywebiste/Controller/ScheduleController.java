package com.example.mywebiste.Controller;

import com.example.mywebiste.Model.ScheduleRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class ScheduleController {

    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("scheduleRequest", new ScheduleRequest());
        return "home";
    }

    @PostMapping("/schedule")
    public String submitSchedule(@ModelAttribute ScheduleRequest request, Model model) {
        model.addAttribute("message", "Meeting scheduled for " + request.getDate() + " at " + request.getTime());
        return "schedule";
    }
}
