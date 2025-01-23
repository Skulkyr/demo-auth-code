package org.pogonin.confirmationservice.service;

import org.pogonin.confirmationservice.dto.in.ConfirmCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @KafkaListener(topics = "dbserver.public.confirmation_code", groupId = "6")
    public void listen(@Payload ConfirmCode confirmCode) {
        System.out.println(confirmCode);
    }
}
