package com.thoughtworks.lkn.quiz.controller;

import com.thoughtworks.lkn.quiz.dto.EducationDto;
import com.thoughtworks.lkn.quiz.dto.UserDto;
import com.thoughtworks.lkn.quiz.service.PersonalInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@CrossOrigin
// GTB: 这个PersonalInformation的明明有点怪怪的，可以直接就叫 UserController
public class PersonalInformationController {
    private final PersonalInformationService personalInformationService;

    public PersonalInformationController(PersonalInformationService personalInformationService) {
        this.personalInformationService = personalInformationService;
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return personalInformationService.findUserById(id);
    }

    @GetMapping("/users/{id}/educations")
    public List<EducationDto> getUserEducationsById(@PathVariable Long id) {
        return personalInformationService.findUserEducationsById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDto userDto) {
        personalInformationService.createUser(userDto);
    }

    @PostMapping("/users/{user_id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEducation(@PathVariable Long user_id, @RequestBody @Valid EducationDto educationDto) {
        personalInformationService.addEducation(user_id, educationDto);
    }

}
