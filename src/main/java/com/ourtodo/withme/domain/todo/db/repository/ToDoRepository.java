package com.ourtodo.withme.domain.todo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ourtodo.withme.domain.todo.db.domain.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
	@Query(value = "SELECT IF(COUNT(ordering) > 0, MAX(ordering), 0) as lastOrder FROM to_do WHERE tag_id = :tag", nativeQuery = true)
	Long findLastOrderByTag(@Param("tag")Long tag);
}
