package com.instagram.instagram.controllers;

import com.instagram.instagram.common.FeedUtil;
import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.*;
import com.instagram.instagram.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedControllerTests {
    private FeedController feedController;
    private SessionUtil sessionUtil;
    private FeedUtil feedUtil;

    @Mock
    private FeedRepository feedRepository;
    @Mock
    private FeedPostsRepository feedPostsRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Before
    public void setUp() {
        Session session = new Session();
        session.setUserId(5);
        when(sessionRepository.findByToken(any())).thenReturn(Optional.of(session));


        Feed feed = new Feed(5);
        when(feedRepository.findByViewerId(any())).thenReturn(Optional.of(feed));
        when(feedRepository.save(any())).thenReturn(feed);


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


        sessionUtil = new SessionUtil(sessionRepository);

        feedUtil = new FeedUtil(feedPostsRepository, subscriptionRepository, postRepository);

        feedController = new FeedController(sessionUtil, feedUtil, feedRepository, feedPostsRepository);
    }

    @Test
    public void testGetFeed_simple_success() {
        // given
        Optional<String> sessionToken = Optional.of("session-token");
        Iterable<FeedPosts> expectedFeedPosts = new ArrayList<>();

        // when
        Iterable<FeedPosts> actualFeedPosts = feedController.getFeed(sessionToken);

        // then
        assertThat(actualFeedPosts).isEqualTo(expectedFeedPosts);

    }
}
