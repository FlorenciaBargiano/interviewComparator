package com.compass.excercise.comparator.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail extends RuntimeException {
    private LocalDateTime timeStamp;
    private String code;
    private String detail;
}
