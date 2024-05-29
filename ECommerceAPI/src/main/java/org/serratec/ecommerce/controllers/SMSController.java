package org.serratec.ecommerce.controllers;

import org.serratec.ecommerce.services.SmsRequest;
import org.serratec.ecommerce.services.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class SMSController {
        @Autowired
        private TwilioService twilioservice;

        public void SMScontroller(TwilioService twilioservice) {
            this.twilioservice = twilioservice;

        }

    @PostMapping("/send-sms")
    public void sendSMS(@RequestBody SmsRequest request) {
        twilioservice.sendSMS(request.getTo(), request.getMessage());


    }
}
