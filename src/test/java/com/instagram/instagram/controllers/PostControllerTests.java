package com.instagram.instagram.controllers;

import com.instagram.instagram.model.Post;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.repositories.PostRepository;
import com.instagram.instagram.repositories.SessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostControllerTests {
    private PostController postController;

    @Mock
    private PostRepository postRepository;

    @Before
    public void setUp() {
        List<Post> posts = new ArrayList<>();
        when(postRepository.findAll()).thenReturn(posts);

        postController = new PostController(postRepository);
    }

    @Test
    public void testGetPosts_simple_success() {
        // given
        Iterable<Post> expectedPosts = new ArrayList<>();

        // when
        Iterable<Post> actualPosts = postController.getPosts();

        // then
        assertThat(expectedPosts).isEqualTo(actualPosts);
    }
}
