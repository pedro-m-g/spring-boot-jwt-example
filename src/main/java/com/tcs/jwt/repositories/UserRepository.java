package com.tcs.jwt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.jwt.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("SELECT u FROM User u WHERE u.username = :username")
  Optional<User> findByUsername(@Param("username") String username);

}
