package com.me.community.auth;

import com.me.community.dto.MemberRequestDto;
import com.me.community.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuthService authService;

    public DataLoader(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        MemberRequestDto memberRequestDto = new MemberRequestDto
                ("찬근",
                "1234",
                "찬근",
                "안녕하세요 찬근입니다.");

        authService.signup(memberRequestDto);
    }
}
