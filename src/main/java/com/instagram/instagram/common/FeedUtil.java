package com.instagram.instagram.common;

import com.instagram.instagram.model.Feed;
import com.instagram.instagram.model.FeedPosts;
import com.instagram.instagram.model.Post;
import com.instagram.instagram.model.Subscription;
import com.instagram.instagram.repositories.FeedPostsRepository;
import com.instagram.instagram.repositories.PostRepository;
import com.instagram.instagram.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedUtil {
    private FeedPostsRepository feedPostsRepository;
    private SubscriptionRepository subscriptionRepository;
    private PostRepository postRepository;

    @Autowired
    public FeedUtil(FeedPostsRepository feedPostsRepository,
             SubscriptionRepository subscriptionRepository,
             PostRepository postRepository) {
        this.feedPostsRepository = feedPostsRepository;
        this.postRepository = postRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Updated the Feed by given parameters
     *
     * @param feed entity of the feed that will be updated
     * @param viewerId is used to find recommended post for user with same id
     */
    public void UpdateFeedByViewerId(Feed feed, Integer viewerId) {
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
