package com.instagram.instagram.common;

import com.instagram.instagram.model.Session;
import com.instagram.instagram.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Component
public class SessionUtil {
    @Autowired
    private SessionRepository sessionRepository;
    public Optional<Session> findSession(Optional<String> sessionToken) {
        if(sessionToken.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "Unauthorized",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        Optional<Session> sessionOptional = Optional.empty();
        sessionOptional = sessionRepository.findByToken(sessionToken.get());

        if (sessionOptional.isEmpty()) {
            throw HttpClientErrorException.create(
                    HttpStatus.FORBIDDEN,
                    "Session not found. Try to login",
                    HttpHeaders.EMPTY,
                    null,
                    null);
        }

        return sessionOptional;
    }
}
