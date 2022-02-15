package com.instagram.instagram.controllers;

import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.Subscription;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.SubscriptionRepository;
import com.instagram.instagram.repositories.UserRepository;
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
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionControllerTests {
    private SubscriptionController subscriptionController;
    private SessionUtil sessionUtil;

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Before
    public void setUp() {
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);
        Session session = new Session();
        session.setUserId(5);
        when(sessionRepository.findByToken(any())).thenReturn(Optional.of(session));


        List<Subscription> subscriptions = new ArrayList<>();
        when(subscriptionRepository.findByFollowerId(any())).thenReturn(subscriptions);

        sessionUtil = new SessionUtil(sessionRepository);

        subscriptionController = new SubscriptionController(subscriptionRepository, sessionUtil);
    }

    @Test
    public void testGetSubscriptions_simple_success() {
        // given
        Iterable<Subscription> expectedSubscriptions = new ArrayList<>();
        Optional<String> sessionToken = Optional.of("session-token");

        // when
        Iterable<Subscription> actualSubscriptions = subscriptionController.getSubscriptions(sessionToken);

        // then
        assertThat(actualSubscriptions).isEqualTo(expectedSubscriptions);
    }
}
