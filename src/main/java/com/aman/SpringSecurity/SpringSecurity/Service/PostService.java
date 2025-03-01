package com.aman.SpringSecurity.SpringSecurity.Service;

import com.aman.SpringSecurity.SpringSecurity.DTO.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
