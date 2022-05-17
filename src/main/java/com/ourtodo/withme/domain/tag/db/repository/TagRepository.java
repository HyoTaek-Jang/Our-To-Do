package com.ourtodo.withme.domain.tag.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.user.db.domain.Member;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findAllByMember(Member memberById);
}
