package com.ourtodo.withme.domain.user.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourtodo.withme.domain.user.db.domain.MailCertification;

@Repository
public interface MailCertificationRepository extends JpaRepository<MailCertification, Long> {
	Optional<MailCertification> findByEmail(String email);
}
