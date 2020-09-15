package com.thoughtworks.lkn.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    @NotNull
    @Size(min=1, max=128)
    private String name;
    @NotNull
    @Min(17)
    private Long age;
    @NotNull
    @Size(min=8, max=512)
    private String avatar;
    @Size(min=0, max=1024)
    private String description;
}
