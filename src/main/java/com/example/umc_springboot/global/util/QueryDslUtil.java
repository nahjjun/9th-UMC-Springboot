package com.example.umc_springboot.global.util;


import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Pageable;


public class QueryDslUtil {
    /**
     * Pageable과 EntityPathBase로 정렬 객체인 OrderSpecifier들의 배열을 반환하는 함수.
     * 하나의 OrderSpecifier는 SQL의 ORDER BY 한 절에 해당한다.
     * @param pageable : Service에서 넘어온 페이징 설정. page, size, 정렬 기준이 담겨 있다.
     * @param qClass : PathBuilder에게 넘겨줄 엔티티의 루트 경로 객체. 정렬이나 조건을 동적으로 만들 때, 기분이 되는 Q클래스가 무엇인지 알게 하기 위함.
     *               정렬 기준 필드인 createdAt이나 id 등이 해당 Entity의 컬럼이라는 것을 알려주기 위함임.
     */
    public static <T>OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, EntityPathBase<T> qClass){
        // 1. PathBuilder 생성
        // QueryDsl에서 어떤 엔티티의 속성에 대한 경로를 동적으로 생성해 줄 필요가 있음.
        // 엔티티는 QueryDSL의 QClass를 사용하지만, 어떤 속성을 사용할지에 대한 정보는 Pageable 객체에 있다.
            // ex) QReview의 createdAt을 기준으로 정렬할 때, QReview의 createdAt은 Pageable에 의존하고 있다.
            // QReview.createdAt에 대한 경로가 필요하므로, PathBuilder를 사용하여 경로를 생성해준다.
        PathBuilder pathBuilder = new PathBuilder<>(qClass.getType(), qClass.getMetadata());

        // 2. pathBuilder를 이용해서
        return pageable.getSort()
                .stream()
                .map(order -> new OrderSpecifier<>(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        pathBuilder.getString(order.getProperty())
                )).toArray(OrderSpecifier[]::new);
    }
}
