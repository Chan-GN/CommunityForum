package com.me.community.service;

import com.me.community.dto.MemberResponseDto;
import com.me.community.entity.Member;
import com.me.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class MemberService {
    private final MemberRepository memberRepository;
    /**
     * 회원가입 ( 학사 API 활용 시, 추가 정보 입력 용도 )
     */
    @Transactional // 필요 시 쓰기 전용
    public Long join(Member member) {
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public MemberResponseDto findMemberInfoById(Long memberId) {
        return MemberResponseDto
                .of(memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다.")));
    }

    public MemberResponseDto findMemberInfoByName(String name) {
        return MemberResponseDto
                .of(memberRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다.")));
    }
}
