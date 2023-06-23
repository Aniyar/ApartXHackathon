package com.hackathon.apartxhackathon.repository;

import java.util.Optional;

import com.hackathon.apartxhackathon.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
