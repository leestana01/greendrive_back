package com.luckyseven.greendrive.Domain;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content; // 리뷰 내용

}
