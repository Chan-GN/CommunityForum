package com.me.community.service;

import com.me.community.entity.User;
import com.me.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class UserService {
    private final UserRepository userRepository;
    /**
     * 회원가입 ( 학사 API 활용 시, 추가 정보 입력 용도 )
     */
    @Transactional // 필요 시 쓰기 전용
    public Long join(User user) {
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public User findUserInfoById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public User findUserInfoByName(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
