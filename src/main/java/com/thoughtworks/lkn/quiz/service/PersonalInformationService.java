package com.thoughtworks.lkn.quiz.service;

import com.thoughtworks.lkn.quiz.domain.Education;
import com.thoughtworks.lkn.quiz.domain.User;
import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.exception.UserNotFoundException;
import com.thoughtworks.lkn.quiz.exception.Error;
import com.thoughtworks.lkn.quiz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonalInformationService {
    private UserRepository userRepository;

    public PersonalInformationService() {
    }

    public PersonalInformationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            UserDto userDto = UserDto.builder().id(user.getId())
                    .age(user.getAge()).avatar(user.getAvatar())
                    .description(user.getDescription()).name(user.getName())
                    .build();
            return userDto;
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String errorDate = simpleDateFormat.format(date);
        String message = "用户不存在";
        Error error = Error.builder()
                .error("Not Found").message(message)
                .status(404).timeStamp(errorDate)
                .build();
        throw new UserNotFoundException(error);

    }

    public List<EducationDto> findUserEducationsById(Long id) {
        List<Education> educationList = userRepository.findUserEducationsById(id);
        if (educationList == null) {
            return new ArrayList<EducationDto>();
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
                .educationList(new ArrayList<>())
                .build();
        userRepository.save(user);
    }

    public void addEducation(Long user_id, EducationDto educationDto) {
        Education education = Education.builder()
                .description(educationDto.getDescription())
                .title(educationDto.getTitle())
                .year(educationDto.getYear())
                .userId(user_id)
                .build();
        userRepository.saveEducation(education);
    }
}
