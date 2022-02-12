package com.instagram.instagram.cron;

import com.instagram.instagram.common.FeedUtil;
import com.instagram.instagram.model.Feed;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.FeedRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FeedCron {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedUtil feedUtil;


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
            feedUtil.UpdateFeedByViewerId(feed.get(), user.getId());
        }
    }
}
