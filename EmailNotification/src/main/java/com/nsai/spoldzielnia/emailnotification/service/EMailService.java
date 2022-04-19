package com.nsai.spoldzielnia.emailnotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


//skrysmailspring@gmail.com
//springmail1
@Service
public class EMailService {

    @Autowired
    private JavaMailSender emailSender;


    public void sendNotificationMessage(String to, String subject, String text) {
        //...
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@Spoldzielnia.pl");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        //...
    }
}
