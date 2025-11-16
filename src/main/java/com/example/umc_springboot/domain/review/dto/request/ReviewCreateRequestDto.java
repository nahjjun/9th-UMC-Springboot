package com.example.umc_springboot.domain.review.dto.request;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

public record ReviewCreateRequestDto(
        @NotNull(message = "userId가 null입니다")
        Long userId,
        @NotNull(message = "storeId가 null입니다")
        Long storeId,
        @NotNull(message = "별점값이 입력되지 않았습니다.")
        @Range(min = 1, max = 5, message = "별점값은 1에서 5 사이어야 합니다.")
        Integer star,
        @NotBlank(message = "리뷰 내용이 비었습니다.")
        String body,
        List<String> reviewPhotoUrlList
) {
}
