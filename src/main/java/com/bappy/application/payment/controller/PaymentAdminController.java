package com.bappy.application.payment.controller;


import com.bappy.application.payment.dto.GatewayConfigDto;
import com.bappy.application.payment.dto.SubscriptionPlanDto;
import com.bappy.application.payment.entity.GatewayConfig;
import com.bappy.application.payment.entity.SubscriptionPlan;
import com.bappy.application.payment.repository.GatewayConfigRepository;
import com.bappy.application.payment.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/payment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PaymentAdminController {

    private final GatewayConfigRepository gatewayConfigRepository;
    private final SubscriptionService subscriptionService;

    @GetMapping("/gateways")
    public ResponseEntity<List<GatewayConfig>> getAllGateways() {
        return ResponseEntity.ok(gatewayConfigRepository.findAll());
    }

    @PostMapping("/gateways")
    public ResponseEntity<GatewayConfig> updateGateway(@RequestBody GatewayConfigDto dto) {
        // Simple update logic for MVP
        GatewayConfig config = gatewayConfigRepository.findByGatewayName(dto.getGatewayName())
                .orElse(GatewayConfig.builder().gatewayName(dto.getGatewayName()).build());

        config.setApiKey(dto.getApiKey());
        if (dto.getSecretKey() != null && !dto.getSecretKey().isEmpty()) {
            config.setSecretKey(dto.getSecretKey());
        }
        config.setEnabled(dto.isEnabled());
        config.setTestMode(dto.isTestMode());
        config.setWebhookSecret(dto.getWebhookSecret());

        return ResponseEntity.ok(gatewayConfigRepository.save(config));
    }


    @GetMapping("/plans")
    public ResponseEntity<List<SubscriptionPlanDto>> getAllPlans() {
        return ResponseEntity.ok(subscriptionService.getAllPlans());
    }


    @PostMapping("/plans")
    public ResponseEntity<SubscriptionPlanDto> createAvailablePlan(@RequestBody SubscriptionPlan plan) {
        return ResponseEntity.ok(subscriptionService.createPlan(plan));
    }

    @PutMapping("/plans/{id}")
    public ResponseEntity<SubscriptionPlanDto> updatePlan(@PathVariable Long id, @RequestBody SubscriptionPlan plan) {
        return ResponseEntity.ok(subscriptionService.updatePlan(id, plan));
    }
}
