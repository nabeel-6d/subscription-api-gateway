package com.example.subscriptionmodule.subscriptionapigateway;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserDetailsDbRepository extends CrudRepository<UserDetailsFromDb,Integer>{
    Optional<UserDetailsFromDb> findByUsername(String username);
}
