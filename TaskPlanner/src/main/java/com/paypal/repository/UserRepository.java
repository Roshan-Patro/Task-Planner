package com.paypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paypal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
