package com.example.dao;

import com.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.email=?1")
    Optional<Person> findByEmail(String email);

}
