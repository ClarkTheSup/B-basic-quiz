package com.thoughtworks.lkn.quiz.repository;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L).name("Jack").build();
        userRepository.save(user);
        userRepository.saveEducation(Education.builder().userId(user.getId()).build());
        userRepository.saveEducation(Education.builder().userId(user.getId()).build());
        userRepository.saveEducation(Education.builder().userId(user.getId()).build());
    }

    @Test
    void should_return_user_when_find_user_by_id() {
        User user = userRepository.findUserById(1L);
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Jack");
    }
}