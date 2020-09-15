package com.thoughtworks.lkn.quiz.service;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalInformationService {
    private UserRepository userRepository;

    public PersonalInformationService() {
        this.userRepository = new UserRepository();
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        UserDto userDto = null;
        if (user != null) {
            userDto = UserDto.builder().id(user.getId())
                    .age(user.getAge()).avatar(user.getAvatar())
                    .description(user.getDescription()).name(user.getName())
                    .build();
        }
        return userDto;
    }

    public List<EducationDto> findUserEducationsById(Long id) {
        List<Education> educationList = userRepository.findUserEducationsById(id);
        if (educationList == null) {
            return null;
        }
        List<EducationDto> educationDtoList = new ArrayList<>();
        educationList.forEach(education -> {
            EducationDto educationDto = EducationDto.builder()
                    .userId(education.getUserId())
                    .description(education.getDescription())
                    .title(education.getTitle())
                    .year(education.getYear())
                    .build();
            educationDtoList.add(educationDto);
        });
        return educationDtoList;
    }

    public void createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .avatar(userDto.getAvatar())
                .description(userDto.getDescription())
                .build();
        userRepository.save(user);
    }
}
