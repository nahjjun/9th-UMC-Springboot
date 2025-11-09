package com.example.umc_springboot.global.success.model;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
