package com.aman.SpringSecurity.SpringSecurity.Utils;

import com.aman.SpringSecurity.SpringSecurity.DTO.PostDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Service.PostService;
import com.aman.SpringSecurity.SpringSecurity.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private  final PostService postService;
    private final SubscriptionService subscriptionService;

    public boolean isOwnerOfPost(Long postId) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }

    public boolean hasPremiumSubscription() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Subscriptions subscriptions = subscriptionService.getSubscriptionPlanFromUser(user);
        return subscriptions == Subscriptions.PREMIUM;
    }

    public boolean hasBasicSubscription() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Subscriptions subscriptions = subscriptionService.getSubscriptionPlanFromUser(user);
        return subscriptions == Subscriptions.BASIC;
    }

}
