package com.example.umc_springboot.domain.review.controller;

import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.dto.request.ReviewCreateReqDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewListResDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResDto;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.domain.review.service.ReviewService;
import com.example.umc_springboot.global.annotation.PageZero;
import com.example.umc_springboot.global.response.GlobalResponse;
import com.example.umc_springboot.global.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ReviewController implements ReviewControllerDocs{
    private final ReviewService reviewService;
    private final PageUtil pageUtil;

    @Override
    public ResponseEntity<GlobalResponse<?>> createReview(@RequestBody @Valid ReviewCreateReqDto dto){
        reviewService.createReview(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("리뷰 등록 성공!"));
    }


    @Override
    public ResponseEntity<GlobalResponse<ReviewListResDto>> searchReviews(
            @RequestParam(required = false) Dong dong,
            @RequestParam(defaultValue = "0") Integer star,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "STAR") @NotNull SearchRequestType type,
            @PageZero Integer page,
            Integer size,
            String sort
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, pageUtil.parseSort(sort));
        ReviewListResDto responseDto = reviewService.searchReviews(dong, star, storeId, userId, type, pageable);

        GlobalResponse<ReviewListResDto> response =  GlobalResponse.success("리뷰 목록 검색 성공!", responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
