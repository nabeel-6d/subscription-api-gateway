package com.example.subscriptionmodule.subscriptionapigateway.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    
        @GetMapping("/yoho")
        public String getString(){
            return "heyyaa mate im up and working";
        }
}



/*
 * job is simple --> 
 *      just make the call to the subscriber api
 */