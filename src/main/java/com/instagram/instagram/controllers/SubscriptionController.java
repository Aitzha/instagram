package com.instagram.instagram.controllers;

import com.instagram.instagram.common.SessionUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private SubscriptionRepository subscriptionRepository;
    private SessionUtil sessionUtil;

    @Autowired
    SubscriptionController(SubscriptionRepository subscriptionRepository, SessionUtil sessionUtil) {
        this.subscriptionRepository = subscriptionRepository;
        this.sessionUtil = sessionUtil;
    }



    @PostMapping
    public String subscribeToUser(@RequestParam(value = "followeeId") Integer followeeId,
                                  @CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionUtil.findSession(sessionToken);

        Integer followerId = sessionOptional.get().getUserId();

        Subscription subscription = new Subscription(followeeId, followerId);
        subscriptionRepository.save(subscription);
        return "Subscribed";
    }

    @GetMapping
    public Iterable<Subscription> getSubscriptions(@CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionUtil.findSession(sessionToken);

        Integer followerId = sessionOptional.get().getUserId();

        List<Subscription> userSubscriptions = subscriptionRepository.findByFollowerId(followerId);

        return userSubscriptions;
    }

//    public Iterable<Subscription> getSubscriptionsById(Integer followerId) {
//        Iterable<Subscription> allSubscriptions = subscriptionRepository.findAll();
//        List<Subscription> userSubscriptions = new ArrayList<Subscription>();
//
//        for (Subscription x : allSubscriptions) {
//            if(x.getFollowerId().equals(followerId)) {
//                userSubscriptions.add(x);
//            }
//        }
//
//        return userSubscriptions;
//    }
}
