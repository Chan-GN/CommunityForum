package com.me.community.dto;


import com.me.community.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String name;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getName());
    }
}