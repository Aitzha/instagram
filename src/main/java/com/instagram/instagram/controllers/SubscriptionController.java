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

    /**
     * Creates new subscription in database table with given params
     *
     * @param followeeId id of the user the current user wants to follow
     * @param sessionToken is empty optional if user is not logged in,
     *                     and is present otherwise
     * @return returns created subscription
     */
    @PostMapping
    public Subscription subscribeToUser(@RequestParam(value = "followeeId") Integer followeeId,
                                  @CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        Session session;
        session = sessionUtil.findSession(sessionToken);

        Integer followerId = session.getUserId();

        Subscription subscription = new Subscription(followeeId, followerId);
        subscriptionRepository.save(subscription);
        return subscription;
    }

    /**
     * Finds all the subscriptions of this user
     *
     * @param sessionToken is empty optional if user is not logged in,
     *                     and is present otherwise
     *
     * @return returns array list of user's subscriptions
     */
    @GetMapping
    public Iterable<Subscription> getSubscriptions(@CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        Session session;
        session = sessionUtil.findSession(sessionToken);

        Integer followerId = session.getUserId();

        List<Subscription> userSubscriptions = subscriptionRepository.findByFollowerId(followerId);

        return userSubscriptions;
    }
}
