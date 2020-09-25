package com.thoughtworks.lkn.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.lkn.quiz.controller.PersonalInformationController;
import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.repository.UserRepository;
import com.thoughtworks.lkn.quiz.service.PersonalInformationService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonalInformationController personalInformationController;

    @Autowired
    PersonalInformationService personalInformationService;

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
    void should_return_user_and_ok_when_get_user_by_id() throws Exception {
        Long id = 1L;
        UserDto userDto = UserDto.builder().id(id).build();

        mockMvc.perform(get("/users/" + id))
                .andExpect(jsonPath("$.id", is(userDto.getId().intValue())))
                .andExpect(status().isOk());
        verify(personalInformationService, times(1)).findUserById(id);
    }

    @Test
    void should_return_educations_and_ok_when_get_educations_by_user_id() throws Exception {
        Long id = 1L;
        List<EducationDto> educationDtoList = new ArrayList<>();
        educationDtoList.add(EducationDto.builder().userId(id).title("edu1").build());
        educationDtoList.add(EducationDto.builder().userId(id).title("edu2").build());

        mockMvc.perform(get("/users/" + id + "/educations"))
                .andExpect(jsonPath("$", hasSize(educationDtoList.size())))
                .andExpect(jsonPath("$[0].title", is("edu1")))
                .andExpect(jsonPath("$[1].title", is("edu2")))
                .andExpect(status().isOk());
        verify(personalInformationService, times(1)).findUserEducationsById(id);
    }
}
