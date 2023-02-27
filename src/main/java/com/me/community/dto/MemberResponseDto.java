package com.me.community.dto;


import com.me.community.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String name;

    public static MemberResponseDto of(User user) {
        return new MemberResponseDto(user.getName());
    }
}