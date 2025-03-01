package com.aman.SpringSecurity.SpringSecurity.Repositories;

import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
}
