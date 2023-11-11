package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Likes;
import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.LikesRepository;
import com.luckyseven.greendrive.Repository.ReviewRepository;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public Integer insert(String userId, long reviewId) {
        Likes like = likesRepository.findByUserIdAndReviewId(userId, reviewId);
        //유저가 리뷰에 좋아요를 한 기록 찾기

        if (like != null) { //좋아요 한 기록이 있으면 삭제
            likesRepository.deleteById(like.getId());
            Optional<Review> review = reviewRepository.findById(reviewId);
            if (review.isPresent()) {
                review.get().likes(review.get().getLikes()-1); //좋아요 기록 하나 다운
                reviewRepository.save(review.get());
                return review.get().getLikes();
            }

        } else{ //좋아요 한 기록이 없으면 User, Review 설정
            like = new Likes();
            Optional<User> u = userRepository.findByUserId(userId);
            Optional<Review> r = reviewRepository.findById(reviewId);
            u.ifPresent(like::setUser);
            r.ifPresent(like::setReview);
            likesRepository.save(like);
            Optional<Review> review = reviewRepository.findById(reviewId);
            if (review.isPresent()) {
                review.get().likes(review.get().getLikes()+1); //좋아요 기록 하나 업
                reviewRepository.save(review.get());
                return review.get().getLikes();
            }
        }
        return null;
    }
}
