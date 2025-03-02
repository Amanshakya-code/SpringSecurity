package com.aman.SpringSecurity.SpringSecurity.Service;

import com.aman.SpringSecurity.SpringSecurity.DTO.SubscriptionDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.aman.SpringSecurity.SpringSecurity.Entity.SessionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.SubscriptionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Repositories.SubscriptionRepository;
import com.aman.SpringSecurity.SpringSecurity.Utils.SessionCountMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public SubscriptionDTO updateSubscriptionPlan(Long userId,Subscriptions newPlan){
        Users user = modelMapper.map(userService.getUserById(userId),Users.class);
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUsers(user);
        subscriptionEntity.setPlan(newPlan);
        subscriptionRepository.save(subscriptionEntity);
        return modelMapper.map(subscriptionEntity,SubscriptionDTO.class);
    }

    public SubscriptionDTO addSubscriptionPlan(Users user, Subscriptions plan){
        Integer activeSession = SessionCountMapper.getActiveSessionBasedOnSubscription(plan);
        SubscriptionEntity newSubscriptionEntity = SubscriptionEntity.builder()
                .users(user)
                .plan(plan)
                .activeSession(activeSession)
                .build();
        return modelMapper.map(subscriptionRepository.save(newSubscriptionEntity),SubscriptionDTO.class);
    }

    public Subscriptions getSubscriptionPlanFromUser(Users user){
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUsers(user);
        return subscriptionEntity.getPlan();
    }

    public Integer getActiveSessionForUser(Users user){
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByUsers(user);
        return subscriptionEntity.getActiveSession();
    }
}
