package com.example.subscriptionmodule.subscriptionapigateway.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.subscriptionmodule.subscriptionapigateway.Models.UserDetailsFromDb;

public interface UserDetailsDbRepository extends CrudRepository<UserDetailsFromDb,Integer>{
    Optional<UserDetailsFromDb> findByUsername(String username);
}
