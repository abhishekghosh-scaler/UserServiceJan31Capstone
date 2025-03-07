package com.scaler.userservicejan31capstone.repositories;

import com.scaler.userservicejan31capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    User save(User user);

    Optional<User> findByEmail(String email);
}
