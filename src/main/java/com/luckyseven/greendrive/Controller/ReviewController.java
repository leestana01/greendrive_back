package com.luckyseven.greendrive.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Service.ReviewService;
import com.luckyseven.greendrive.dto.ReviewDto;
import com.luckyseven.greendrive.dto.ReviewReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{spaceId}") //리뷰 저장, 있다면 덮어씌우기
    public ResponseEntity<Review> save(@PathVariable String spaceId,
                                       @RequestBody ReviewReqDto reqDto) throws IOException {
        ReviewDto review = reqDto.getReviewDto();
        long userId = reqDto.getUserId();
        Review r = reviewService.save(spaceId,userId,review);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);

    }

    @GetMapping("/space/{spaceId}")     //주차장 리뷰
    public ResponseEntity<List<Review>> readAll(@PathVariable String spaceId){
        List<Review> ReviewList = reviewService.findAllBySpaceId(spaceId);
        return ResponseEntity.status(HttpStatus.OK).body(ReviewList);
    }

    @GetMapping("/user/{userId}") //내 리뷰
    public ResponseEntity<List<Review>> read(@PathVariable long userId){
        List<Review> userReview = reviewService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userReview);
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<Review> update(@RequestBody ReviewReqDto review,
                                         @PathVariable String spaceId) throws IOException {
        long userId = review.getUserId();
        ReviewDto r = review.getReviewDto();
        Review updateReview = reviewService.update(r,spaceId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(updateReview);
    }

    @GetMapping("/{spaceId}") //리뷰 삭제
    public void delete(@RequestParam("userId") long userId, @PathVariable String spaceId){
        reviewService.delete(userId, spaceId);
    }


}
