package com.niladri.lovable_app.repository;

import com.niladri.lovable_app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
