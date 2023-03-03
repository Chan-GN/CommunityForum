package com.me.community.controller;

import com.me.community.dto.MemberResponseDto;
import com.me.community.entity.Member;
import com.me.community.service.MemberService;
import com.me.community.auth.SecurityUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 유저 저장
     */
    @PostMapping("/users")
    public ResponseEntity<Long> saveUser(@RequestBody MemberDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
        Member member = new Member();
        member.setName(dto.getName());
        member.setNickname(dto.getNickname());

        Long userId = memberService.join(member);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findUserInfoById() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<MemberResponseDto> findUserInfoByEmail(@PathVariable String username) {
        return ResponseEntity.ok(memberService.findMemberInfoByName(username));
    }

    // 유저 DTO
    @Data
    static class MemberDto {
        private String name;
        private String nickname;
    }
}

