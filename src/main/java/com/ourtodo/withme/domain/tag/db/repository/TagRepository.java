package com.ourtodo.withme.domain.tag.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ourtodo.withme.domain.tag.db.domain.Tag;
import com.ourtodo.withme.domain.user.db.domain.Member;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findAllByMember(Member memberById);

	Tag findByMemberAndName(Member member, String commonTag);

	@Query("SELECT distinct t FROM Tag t JOIN FETCH t.toDoList WHERE t.member = :member")
	List<Tag> findAllWithTodoByMember(@Param("member") Member memberById);
}
