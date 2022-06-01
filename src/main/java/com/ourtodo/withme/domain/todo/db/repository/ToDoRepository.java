package com.ourtodo.withme.domain.todo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ourtodo.withme.domain.todo.db.domain.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
