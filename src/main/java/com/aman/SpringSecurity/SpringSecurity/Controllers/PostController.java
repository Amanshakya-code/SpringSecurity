package com.aman.SpringSecurity.SpringSecurity.Controllers;

import com.aman.SpringSecurity.SpringSecurity.DTO.PostDTO;
import com.aman.SpringSecurity.SpringSecurity.Service.PostService;
import lombok.RequiredArgsConstructor;
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
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }
}
