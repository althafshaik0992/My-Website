package com.example.mywebiste.Controller;



import com.example.mywebiste.Model.Contact;
import com.example.mywebiste.Model.Meeting;
import com.example.mywebiste.Repository.ContactRepository;
import com.example.mywebiste.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@org.springframework.stereotype.Controller
//@CrossOrigin(origins = "http://localhost:5173")
public class Controller {

    @Autowired
     ContactRepository contactRepository;

    @Autowired
    MeetingRepository meetingRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contacts", "Portfolio website");
        model.addAttribute("github", "https://github.com/althafshaik0992");
        model.addAttribute("linkedin", "https://linkedin.com/in/yourlinkedin");
        //model.addAttribute("name", "Altha");
        model.addAttribute("twitter", "https://twitter.com/yourtwitter");
        return "index";
    }




}

