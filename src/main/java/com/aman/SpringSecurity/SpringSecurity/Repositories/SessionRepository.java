package com.aman.SpringSecurity.SpringSecurity.Repositories;

import com.aman.SpringSecurity.SpringSecurity.Entity.SessionEntity;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUser(Users user);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
