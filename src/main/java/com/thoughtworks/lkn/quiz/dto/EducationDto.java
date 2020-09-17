package com.thoughtworks.lkn.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// GTB: + 有单独的 Dto，不错
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationDto {
    private Long userId;
    @NotNull
    private Long year;
    @NotNull
    @Size(min=1, max=256)
    private String title;
    @NotNull
    @Size(min=1, max=4096)
    private String description;
}
