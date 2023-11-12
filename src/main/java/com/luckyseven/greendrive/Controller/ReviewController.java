package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Service.ReviewService;
import com.luckyseven.greendrive.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{spaceId}") //리뷰 저장, 있다면 덮어 씌우기
    public ResponseEntity<Review> save(@PathVariable String spaceId,
                                       @ModelAttribute ReviewDto review) throws IOException {
        Review r = reviewService.save(spaceId,review);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    @GetMapping("/space/{spaceId}")     //주차장 리뷰
    public ResponseEntity<List<Review>> readAll(@PathVariable String spaceId){
        List<Review> ReviewList = reviewService.findAllBySpaceId(spaceId);
        return ResponseEntity.status(HttpStatus.OK).body(ReviewList);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> readReview(@PathVariable long reviewId){
        Review r = reviewService.findById(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @GetMapping("/user/{userId}") //내 리뷰
    public ResponseEntity<List<Review>> read(@PathVariable String userId){
        List<Review> userReview = reviewService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userReview);
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<Review> update(@ModelAttribute ReviewDto review,
                                         @PathVariable String spaceId) throws IOException {
        Review updateReview = reviewService.update(review,spaceId);
        return ResponseEntity.status(HttpStatus.OK).body(updateReview);
    }

    @DeleteMapping("/{spaceId}") //리뷰 삭제
    public void delete(@RequestBody ReviewDto reviewDto, @PathVariable String spaceId){
        reviewService.delete(reviewDto.getUserId(), spaceId);
    }

    @PostMapping("/{spaceId}/satisfy")
    public ResponseEntity<Integer> updateSatisfy(@RequestBody ReviewDto reviewDto, @PathVariable String spaceId){
        int s = reviewService.updateSatisfy(reviewDto.getId(), reviewDto.getSatisfaction());
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }


}
