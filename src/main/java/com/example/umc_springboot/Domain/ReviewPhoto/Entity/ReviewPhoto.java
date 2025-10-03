package com.example.umc_springboot.Domain.ReviewPhoto.Entity;

import com.example.umc_springboot.Domain.Review.Entity.Review;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // jpa, hibernate에서만 접근할 수 있게 설정
@Table(name = "ReviewPhoto")
public class ReviewPhoto extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지연 로딩 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable = false)
    private Review review;

    @Column(name = "url", nullable = false)
    private String url;
}
