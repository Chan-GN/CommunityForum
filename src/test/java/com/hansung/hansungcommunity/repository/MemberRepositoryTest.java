package com.hansung.hansungcommunity.repository;

import com.me.community.entity.Member;
import com.me.community.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Member member = createUser();

        Member savedMember = memberRepository.save(member);

        assertEquals("OCG", savedMember.getName());
    }

    @Test
    public void findById() {
        Member member = createUser();

        memberRepository.save(member);

        Optional<Member> target = memberRepository.findById(member.getId());

        assertEquals("OCG", target.get().getName());
    }

    private Member createUser() {
        Member member = new Member();
        member.setId(1L);
        member.setName("OCG");

        return member;
    }
}