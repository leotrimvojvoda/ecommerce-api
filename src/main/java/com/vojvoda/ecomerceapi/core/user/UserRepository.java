package com.vojvoda.ecomerceapi.core.user;

import com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String username);

  @Query("SELECT new com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser"
          + "(u.id, u.firstName, u.lastName, u.email) FROM User u WHERE u.id = ?1 AND u.deletedAt IS NULL")
  ViewUser findUserById(long id);

  @Query("SELECT new com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser"
          + "(u.id, u.firstName, u.lastName, u.email) FROM User u WHERE u.email = ?1 AND u.deletedAt IS NULL")
  ViewUser findUserByEmail(String email);

  @Query("SELECT new com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser"
          + "(u.id, u.firstName, u.lastName, u.email) FROM User u WHERE u.deletedAt IS NULL")
  List<ViewUser> findAllUsers();

  @Modifying
  @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = ?1")
  void deleteUserById(Long id);

  @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.deletedAt IS NULL")
  Optional<User> findById(@NonNull Integer id);

}