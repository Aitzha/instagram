package com.instagram.instagram.controllers;

import com.instagram.instagram.common.FeedUtil;
import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.*;
import com.instagram.instagram.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feed")
public class FeedController {
    private FeedRepository feedRepository;
    private FeedPostsRepository feedPostsRepository;
    private SessionUtil sessionUtil;
    private FeedUtil feedUtil;

    @Autowired
    FeedController(SessionUtil sessionUtil,
                   FeedUtil feedUtil,
                   FeedRepository feedRepository,
                   FeedPostsRepository feedPostsRepository) {
        this.feedUtil = feedUtil;
        this.sessionUtil = sessionUtil;
        this.feedRepository = feedRepository;
        this.feedPostsRepository = feedPostsRepository;
    }

    @GetMapping
    public Iterable<FeedPosts> getFeed(@CookieValue(value = "sessionToken") Optional<String> sessionToken) {

        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionUtil.findSession(sessionToken);

        Integer viewerId = sessionOptional.get().getUserId();

        //seeking feed or creating new feed
        Optional<Feed> feed = Optional.empty();
        feed = feedRepository.findByViewerId(viewerId);

        if (feed.isEmpty()) {
            //creating new feed
            Feed newFeed = new Feed(viewerId);
            feedRepository.save(newFeed);
            feed = Optional.of(newFeed);
        }

        //updating feed
        feedUtil.UpdateFeedByViewerId(feed.get(), viewerId);

        return feedPostsRepository.findByFeedId(feed.get().getId());
    }
}
