package com.example.umc_springboot.domain.review.dto.request;

import com.example.umc_springboot.domain.address.enums.Dong;
import com.example.umc_springboot.domain.review.enums.SearchRequestType;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// @ModelAttribute로 쿼리 데이터들을 한번에 매핑할 수 있다고 함.
// @ModelAttribute는 @Setter를 사용해서 한번에 묶기 때문에, Dto에 @Setter를 붙여줘야 함
@Getter
@Setter
@Builder
public class ReviewSearchReqDto {
    @Parameter(description = "리뷰 페이지")
    @NotNull(message = "몇 페이지의 리뷰를 볼 것인지 입력해야합니다.")
    private final Integer page;

    @Parameter(description = "한 페이지의 데이터 개수")
    @NotNull(message = "한 페이지에 몇 개의 리뷰를 볼 것인지 입력해야합니다.")
    private final Integer size;

    @Parameter(description = "어떤 '동'의 리뷰를 검색할지 지정하는 변수")
    private final Dong dong;
    // ㄴ> @ModelAttribute는 Enum을 직접 받을 수 있음

    // 별점 몇점 이상의 리뷰를 반환하게 할 것인지 지정
    // 안들어오면 기본값을 0으로 설정해서, 0 이상의 별점들을 전부 조회하도록 설정
    @Parameter(description = "별점 몇점 이상의 리뷰를 검색할 것인지 지정하는 변수")
    @Builder.Default
    @NotNull
    private final Integer star = 0;

    @Parameter(description = "리뷰를 검색하고자 하는 가게의 고유 ID")
    private final Long storeId;

    @Parameter(description = "해당 리뷰 검색 요청이 어떤 타입의 요청인지 지정하는 변수")
    @NotBlank
    @Builder.Default
    private final SearchRequestType type = SearchRequestType.STAR;
        // ㄴ> @ModelAttribute는 Enum을 직접 받을 수 있음
}
