package com.bappy.application.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    @PostMapping("/stripe")
    public ResponseEntity<String> stripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        // TODO: Implement Stripe webhook verification and handling
        // 1. Verify signature using Webhook.constructEvent
        // 2. Handle 'checkout.session.completed', 'invoice.payment_succeeded'
        // 3. Update UserSubscription and create PaymentTransaction
        
        log.info("Received Stripe Webhook");
        return ResponseEntity.ok("Received");
    }
    
    @PostMapping("/paypal")
    public ResponseEntity<String> paypalWebhook(@RequestBody String payload) {
        log.info("Received PayPal Webhook");
        // Implement PayPal webhook handling
        return ResponseEntity.ok("Received");
    }
}
