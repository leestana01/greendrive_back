package com.luckyseven.greendrive.Domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String userId; // 아이디
    private String userPassword; // 비밀번호

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites = new ArrayList<>();


}
