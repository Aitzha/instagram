package com.instagram.instagram.controllers;

import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
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
public class UserControllerTests {
    private UserController userController;
    private SessionUtil sessionUtil;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SessionRepository sessionRepository;

    @Before
    public void setUp() {
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);
        User user = new User("Gabit", "123");
        user.setId(5);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        List<Session> sessions = new ArrayList<>();
        when(sessionRepository.findAll()).thenReturn(sessions);
        Session session = new Session();
        session.setUserId(5);
        when(sessionRepository.findByToken(any())).thenReturn(Optional.of(session));

        sessionUtil = new SessionUtil(sessionRepository);

        userController = new UserController(userRepository, sessionUtil);
    }

    @Test
    public void testGetAll_simple_success() throws IOException{
        // given
        Iterable<User> expectedUsers = new ArrayList<>();

        // when
        Iterable<User> actualUsers = userController.getAll();

        // then
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void testGet_simple_success() throws IOException {
        // given
        Integer id = 5;
        Optional<String> sessionToken = Optional.of("session-token");

        User expectedUser = new User("Gabit", "123");
        expectedUser.setId(id);

        EntityModel<User> expectedEntityModelUser = EntityModel.of(
                expectedUser,
                linkTo(methodOn(UserController.class).get(id, sessionToken)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("/"));

        // when
        EntityModel<User> actualEntityModelUser = userController.get(id, sessionToken);

        // then
        assertThat(actualEntityModelUser).isEqualTo(expectedEntityModelUser);

    }




}
