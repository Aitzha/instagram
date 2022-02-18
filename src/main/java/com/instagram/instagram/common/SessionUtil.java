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
    private SessionRepository sessionRepository;

    @Autowired
    public SessionUtil(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Finds session instance with given parameters.
     *
     * @param sessionToken is empty optional if user is not logged in,
     *                     and is present otherwise
     * @return returns session entity if session found otherwise throws exception
     * @throws HttpClientErrorException if session is not found
     */
    public Session findSession(Optional<String> sessionToken) {
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

        return sessionOptional.get();
    }
}
