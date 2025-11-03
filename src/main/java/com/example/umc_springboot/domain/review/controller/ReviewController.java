package com.example.umc_springboot.domain.review.controller;

import com.example.umc_springboot.domain.review.dto.request.ReviewSearchRequestDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResponseDto;
import com.example.umc_springboot.domain.review.service.ReviewService;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Review 관련 API")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(
            summary = "리뷰 정보들 가져오기",
            description = """
                    검색 필터를 적용하여 리뷰 정보들을 가져옵니다.
                    
                    [인증 필요 없음]
                    
                    [Request Query]
                    - page : 페이지 번호
                    - size : 한 페이지에 들어갈 데이터 개수
                    - dong : 어떤 '동'의 리뷰를 검색할지 지정
                    - star : 별점 몇점 이상의 리뷰를 검색할지 지정
                    - storeId : 리뷰를 검색하고자 하는 가게의 고유 ID
                    - type : 해당 리뷰 검색 요청이 어떤 타입의 요청인지 지정하는 변수 
                    """
    )
    @GetMapping("")
    public ResponseEntity<GlobalResponse<List<ReviewResponseDto>>> searchReviews(@ModelAttribute ReviewSearchRequestDto dto) {
        List<ReviewResponseDto> responseList = reviewService.searchReviews(dto);
        GlobalResponse<List<ReviewResponseDto>> response =  GlobalResponse.success("리뷰들 검색 성공!", responseList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
