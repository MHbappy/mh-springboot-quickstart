package com.bappy.application.payment.service;

import com.bappy.application.payment.dto.CheckoutRequest;
import com.bappy.application.payment.dto.CheckoutResponse;
import com.bappy.application.payment.dto.SubscriptionPlanDto;
import com.bappy.application.payment.entity.GatewayConfig;
import com.bappy.application.payment.entity.SubscriptionPlan;
import com.bappy.application.payment.entity.UserSubscription;
import com.bappy.application.payment.repository.GatewayConfigRepository;
import com.bappy.application.payment.repository.SubscriptionPlanRepository;
import com.bappy.application.payment.repository.UserSubscriptionRepository;
import com.bappy.application.user.entity.User;
import com.bappy.application.user.repository.UserRepository; // Assuming UserRepository exists
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final UserSubscriptionRepository subscriptionRepository;
    private final GatewayConfigRepository gatewayConfigRepository;
    private final List<PaymentGatewayService> gatewayServices;
    // Assuming we can get current user or passed in. For now passing ID.
    private final UserRepository userRepository; 

    @Transactional(readOnly = true)
    public List<SubscriptionPlanDto> getActivePlans() {
        return planRepository.findByIsActiveTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    

    @Transactional(readOnly = true)
    public List<SubscriptionPlanDto> getAllPlans() {
        return planRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    

    @Transactional
    public SubscriptionPlanDto createPlan(SubscriptionPlan plan) {
        return mapToDto(planRepository.save(plan));
    }

    @Transactional
    public SubscriptionPlanDto updatePlan(Long id, SubscriptionPlan planDetails) {
        SubscriptionPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        plan.setName(planDetails.getName());
        plan.setPrice(planDetails.getPrice());
        plan.setCurrency(planDetails.getCurrency());
        plan.setInterval(planDetails.getInterval());
        plan.setDescription(planDetails.getDescription());
        plan.setFeatures(planDetails.getFeatures());
        plan.setActive(planDetails.isActive());
        
        // Code is usually immutable as it might be used for external references, but can be changed if needed.
        // plan.setCode(planDetails.getCode()); 

        return mapToDto(planRepository.save(plan));
    }

    @Transactional
    public CheckoutResponse initiateCheckout(Long userId, CheckoutRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionPlan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        
        // Determine gateway from request, default to STRIPE if null
        GatewayConfig.GatewayType gatewayType = GatewayConfig.GatewayType.STRIPE;
        if (request.getGateway() != null) {
            try {
                gatewayType = GatewayConfig.GatewayType.valueOf(request.getGateway().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Keep default
            }
        }

        PaymentGatewayService gateway = getGatewayService(gatewayType);
        
        return gateway.createCheckoutSession(user, plan, request.getSuccessUrl(), request.getCancelUrl());
    }
    
    public Optional<UserSubscription> getCurrentSubscription(Long userId) {
        return subscriptionRepository.findActiveSubscriptionByUserId(userId);
    }

    private PaymentGatewayService getGatewayService(GatewayConfig.GatewayType type) {
        return gatewayServices.stream()
                .filter(s -> s.getGatewayType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gateway implementation not found for: " + type));
    }

    private SubscriptionPlanDto mapToDto(SubscriptionPlan plan) {
        SubscriptionPlanDto dto = new SubscriptionPlanDto();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setCode(plan.getCode());
        dto.setPrice(plan.getPrice());
        dto.setCurrency(plan.getCurrency());
        dto.setInterval(plan.getInterval());
        dto.setDescription(plan.getDescription());
        if (plan.getFeatures() != null) {
            dto.setFeatures(new java.util.ArrayList<>(plan.getFeatures()));
        } else {
            dto.setFeatures(new java.util.ArrayList<>());
        }
        dto.setActive(plan.isActive());
        return dto;
    }

    public List<String> getEnabledGateways() {
        return gatewayConfigRepository.findAll().stream()
                .filter(GatewayConfig::isEnabled)
                .map(g -> g.getGatewayName().name())
                .collect(Collectors.toList());
    }
}
