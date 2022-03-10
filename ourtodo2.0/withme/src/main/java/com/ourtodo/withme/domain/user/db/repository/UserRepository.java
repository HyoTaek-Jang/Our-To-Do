package com.ourtodo.withme.domain.user.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourtodo.withme.domain.user.db.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
