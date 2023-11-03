package com.luckyseven.greendrive.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Favorite {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content; // 리뷰 내용
}
