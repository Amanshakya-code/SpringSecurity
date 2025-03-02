package com.aman.SpringSecurity.SpringSecurity.Utils;

import com.aman.SpringSecurity.SpringSecurity.DTO.PostDTO;
import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import com.aman.SpringSecurity.SpringSecurity.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {

    private  final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }

}
