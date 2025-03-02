package com.aman.SpringSecurity.SpringSecurity.Controllers;

import com.aman.SpringSecurity.SpringSecurity.DTO.SubscriptionDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.aman.SpringSecurity.SpringSecurity.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PutMapping(path = "/{userId}/plan/{subscriptionPlan}")
    @PreAuthorize("@postSecurity.hasPremiumSubscription()")
    public SubscriptionDTO updateTheSubscriptionPlan(@PathVariable Long userId, @PathVariable Subscriptions subscriptionPlan){
        return subscriptionService.updateSubscriptionPlan(userId,subscriptionPlan);
    }

}
