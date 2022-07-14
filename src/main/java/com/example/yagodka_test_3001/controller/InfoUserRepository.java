package com.example.yagodka_test_3001.controller;

import com.example.yagodka_test_3001.persist.InfoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoUserRepository extends JpaRepository<InfoUser, Long> {

    List<InfoUser> findByUserUsername(String username);

    List<InfoUser> findByGender(String gender);


}
