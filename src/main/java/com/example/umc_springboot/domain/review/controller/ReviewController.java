package com.example.umc_springboot.domain.review.controller;

import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.dto.request.ReviewCreateRequestDto;
import com.example.umc_springboot.domain.review.dto.request.ReviewSearchRequestDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewResponseDto;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.domain.review.service.ReviewService;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
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
            summary = "리뷰 생성 API",
            description = """
                    리뷰를 생성해 특정 가게에 등록합니다.
                    
                    [인증 필요함]
                    
                    [Request Body]
                    - userId: Long
                    - storeId: Long
                    - star: Integer (최대가 5)
                    - body: String
                    - reviewPhotoUrlList: List<String>
                    """
    )
    @PostMapping("")
    public ResponseEntity<GlobalResponse<?>> createReview(@RequestBody @Valid ReviewCreateRequestDto dto){
        reviewService.createReview(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success("리뷰 등록 성공!"));
    }


    @Operation(
            summary = "리뷰 정보들 가져오는 API",
            description = """
                    검색 필터를 적용하여 리뷰 정보들을 가져옵니다.
                    
                    [인증 필요 없음]
                    
                    [Request Query]
                    - page : 페이지 번호
                    - size : 한 페이지에 들어갈 데이터 개수
                    - sort : 어떤 기준으로 정렬할지 지정. ex: "정렬기준,정렬방식"
                    - dong : 어떤 '동'의 리뷰를 검색할지 지정
                    - star : 별점 몇점 이상의 리뷰를 검색할지 지정
                    - storeId : 리뷰를 검색하고자 하는 가게의 고유 ID
                    - type : 해당 리뷰 검색 요청이 어떤 타입의 요청인지 지정하는 변수 
                    """
    )
    // swagger에 page, size, sort가 각각 따로 뜨게 하도록 @Parameters, @Parameter 어노테이션을 붙여서 따로 입력할 수 있게 함
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)", example = "0"),
            @Parameter(name = "size", description = "페이지 크기", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준(ex: createdAt,desc", example = "createdAt,desc")
    })
    @GetMapping("")
    public ResponseEntity<GlobalResponse<List<ReviewResponseDto>>> searchReviews(
            @RequestParam(required = false) Dong dong,
            @RequestParam(defaultValue = "0") Integer star,
            @RequestParam(required = false) Long storeId,
            @RequestParam(defaultValue = "STAR") @NotNull SearchRequestType type,
            Pageable pageable // page, size, sort 를 받을 수 있음
            // sort=star,asc는 star 기준으로 오름차순
    ) {
        List<ReviewResponseDto> responseList = reviewService.searchReviews(dong, star, storeId, type, pageable);
        GlobalResponse<List<ReviewResponseDto>> response =  GlobalResponse.success("리뷰들 검색 성공!", responseList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
