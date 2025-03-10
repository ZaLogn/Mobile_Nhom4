package com.example.API.repository;

import com.example.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm kiếm người dùng theo email
    User findByEmail(String email);
}