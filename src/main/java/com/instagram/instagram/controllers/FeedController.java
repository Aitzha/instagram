package com.instagram.instagram.controllers;

import com.instagram.instagram.model.*;
import com.instagram.instagram.repositories.FeedPostsRepository;
import com.instagram.instagram.repositories.FeedRepository;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private PostController postController;
    @Autowired
    private FeedPostsRepository feedPostsRepository;

    @GetMapping
    public Iterable<FeedPosts> getFeed(@CookieValue(value = "sessionToken") String sessionToken) {
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

        Iterable<Feed> feeds = feedRepository.findAll();
        Optional<Feed> feed = Optional.empty();
        Integer viewerId = sessionOptional.get().getUserId();

        for (Feed x : feeds) {
            if(x.getViewerId().equals(viewerId)) {
                feed = Optional.of(x);
            }
        }

        if (feed.isEmpty()) {
            //creating new feed
            Feed newFeed = new Feed(viewerId);
            feedRepository.save(newFeed);
            feed = Optional.of(newFeed);
        }

        //find all subscriptions of user and their posts and create FeedPosts
        Iterable<Subscription> subscriptions = subscriptionController.getSubscriptionsById(viewerId);
        for (Subscription subscription : subscriptions) {
            Integer followeeId = subscription.getFolloweeId();
            Iterable<Post> posts = postController.getUserPosts(followeeId);
            for (Post post : posts) {
                FeedPosts feedPosts = new FeedPosts(feed.get().getId(), post.getId());
                feedPostsRepository.save(feedPosts);
            }
        }


        Iterable<FeedPosts> feedPosts = feedPostsRepository.findAll();
        List<FeedPosts> userFeedPosts = new ArrayList<FeedPosts>();

        for (FeedPosts x : feedPosts) {
            if (x.getFeedId().equals(feed.get().getId())) {
                userFeedPosts.add(x);
            }
        }

        return userFeedPosts;
    }


}
