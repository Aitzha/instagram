package com.instagram.instagram.controllers;

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
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionController sessionController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserController userController;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedPostsRepository feedPostsRepository;
    @Autowired
    private FeedPostsController feedPostsController;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionController subscriptionController;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostController postController;





    @GetMapping
    public Iterable<FeedPosts> getFeed(@CookieValue(value = "sessionToken") Optional<String> sessionToken) {
        //finding session
//        if(sessionToken.isEmpty()) {
//            throw HttpClientErrorException.create(
//                    HttpStatus.FORBIDDEN,
//                    "Unauthorized",
//                    HttpHeaders.EMPTY,
//                    null,
//                    null);
//        }
//
//        Optional<Session> sessionOptional = Optional.empty();
//        sessionOptional = sessionRepository.findByToken(sessionToken.get());
//
//        if (sessionOptional.isEmpty()) {
//            throw HttpClientErrorException.create(
//                    HttpStatus.FORBIDDEN,
//                    "Session not found. Try to login",
//                    HttpHeaders.EMPTY,
//                    null,
//                    null);
//        }

        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionController.findSession(sessionToken);

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
        UpdateFeedByViewerId(feed.get(), viewerId);

//        List<FeedPosts> userFeedPosts = feedPostsRepository.findByFeedId(feed.get().getId());
//        Iterable<FeedPosts> feedPosts = feedPostsRepository.findAll();
//        for (FeedPosts x : feedPosts) {
//            if (x.getFeedId().equals(feed.get().getId())) {
//                userFeedPosts.add(x);
//            }
//        }

        return feedPostsRepository.findByFeedId(feed.get().getId());
    }

    @Scheduled(fixedRate = 5000)
    private void UpdateFeeds() {
        Iterable<User> users = userRepository.findAll();

        for(User user : users) {
            //seeking feed or creating new feed
            Optional<Feed> feed = Optional.empty();
            feed = feedRepository.findByViewerId(user.getId());

            if (feed.isEmpty()) {
                //creating new feed
                Feed newFeed = new Feed(user.getId());
                feedRepository.save(newFeed);
                feed = Optional.of(newFeed);
            }

            //updating feed
            UpdateFeedByViewerId(feed.get(), user.getId());
        }
    }

    private void UpdateFeedByViewerId(Feed feed, Integer viewerId) {
        //firstly we delete all feedPosts of this feed
        List<FeedPosts> feedPostsToDelete = feedPostsRepository.findByFeedId(feed.getId());
        for(FeedPosts x : feedPostsToDelete) {
            feedPostsRepository.deleteById(x.getId());
        }
        //filling feedPosts with new posts
        //find all subscriptions of user and their posts and create FeedPosts
        Iterable<Subscription> subscriptions = subscriptionRepository.findByFollowerId(viewerId);
        for (Subscription subscription : subscriptions) {
            Integer followeeId = subscription.getFolloweeId();
            Iterable<Post> posts = postRepository.findByUserId(followeeId);
            for (Post post : posts) {
                FeedPosts feedPosts = new FeedPosts(feed.getId(), post.getId());
                feedPostsRepository.save(feedPosts);
            }
        }
    }

}
