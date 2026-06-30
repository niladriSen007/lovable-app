package com.niladri.lovable_app.security;

import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AppUserDetailsService implements UserDetailsService {

    UserRepository  userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Find the user in the database
        Optional<UserEntity> user = userRepository.findByEmail(username);

        //If user does not exists then throw UsernameNotFound Exception
        if (user.isEmpty()) {
            log.info("No user found with username: " + username);
            throw new UsernameNotFoundException("User with - " + username + " does not exist");
        }
        log.info("User with - " + username + " found from database");

        return new UserInfoService(
                user.get().getId(),
                user.get().getEmail(),
                user.get().getName(),
                user.get().getPassword(),
                List.of() // Replace with actual authorities if available
        );
    }

    public UserInfoService loadUserByUserId(Long id) {
        //Find the user in the database
        Optional<UserEntity> user = userRepository.findById(Long.valueOf(id));

        //If user does not exists then throw UsernameNotFound Exception
        if (user.isEmpty()) {
            log.info("No user found with id: " + id);
            throw new UsernameNotFoundException("User with - " + id + " does not exist");
        }
        log.info("User with - " + id + " found from database");


        return new UserInfoService(
                user.get().getId(),
                user.get().getEmail(),
                user.get().getName(),
                user.get().getPassword(),
                List.of() // Replace with actual authorities if available
        );
    }
}
