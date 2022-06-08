package com.nsai.spoldzielnia.emailnotification.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
public class Config {

    //skrysmailspring@gmail.com
    //springmail1


    /*
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("skrysmailspring@gmail.com");
        mailSender.setPassword("springmail1");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
    */


    //ZOHO skrysmailspring@zohomail.eu
    //ZOHO Springmail1!
    @Bean
    public JavaMailSender getJavaMailSender() {

        /*Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });*/

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.zoho.eu");
        mailSender.setPort(587);

        mailSender.setUsername("skrysmailspring@zohomail.eu");
        mailSender.setPassword("Springmail1!");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


}
