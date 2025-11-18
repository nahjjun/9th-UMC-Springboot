package com.example.umc_springboot.domain.review.entity;

import com.example.umc_springboot.domain.reviewPhoto.entity.ReviewPhoto;
import com.example.umc_springboot.domain.store.entity.Store;
import com.example.umc_springboot.domain.user.entity.User;
import com.example.umc_springboot.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Review")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false) // Review 테이블에 "user_id"라는 이름의 컬럼이 하나 생기는 것, user의 PK를 참조한다.
    private User user; // 사용자와 리뷰는 1대1 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    @Column(name="star", nullable = false)
    @Builder.Default
    private Integer star = 1;

    @Column(name="body", nullable = false, length = 1000)
    private String body;

    // 리뷰:리뷰 사진 => 1:다 관계 매핑
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    // fetch join 시 n+1 문제를 예방하고자 @BatchSize를 지정해주었음.
    // 100개씩 묶어서 연관 데이터를 지연로딩 시켜준다.
    @BatchSize(size=100)
    private List<ReviewPhoto> reviewPhotoList = new ArrayList<>();

    public void addReviewPhoto(String photoUrl) {
        ReviewPhoto reviewPhoto = ReviewPhoto.builder()
                .review(this)
                .url(photoUrl)
                .build();
        reviewPhotoList.add(reviewPhoto);
    }


}
