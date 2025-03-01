package com.aman.SpringSecurity.SpringSecurity.Repositories;

import com.aman.SpringSecurity.SpringSecurity.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
