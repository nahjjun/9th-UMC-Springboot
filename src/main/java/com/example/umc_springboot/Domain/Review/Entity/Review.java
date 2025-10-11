package com.example.umc_springboot.Domain.Review.Entity;

import com.example.umc_springboot.Domain.ReviewPhoto.Entity.ReviewPhoto;
import com.example.umc_springboot.Domain.Store.Entity.Store;
import com.example.umc_springboot.Domain.User.Entity.User;
import com.example.umc_springboot.Global.Entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.EmbeddableInstantiator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @Column(name="score", nullable = false)
    @Builder.Default
    private Integer score = 0;

    @Column(name="body", nullable = false, length = 1000)
    private String body;

    // 리뷰:리뷰 사진 => 1:다 관계 매핑
    @OneToMany(mappedBy = "review")
    private List<ReviewPhoto> reviewPhotoList = new ArrayList<>();
}
