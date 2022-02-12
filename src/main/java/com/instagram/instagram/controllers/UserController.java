package com.instagram.instagram.controllers;

import com.instagram.instagram.common.SessionUtil;
import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionUtil sessionUtil;

    @PostMapping
    @ResponseBody
    public String post(@RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password) throws IOException {

        User user = new User(username, password);
        userRepository.save(user);

        return "Account was created. Try to login";
    }

//    @GetMapping
//    public CollectionModel<EntityModel<User>> getAll() {
//        List<EntityModel<User>> users = userRepository.findAll().stream().map(employee -> EntityModel.of(employee)).collect(Collectors.toList());
//
//        return CollectionModel.of(users);
//    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id:.+}")
    public EntityModel<User> get(@PathVariable Integer id,
                                 @CookieValue(value = "sessionToken") Optional<String> sessionToken) throws IOException {

        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionUtil.findSession(sessionToken);

        Optional<User> userOptional = userRepository.findById(sessionOptional.get().getUserId());

        if (userOptional.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "User not found",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        return EntityModel.of(userOptional.get(),
                linkTo(methodOn(UserController.class).get(id, sessionToken)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("/"));
    }

    @DeleteMapping
    public @ResponseBody
    String deleteAll() {
        userRepository.deleteAll();

        return "Deleted!";
    }
}
