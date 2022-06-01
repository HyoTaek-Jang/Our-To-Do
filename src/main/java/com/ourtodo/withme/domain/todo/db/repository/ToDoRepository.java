package com.ourtodo.withme.domain.todo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.todo.db.domain.ToDo;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
	@Query(value = "SELECT CASE WHEN COUNT(t.ordering) > 0 then MAX(t.ordering) else -1 end FROM ToDo t WHERE t.tag = :tag")
	Long findLastOrderByTag(@Param("tag") Tag tag);
}
