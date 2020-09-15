package com.thoughtworks.lkn.quiz.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    private String timeStamp;
    private Integer status;
    private String error;
    private String message;
}
