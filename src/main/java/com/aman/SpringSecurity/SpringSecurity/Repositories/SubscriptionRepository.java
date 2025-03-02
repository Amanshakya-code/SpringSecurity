package com.aman.SpringSecurity.SpringSecurity.Repositories;

import com.aman.SpringSecurity.SpringSecurity.Entity.SessionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.SubscriptionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    SubscriptionEntity findByUsers(Users users);
}
