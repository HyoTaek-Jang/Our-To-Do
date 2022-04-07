package com.ourtodo.withme.global.security.domain;

import javax.persistence.Entity;

import com.ourtodo.withme.global.entity.BaseEntity;

import lombok.Builder;

@Entity
@Builder
public class RefreshToken extends BaseEntity {
}
