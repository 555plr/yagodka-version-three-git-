package com.example.yagodka_test_3001.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedUserRepository extends JpaRepository<LikedUser, Long> {
    List<LikedUser> findByUserUsername(String username);
}
