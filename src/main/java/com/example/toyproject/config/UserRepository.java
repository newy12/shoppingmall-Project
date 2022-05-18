package com.example.toyproject.config;

import com.example.toyproject.entity.Member;
import com.example.toyproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findAllByEmail(String email);
}
