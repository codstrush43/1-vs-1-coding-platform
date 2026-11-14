package com.plateform.multiplayer_platform.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plateform.multiplayer_platform.Entity.User;

import jakarta.persistence.LockModeType;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findById(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByIdWithLock(@Param("userId") Long userId);
}