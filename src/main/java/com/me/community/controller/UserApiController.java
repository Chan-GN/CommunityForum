package com.me.community.controller;

import com.me.community.service.UserService;
import com.me.community.auth.SecurityUtil;
import com.me.community.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;

    /**
     * 유저 저장
     */
    @PostMapping("/users")
    public ResponseEntity<Long> saveUser(@RequestBody UserDto dto) { // api 통신 시, Entity 가 아닌 DTO 로 주고받기 !!!
        User user = new User();
        user.setName(dto.getName());
        user.setNickname(dto.getNickname());

        Long userId = userService.join(user);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @GetMapping("/me")
    public ResponseEntity<User> findUserInfoById() {
        return ResponseEntity.ok(userService.findUserInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findUserInfoByEmail(@PathVariable String username) {
        return ResponseEntity.ok(userService.findUserInfoByName(username));
    }

    // 유저 DTO
    @Data
    static class UserDto {
        private String name;
        private String nickname;
    }
}

