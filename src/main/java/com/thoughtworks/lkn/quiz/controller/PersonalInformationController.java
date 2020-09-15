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

}
