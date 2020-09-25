package com.thoughtworks.lkn.quiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.service.PersonalInformationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

@WebMvcTest(PersonalInformationController.class)
class PersonalInformationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PersonalInformationService personalInformationService;

    @Test
    void should_return_user_and_ok_when_get_user_by_id() throws Exception {
        Long id = 1L;
        UserDto userDto = UserDto.builder().id(id).build();
        when(personalInformationService.findUserById(anyLong())).thenReturn(userDto);

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
        when(personalInformationService.findUserEducationsById(anyLong())).thenReturn(educationDtoList);

        mockMvc.perform(get("/users/" + id + "/educations"))
                .andExpect(jsonPath("$", hasSize(educationDtoList.size())))
                .andExpect(jsonPath("$[0].title", is("edu1")))
                .andExpect(jsonPath("$[1].title", is("edu2")))
                .andExpect(status().isOk());
        verify(personalInformationService, times(1)).findUserEducationsById(id);
    }

    @Test
    void should_create_user_when_post_users() throws Exception {
        Long id = 1L;
        UserDto userDto = UserDto.builder()
                .id(id).name("Jackson").age(19L).avatar("url:888888").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJson))
                .andExpect(status().isCreated());
        verify(personalInformationService, times(1)).createUser(userDto);
    }

    @Test
    void should_create_education_when_post_education() throws Exception {
        Long id = 1L;
        EducationDto educationDto = EducationDto.builder()
                .userId(id).title("edu1").year(2019L)
                .description("asdfasdfasdfasdf")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String educationDtoJson = objectMapper.writeValueAsString(educationDto);
        mockMvc.perform(post("/users/" + id + "/educations")
                .contentType(MediaType.APPLICATION_JSON).content(educationDtoJson))
                .andExpect(status().isCreated());
        verify(personalInformationService, times(1)).addEducation(id, educationDto);
    }
}