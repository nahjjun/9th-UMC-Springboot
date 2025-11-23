package com.example.umc_springboot.domain.review.controller;


import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.dto.request.ReviewCreateReqDto;
import com.example.umc_springboot.domain.review.dto.response.ReviewListResDto;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import com.example.umc_springboot.global.annotation.PageZero;
import com.example.umc_springboot.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/reviews")
@Tag(name = "Review", description = "Review 관련 API")
public interface ReviewControllerDocs {
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
    ResponseEntity<GlobalResponse<?>> createReview(@RequestBody @Valid ReviewCreateReqDto dto);


    @Operation(
            summary = "리뷰 정보들을 가져오는 API",
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
                    - userId : 특정 사용자가 작성한 리뷰들을 검색할 때 지정해줄 사용자 고유 ID
                    - type : 해당 리뷰 검색 요청이 어떤 타입의 요청인지 지정하는 변수 
                    """
    )
    // swagger에 page, size, sort가 각각 따로 뜨게 하도록 @Parameters, @Parameter 어노테이션을 붙여서 따로 입력할 수 있게 함
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호(1부터 시작)", example = "1"),
            @Parameter(name = "size", description = "페이지 크기", example = "10"),
            @Parameter(name = "sort", description = "정렬 기준(ex: createdAt,desc", example = "createdAt,desc")
    })
    @GetMapping("")
    ResponseEntity<GlobalResponse<ReviewListResDto>> searchReviews(
            @RequestParam(required = false) Dong dong,
            @RequestParam(defaultValue = "0") Integer star,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "STAR") @NotNull SearchRequestType type,
            @PageZero Integer page,
            Integer size,
            String sort
    );


}