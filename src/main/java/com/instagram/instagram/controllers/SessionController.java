package com.instagram.instagram.controllers;

import com.instagram.instagram.model.Session;
import com.instagram.instagram.model.User;
import com.instagram.instagram.repositories.SessionRepository;
import com.instagram.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/session")
public class SessionController {
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @PostMapping
    public RedirectView post(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             HttpServletResponse response) {


        Iterable<User> usernames = userRepository.findByUsername(username);

        for(User x : usernames) {
            if(x.getUsername().equals(username) && x.getPassword().equals(password)) {
                Session session = new Session();
                session.setUserId(x.getId());
                sessionRepository.save(session);

                Cookie cookie = new Cookie("sessionToken", session.getToken());
                response.addCookie(cookie);
                return new RedirectView("/user/" + x.getId());
            }
        }

//        Iterable<User> users = userRepository.findAll();
//        for(User x : users) {
//            if(x.getUsername().equals(username) && x.getPassword().equals(password)) {
//                Session session = new Session();
//                session.setUserId(x.getId());
//                sessionRepository.save(session);
//
//                Cookie cookie = new Cookie("sessionToken", session.getToken());
//                response.addCookie(cookie);
//                return new RedirectView("/user/" + x.getId());
//            }
//        }

        return new RedirectView("/");
    }

    @GetMapping("/all")
    @ResponseBody
    public Iterable<Session> getAll() {
        return sessionRepository.findAll();
    }

    @DeleteMapping
    @ResponseBody
    public String deleteAll() {
        sessionRepository.deleteAll();
        return "Deleted";
    }


}
