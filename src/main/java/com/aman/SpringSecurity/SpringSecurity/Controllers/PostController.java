package com.aman.SpringSecurity.SpringSecurity.Controllers;

import com.aman.SpringSecurity.SpringSecurity.DTO.PostDTO;
import com.aman.SpringSecurity.SpringSecurity.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Secured("ROLE_USER")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    //@PreAuthorize("hasRole('USER') AND hasAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }
}
