package com.thesun4sky.springblog.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesun4sky.springblog.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
