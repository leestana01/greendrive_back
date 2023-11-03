package com.luckyseven.greendrive.Domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userId; // 아이디
    private String userPassword; // 비밀번호

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favoriteList = new ArrayList<>();


    @Builder
    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }
}
