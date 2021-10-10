package ru.savimar.storedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.savimar.storedemo.entity.User;


import java.util.List;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    List<User> findAll();


}