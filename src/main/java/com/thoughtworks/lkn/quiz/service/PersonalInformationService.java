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
        // GTB: - 应该注入而不是自己 new 实例
        this.userRepository = new UserRepository();
    }

    public UserDto findUserById(Long id) {
        User user = userRepository.findUserById(id);
        // GTB: - 直接在 if 里 return userDto 就行了，不用在外面先定义一遍。
        UserDto userDto = null;
        if (user != null) {
            // GTB: - 转换的逻辑可以抽取到其它工具类中完成
            userDto = UserDto.builder().id(user.getId())
                    .age(user.getAge()).avatar(user.getAvatar())
                    .description(user.getDescription()).name(user.getName())
                    .build();
            return userDto;
        } else {
            // GTB: - 这里不再需要用 else 子句包一层，直接写就好
            // GTB: - UserNotFoundException的输入只要有 status 和 message 就够了，不需要给整个 error 给它
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

    }

    public List<EducationDto> findUserEducationsById(Long id) {
        List<Education> educationList = userRepository.findUserEducationsById(id);
        if (educationList == null) {
            // GTB: - 如果用户没有 educations，应该返回的是 [] 而不是 null
            return null;
        }
        // GTB: - 转换的逻辑可以抽取到其它工具类中完成
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
        // GTB: - 转换的逻辑可以抽取到其它工具类中完成
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
        // GTB: - 转换的逻辑可以抽取到其它工具类中完成
        Education education = Education.builder()
                .description(educationDto.getDescription())
                .title(educationDto.getTitle())
                .year(educationDto.getYear())
                .userId(user_id)
                .build();
        userRepository.saveEducation(education);
    }
}
