package com.instagram.instagram.common;

import com.instagram.instagram.controllers.UserController;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
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
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionUtilTests {
    private SessionUtil sessionUtil;

    @Mock
    private SessionRepository sessionRepository;

    @Before
    public void setUp() {
        when(sessionRepository.findByToken(any())).thenReturn(Optional.empty());

        sessionUtil = new SessionUtil(sessionRepository);
    }

    @Test
    public void testFindSession_simple_success() {
        // given
        Optional<String> sessionToken = Optional.of("session-token");

        Session expectedSession = new Session();
        expectedSession.setToken("session-token");

        // when
        Session actualSession = sessionUtil.findSession(sessionToken);

        // then
        assertThat(expectedSession).isEqualTo(actualSession);
    }
}
