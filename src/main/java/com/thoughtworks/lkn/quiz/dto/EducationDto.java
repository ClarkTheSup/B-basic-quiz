package com.thoughtworks.lkn.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationDto {
    private Long userId;
    @NotNull
    private Long year;
    private String title;
    private String description;
}
