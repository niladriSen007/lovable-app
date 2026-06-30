package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserEmail(String email);

    Optional<Session> findByRefreshToken(String refreshToken);
}
