package com.me.community.repository;

import com.me.community.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String username);
    boolean existsByName(String email);
}
