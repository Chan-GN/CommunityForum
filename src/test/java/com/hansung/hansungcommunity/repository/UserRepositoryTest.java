package com.hansung.hansungcommunity.repository;

import com.me.community.entity.User;
import com.me.community.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() {
        User user = createUser();

        User savedUser = userRepository.save(user);

        assertEquals("OCG", savedUser.getName());
    }

    @Test
    public void findById() {
        User user = createUser();

        userRepository.save(user);

        Optional<User> target = userRepository.findById(user.getId());

        assertEquals("OCG", target.get().getName());
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("OCG");

        return user;
    }
}