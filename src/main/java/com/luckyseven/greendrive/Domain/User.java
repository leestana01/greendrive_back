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

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptionList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image profileImg;

    @Builder
    public User(String name, String userId, String userPassword, String carType, String phoneNo) {
        this.name = name;
        this.userId = userId;
        this.userPassword = userPassword;
        this.carType = carType;
        this.phoneNo = phoneNo;
    }
}
