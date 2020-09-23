package com.thoughtworks.lkn.quiz.service;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.exception.UserNotFoundException;
import com.thoughtworks.lkn.quiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalInformationServiceTest {
    @Mock
    private UserRepository userRepository;
    private PersonalInformationService personalInformationService;

    @BeforeEach
    public void setUp() {
        personalInformationService = new PersonalInformationService(this.userRepository);
    }

    @Test
    void should_return_userDto_when_find_user_by_id_and_user_exists() {
        Long id = 1L;
        User user = User.builder().id(id).build();
        when(userRepository.findUserById(anyLong())).thenReturn(user);

        UserDto userDto = personalInformationService.findUserById(id);

        verify(userRepository, times(1)).findUserById(id);
        assertEquals(userDto.getId(), user.getId());
    }

    @Test
    void should_throw_error_when_find_user_by_id_and_user_not_exists() {
        Long id = 1L;
        when(userRepository.findUserById(anyLong())).thenReturn(null);

        try {
            personalInformationService.findUserById(id);
        } catch (UserNotFoundException e) {
            assertEquals(e.getError().getStatus(), 404);
        }
        verify(userRepository, times(1)).findUserById(id);
    }

    @Test
    void should_return_education_list_when_find_educations_by_user_id() {
        Long id = 1L;
        List<Education> educationList = new ArrayList<>();
        educationList.add(Education.builder().userId(id).title("edu1").build());
        educationList.add(Education.builder().userId(id).title("edu2").build());
        when(userRepository.findUserEducationsById(anyLong())).thenReturn(educationList);

        List<EducationDto> educationDtoList = personalInformationService.findUserEducationsById(id);

        verify(userRepository).findUserEducationsById(id);
        assertEquals(educationDtoList.size(), educationList.size());
        for (int i=0; i<educationDtoList.size(); i++) {
            assertEquals(educationDtoList.get(i).getTitle(), educationList.get(i).getTitle());
        }
    }

    @Test
    void should_return_empty_list_when_user_has_no_educations() {
        Long id = 1L;
        when(userRepository.findUserEducationsById(anyLong())).thenReturn(null);

        List<EducationDto> educationDtoList = personalInformationService.findUserEducationsById(id);

        verify(userRepository).findUserEducationsById(id);
        assertEquals(educationDtoList.size(), 0);
    }

    @Test
    void should_call_save_when_create_user() {
        Long id = 1L;
        UserDto userDto = UserDto.builder().id(id).build();

        personalInformationService.createUser(userDto);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void should_call_save_when_add_education() {
        Long id = 1L;
        EducationDto educationDto = EducationDto.builder().userId(id).build();

        personalInformationService.addEducation(id, educationDto);

        verify(userRepository, times(1)).saveEducation(any());
    }
}