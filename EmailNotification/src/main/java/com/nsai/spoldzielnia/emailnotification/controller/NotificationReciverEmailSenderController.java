package com.nsai.spoldzielnia.emailnotification.controller;

import com.nsai.spoldzielnia.emailnotification.service.EMailService;
import com.nsai.spoldzielnia.emailnotification.entity.Notification;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationReciverEmailSenderController {

    private static final String QUEUE_244019 = "244019";
    private final RabbitTemplate rabbitTemplate;
    private EMailService eMailService;

    public NotificationReciverEmailSenderController(RabbitTemplate rabbitTemplate, EMailService eMailService) {
        this.rabbitTemplate = rabbitTemplate;
        this.eMailService = eMailService;
    }

    @RabbitListener(queues = QUEUE_244019)
    public void messageListener(Notification notification){

        System.out.println(notification);
        if(EmailValidator.getInstance().isValid(notification.getEmail())){
            eMailService.sendNotificationMessage(notification.getEmail(), notification.getTitle(), notification.getBody());

        }else{
            System.out.println("invalid email");
        }

    }

    //todo to poniżej do testów tylko - usunac
    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification){
        rabbitTemplate.convertAndSend(QUEUE_244019, notification);
        return "Notification zostala wyslana";
    }


}
