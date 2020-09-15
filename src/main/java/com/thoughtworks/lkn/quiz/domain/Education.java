package com.thoughtworks.lkn.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    private Long userId;
    private Long year;
    private String title;
    private String description;
}
