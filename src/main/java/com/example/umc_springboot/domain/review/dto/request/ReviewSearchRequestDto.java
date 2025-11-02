package com.example.umc_springboot.domain.review.dto.request;

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
public class ReviewSearchRequestDto {
    @NotNull(message = "몇 페이지의 리뷰를 볼 것인지 입력해야합니다.")
    private final Integer page;
    @NotNull(message = "한 페이지에 몇 개의 리뷰를 볼 것인지 입력해야합니다.")
    private final Integer size;
    // 어느 지역을 기준으로 검색할 것인지 지정
    private final String location;
    // 별점 몇점 이상의 리뷰를 반환하게 할 것인지 지정
    private final Integer star;

}
