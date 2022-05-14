package com.ourtodo.withme.domain.tag.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourtodo.withme.domain.tag.db.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
