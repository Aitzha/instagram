package com.instagram.instagram.controllers;

import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.Subscription;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.SubscriptionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserController userController;


    @PostMapping
    public String subscribeToUser(@RequestParam(value = "followeeId") Integer followeeId,
                                  @CookieValue(value = "sessionToken") String sessionToken) {
        Iterable<Session> sessions = sessionRepository.findAll();
        Optional<Session> sessionOptional = Optional.empty();

        for (Session x : sessions) {
            if (x.getToken().equals(sessionToken)) {
                sessionOptional = Optional.of(x);
                break;
            }
        }

        if (sessionOptional.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "Unauthorized",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        Integer followerId = sessionOptional.get().getUserId();


        Subscription subscription = new Subscription(followeeId, followerId);
        subscriptionRepository.save(subscription);
        return "Subscribed";
    }

}
