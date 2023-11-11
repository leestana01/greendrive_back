package com.luckyseven.greendrive.Domain;

import com.luckyseven.greendrive.dto.memberdto.SignupReqDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String userId; // 아이디
    private String userPassword; // 비밀번호
    private String carType;
    private String phoneNo;
    private Integer isJudged = 0; // (0: 등록 안됨, 1: 승인 됨, 2: 심사 중, 3: 심사 반려)

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptionList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image profileImg;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image judgeCarImg;

    @OneToMany(mappedBy="user")
    private List<Likes> likesList = new ArrayList<>();

    @Builder
    public User(String name, String userId, String userPassword, String carType, String phoneNo) {
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.carType = carType;
        this.phoneNo = phoneNo;
    }
}
