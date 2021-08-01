package com.deeplify.tutorial.oauthlogin.api.repository.user;

import com.deeplify.tutorial.oauthlogin.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
