package com.instagram.instagram.controllers;

import com.instagram.instagram.model.Session;
import com.instagram.instagram.repositories.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionControllerTests {
    private SessionController sessionController;

    @Mock
    private SessionRepository sessionRepository;

    @Before
    public void setUp() {
        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);

        sessionController = new SessionController(sessionRepository);
    }

    @Test
    public void testGetAll_simple_success() {
        // given
        Iterable<Session> expectedSessions = new ArrayList<>();

        // when
        Iterable<Session> actualSessions = sessionController.getAll();

        // then
        assertThat(actualSessions).isEqualTo(expectedSessions);
    }


}
