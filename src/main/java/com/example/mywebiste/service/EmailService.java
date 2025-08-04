package com.example.mywebiste.service;

import com.example.mywebiste.Model.Contact;
import com.example.mywebiste.Model.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;


@Service
@Component
public class EmailService {


    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;


    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendContactEmail(Contact contactForm) throws MessagingException {


        Properties server = propertiesServer();
        Date date = new Date();
        javax.mail.Session session = javax.mail.Session.getInstance(server, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
        javax.mail.Message message = new MimeMessage(session);
        message.setSentDate(date);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(contactForm.getEmail()));
        message.setSubject("New Contact Message from " + contactForm.getName());
        String newline = System.lineSeparator();
        UUID uuid = UUID.randomUUID();

        message.setText("Name: " + contactForm.getName() + "\n" +
                "Email: " + contactForm.getEmail() + "\n" +
                "Message: " + contactForm.getMessage()
        );



        Transport.send(message);
        System.out.println(" message " + new RuntimeException());
        System.out.println("email sent");


    }

    public void sendMeetingEmail(ScheduleRequest meetingForm, String meetingLink) throws MessagingException {


        Properties server = propertiesServer();
        Date date = new Date();
        javax.mail.Session session = javax.mail.Session.getInstance(server, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
        javax.mail.Message message = new MimeMessage(session);
        message.setSentDate(date);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(meetingForm.getEmail()));
        message.setSubject("Interview Request For  " + meetingForm.getName());
        String newline = System.lineSeparator();
        UUID uuid = UUID.randomUUID();

        message.setText("A new meeting has been requested:\n" +
                "Name: " + meetingForm.getName() + "\n" +
                "Email: " + meetingForm.getEmail() + "\n" +
                "Date: " + meetingForm.getMeetingDate() + "\n" +
                "Time: " + meetingForm.getMeetingTime() + "\n" +
                "Meeting Link: " + meetingLink
        );


        Transport.send(message);
        System.out.println(" message " + new RuntimeException());
        System.out.println("email sent");
    }


    public Properties propertiesServer() {
        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        return properties;


    }
}
