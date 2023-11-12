package com.luckyseven.greendrive.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luckyseven.greendrive.dto.ReviewDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    private String content; // 리뷰 내용

    private String date; //리뷰 등록 날짜
    private Integer likes; //좋아요 수

    private Integer satisfaction=0; //1~5의 level

    public void update(Image image, String content) {
        this.image = image;
        this.content = content;
    }

    public void likes(Integer likes){
        this.likes = likes;
    }
}
