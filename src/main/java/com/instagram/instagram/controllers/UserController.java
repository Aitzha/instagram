package com.instagram.instagram.controllers;

import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private SessionRepository sessionRepository;

    @PostMapping
    @ResponseBody
    public RedirectView post(@RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             Model model) throws IOException {

        User user = new User(username, password);
        userRepository.save(user);

        model.addAttribute("message", "Account was created. Try to login");
        return new RedirectView("/");
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
                                 @CookieValue(value = "sessionToken") String sessionToken) throws IOException {

        Iterable<Session> sessions = sessionRepository.findAll();
        Session session = new Session();
        boolean sessionFound = false;

        for(Session x : sessions) {
            if(x.getToken().equals(sessionToken)) {
                sessionFound = true;
                session = x;
                break;
            }
        }

        if(sessionFound) {
            Optional<User> user = userRepository.findById(session.getUserId());
            if(user.isPresent()) {
                return EntityModel.of(user.get(),
                                      linkTo(methodOn(UserController.class).get(id, sessionToken)).withSelfRel(),
                                      linkTo(methodOn(UserController.class).getAll()).withRel("/"));
            }
        }

        return EntityModel.of(new User());
    }

    @DeleteMapping
    public @ResponseBody
    String deleteAll() {
        userRepository.deleteAll();

        return "Deleted!";
    }
}
