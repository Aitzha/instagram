package com.instagram.instagram.controllers;

import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.Post;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.repositories.PostRepository;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import com.instagram.instagram.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private StorageService storageService;
    private PostRepository postRepository;
    @Autowired
    private SessionUtil sessionUtil;

    PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Creates a post for given image
     *
     * @param image that will be saved in bd
     * @param sessionToken is empty optional if user is not logged in,
     *                     and is present otherwise
     * @return returns message of successful operation
     */
    @PostMapping
    public String uploadImage(@RequestParam(value = "image") MultipartFile image,
                              @CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        Session session;
        session = sessionUtil.findSession(sessionToken);

        storageService.store(image);

        Post post = new Post(
                storageService.load(image.getOriginalFilename()).toString(),
                session.getUserId());

        postRepository.save(post);

        return "You successfully uploaded";
    }

    /**
     * Finds all posts
     *
     * @return returns Iterable list of posts
     */
    @GetMapping
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

}
