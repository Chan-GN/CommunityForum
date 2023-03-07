package com.me.community.dto;

import com.me.community.auth.Authority;
import com.me.community.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String name;
    private String password;
    private String nickname;
    private String introduce;

    public Member toUser(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .nickname(nickname)
                .introduce(introduce)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(name, password);
    }
}