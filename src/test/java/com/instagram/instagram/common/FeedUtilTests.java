package com.instagram.instagram.common;

import com.instagram.instagram.controllers.FeedController;
import com.instagram.instagram.model.*;
import com.instagram.instagram.repositories.FeedPostsRepository;
import com.instagram.instagram.repositories.PostRepository;
import com.instagram.instagram.repositories.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedUtilTests {
    private FeedUtil feedUtil;

    @Mock
    private FeedPostsRepository feedPostsRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Before
    public void setUp() {
        List<FeedPosts> feedPostsList = new ArrayList<>();
        when(feedPostsRepository.findByFeedId(any())).thenReturn(feedPostsList);
        FeedPosts feedPosts = new FeedPosts();
        Integer id = 5;
        doNothing().when(feedPostsRepository).deleteById(any());
        when(feedPostsRepository.save(any())).thenReturn(feedPosts);


        List<Post> posts = new ArrayList<>();
        when(postRepository.findByUserId(any())).thenReturn(posts);


        List<Subscription> subscriptions = new ArrayList<>();
        when(subscriptionRepository.findByFollowerId(any())).thenReturn(subscriptions);

        feedUtil = new FeedUtil(feedPostsRepository, subscriptionRepository, postRepository);
    }

    @Test
    public void testUpdateFeedByViewerId_simple_success() {
        // given
        Feed feed = new Feed();
        feed.setViewerId(5);
        Integer userId = 5;

        // when
        feedUtil.UpdateFeedByViewerId(feed, userId);

        // then
        verify(feedPostsRepository, times(1)).findByFeedId(feed.getId());
        verify(subscriptionRepository, times(1)).findByFollowerId(userId);
    }
}
