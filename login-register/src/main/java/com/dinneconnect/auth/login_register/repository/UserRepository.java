package com.dinneconnect.auth.login_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinneconnect.auth.login_register.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
