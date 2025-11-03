package com.example.umc_springboot.domain.review.repository.querydsl;

import com.example.umc_springboot.domain.review.entity.QReview;
import com.example.umc_springboot.domain.review.entity.Review;
import com.example.umc_springboot.domain.review.repository.ReviewRepository;
import com.example.umc_springboot.global.util.QueryDslUtil;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

// 커스텀 레포지토리임을 표현해주기 위해 @Service가 아니라 @Repository를 붙인다.
@Repository
@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl {
    private final EntityManager em; // JPA 핵심 객체. 영속성 컨텍스트를 관리함

    /**
     * 리뷰 검색 API
     */
    @Override
    public List<Review> searchReview(Predicate predicate) {
        // 1. JPA 세팅
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 2. Q클래스 선언하기
        QReview review = QReview.review;

        return queryFactory
                .selectFrom(review)
                .where(predicate)
                .fetch();
    }

    /**
     * 리뷰 검색 API
     */
    @Override
    public Page<Review> searchReview(Predicate predicate, Pageable pageable) {
        // 1. JPA 세팅
        // JPAQeuryFactory : JPA 환경에서 타입 안전한 쿼리를 쉽게 작성하도록 도와주는 클래스
        // EntityManager를 기반으로 생성되며, 실제 결과를 가져오는 역할도 함
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 2. Q클래스 선언하기
        QReview review = QReview.review;

        // 3. Predicate, Pageable에 맞는 데이터 내용 가져오기
        List<Review> content = queryFactory
                .selectFrom(review)
                .where(predicate)
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지에 들어가는 데이터 개수
                .orderBy(QueryDslUtil.getOrderSpecifiers(pageable, review)) // OrderSpecifier<?>[]로 변환해서 넘겨줘야함.
                .fetch();

        // 4. 해당 조건(Predicate)에 맞는 데이터의 총 개수를 구하는 쿼리
        // Page<T>는 전체 페이지/전체 검색 개수를 포함해야하므로, 조건에 매칭되는 총 건수가 필요하다.
        // 해당 값으로 총 페이지 수와 다음/이전 페이지 존재 여부를 정확하게 계산한다.
        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(predicate);

        // 5. PageableExecutionUtils 클래스의 .getPage() 함수: List<Review>인 content를 감싸주는 헬퍼 메서드임
        // 이미 페이징된 데이터(content)와 pageable 정보를 받아서, 필요한 경우 count 쿼리를 실행해 Page 객체를 만들어준다.
        // getPage(List<T> content, Pageable pageable, LongSupplier totalSupplier)
            // content : 현재 페이지의 데이터 리스트
            // pageable : 요청 받은 페이지 정보 (page number, page size, sort)
            // totalSupplier : 전체 데이터 개수를 반환하는 함수형 인터페이스
                // ㄴ> 조건에 따라 count() 쿼리가 실행이 될 수도 있고 아닐 수도 있음
                // (1) 첫 페이지이고(pageable.getOffset() == 0), content.size() < pageSize인 경우 : 실행 안함
                // (2) content가 비어 있는 경우 (content.isEmpty() == true) : 실행 안함
                // (3) 위 두 조건 외 : 실행함 (전체 개수를 알아야 총 페이지 수 계산 가능)
                // => 이 조건들은 getPage() 함수가 내부적으로 전부 처리한다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

}
