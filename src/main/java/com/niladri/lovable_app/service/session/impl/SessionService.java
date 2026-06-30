package com.niladri.lovable_app.service.session.impl;

import com.niladri.lovable_app.entity.Session;
import com.niladri.lovable_app.repository.SessionRepository;
import com.niladri.lovable_app.service.session.ISessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService implements ISessionService {

    SessionRepository sessionRepository;

    int MAX_SESSIONS_PER_USER = 2;

    @Override
    public void generateNewSession(String email, String refreshToken) {
        List<Session> sessionsByUser = sessionRepository.findByUserEmail(email);
        if (sessionsByUser != null && sessionsByUser.size() == MAX_SESSIONS_PER_USER) {
            sessionsByUser.sort(Comparator.comparing(Session::getLastUsedAt));
            Session oldestSession = sessionsByUser.get(0);
            sessionRepository.delete(oldestSession);
        }
        Session build = Session.builder()
                .userEmail(email)
                .refreshToken(refreshToken)
                .lastUsedAt(Instant.now())
                .build();
        sessionRepository.save(build);
    }

    @Override
    public boolean validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new SessionAuthenticationException("Invalid session")
        );
        session.setLastUsedAt(Instant.now());
        sessionRepository.save(session);
        return session != null;
    }
}
