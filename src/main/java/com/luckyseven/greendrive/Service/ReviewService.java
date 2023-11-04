package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Image;
import com.luckyseven.greendrive.Domain.Review;
import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.ImageRepository;
import com.luckyseven.greendrive.Repository.ReviewRepository;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final ImageRepository imageRepository;

    public Review save(String spaceId, long userId, ReviewDto review) throws IOException {
        List<Review> reviewObject = reviewRepository.findReviewByUserIdAndSpaceId(userId, spaceId);
        if (!reviewObject.isEmpty()){
            reviewRepository.deleteById(reviewObject.get(0).getId());
        } //삭제 후 업데이트 하기

        Review newReview = new Review();
        newReview.setContent(review.getContent());
        Optional<User> optionalUser = userRepository.findById(userId);
        Space s = spaceRepository.findBySpaceId(spaceId);
        newReview.setSpace(s);
        if (optionalUser.isPresent()) {
            User u = optionalUser.get();
            newReview.setUser(u);
        }
        else throw new IllegalArgumentException("유저나 주차장 정보가 존재하지 않습니다");
        //유저 정보와 주차장 정보 넣기

        //이미지 처리
        if (review.getReviewImage()!=null) {
            MultipartFile reviewFile = review.getReviewImage();
            Image image = new Image();
            image.setData(reviewFile.getBytes());
            imageRepository.save(image); //이미지 저장
            newReview.setImage(image);
        }
        return reviewRepository.save(newReview);
    }


    public void delete(long userId, String spaceId) {
        List<Review> optionalReview = spaceRepository.findAllReviewsByUserIdAndSpaceId(userId, spaceId);
        reviewRepository.deleteByUserIdAndSpaceId(userId,spaceId);
    }

    public List<Review> findAllBySpaceId(String spaceId) {
        return reviewRepository.findReviewsBySpaceId(spaceId);
    }

    public List<Review> findAllByUserId(long userId) {
        return reviewRepository.findReviewsByUserId(userId);
    }

    @Transactional
    public Review update(ReviewDto review, String spaceId, long userId) throws IOException {
        List<Review> r = reviewRepository.findReviewByUserIdAndSpaceId(userId, spaceId);
        if (r.isEmpty()) throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        if (review.getReviewImage()!=null) {
            MultipartFile file = review.getReviewImage();
            Image image = new Image();
            image.setData(file.getBytes());
            r.get(0).update(image, review.getContent());
        } else{
            r.get(0).update(r.get(0).getImage(), review.getContent());
        }
        return r.get(0);
    }
}

