package com.instagram.instagram.controllers;

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
    private SessionRepository sessionRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public String uploadImage(@RequestParam(value = "image") MultipartFile image,
                              @CookieValue(value = "sessionToken") Optional<String> sessionToken) {

        if(sessionToken.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "Unauthorized",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionRepository.findByToken(sessionToken.get());

//        Iterable<Session> sessions = sessionRepository.findAll();
//        for (Session x : sessions) {
//            if (x.getToken().equals(sessionToken)) {
//                sessionOptional = Optional.of(x);
//                break;
//            }
//        }

        if (sessionOptional.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "Session not found. Try to login",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        storageService.store(image);

        Post post = new Post(
                storageService.load(image.getOriginalFilename()).toString(),
                sessionOptional.get().getUserId());

        postRepository.save(post);

        return "You successfully uploaded";
    }

    @GetMapping
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

//    public Iterable<Post> getUserPosts(Integer id) {
//
//        List<Post> userPosts = postRepository.findByUserId(id);
//
////        Iterable<Post> posts = postRepository.findAll();
////        for(Post x : posts) {
////            if(x.getUserId().equals(id)) {
////                userPosts.add(x);
////            }
////        }
//
//        return userPosts;
//    }

}
