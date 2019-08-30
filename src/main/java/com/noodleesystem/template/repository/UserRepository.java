package com.noodleesystem.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.noodleesystem.template.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ }
