package com.example.umc_springboot.global.response;


import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T>(
        List<T> content,
        Integer currentPage,
        Integer contentSize,
        Integer totalPage,
        Long totalElements,
        Boolean isFirst,
        Boolean isLast
) {

    /**
     * Page<Dto> -> PageResponse<Dto>로 변환하는 함수
     * 단순히 데이터를 옮기기만 하면 됨
     * @param pageData Page<Dto> 형식의 데이터. 페이징해온 데이터가 담겨있음
     * @return PageResponse<T> 페이징 정보가 담긴 PageResponse
     * @param <T> 데이터의 타입
     */
    public static <T> PageResponse<T> of(Page<T> pageData){
        return new PageResponse<>(
                pageData.getContent(),
      pageData.getNumber() + 1,       // 현재 페이지 반환 (프론트 기준이므로 +1해야함)
                pageData.getNumberOfElements(), // 검색 목록 개수
                pageData.getTotalPages(),       // 총 페이지 개수
                pageData.getTotalElements(),    // 총 검색 개수
                pageData.isFirst(),             // 첫번째 페이지인지 여부
                pageData.isLast()               // 마지막 페이지인지 여부
        );
    }

    /**
     * Page<Entity> => mapper(Entity) -> Dto => PageResponse<Dto>로 변환하는 함수
     * mapper 함수를 입력받아서 Page에 있는 Entity를 Dto로 변경한 후 PageResponse<Dto>로 변환하는 함수
     * @param pageData Page 객체
     * @param mapper Page에 들어있는 Entity를 Dto로 변환하는 함수
     * @return PageResponse<Dto>
     * @param <E> Entity
     * @param <R> Result (Entity가 Dto로 변환된 결과)
     */
    public static <E, R> PageResponse<R> of(Page<E> pageData, Function<E, R> mapper){
        // Function<E, R> mapper : Entity -> mapper -> Dto(Result)
        // Function<E, R>은 함수 자체를 인자로 전달받기 위한 타입임. (자바의 함수형 인터페이스 중 하나임)
        //        @FunctionalInterface
        //        public interface Function<T, R> {
        //            R apply(T t);
        //        }
        //          => 입력(T)을 넣으면 -> 출력(R)을 반환하는 함수

        // 전달받은 mapper 함수를 이용해서 Entity를 Dto로 변환해서 List로 변환
        List<R> content = pageData.getContent().stream().map(mapper).toList();

        return new PageResponse<>(
                content,
      pageData.getNumber() + 1,       // 현재 페이지 반환 (프론트 기준이므로 +1해야함)
                pageData.getNumberOfElements(),
                pageData.getTotalPages(),
                pageData.getTotalElements(),
                pageData.isFirst(),
                pageData.isLast()
        );
    }
}
