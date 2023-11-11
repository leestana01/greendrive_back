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
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;
    private final ImageRepository imageRepository;

    public Review save(String spaceId, ReviewDto review) throws IOException {
        String userId = review.getUserId();
        List<Review> reviewObject = reviewRepository.findByUserIdAndSpaceId(userId, spaceId);
        if (!reviewObject.isEmpty()){
            reviewRepository.deleteById(reviewObject.get(0).getId());
        } //삭제 후 업데이트 하기

        Review newReview = new Review();
        newReview.setContent(review.getContent()); //내용

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<Space> s = spaceRepository.findById(spaceId);
        s.ifPresent(newReview::setSpace);
        optionalUser.ifPresent(newReview::setUser);
        //유저 정보와 주차장 정보 넣기

        LocalDate now = LocalDate.now();
        newReview.setDate(now.toString());
        newReview.setLikes(0); //처음 좋아요는 0

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


    public void delete(String userId, String spaceId) {
        Optional<User> u = userRepository.findByUserId(userId);
        if (u.isPresent()) {
            User user = u.get();
            long id = user.getId();
            reviewRepository.deleteByUserIdAndSpaceId(id,spaceId);
        }
    }

    public List<Review> findAllBySpaceId(String spaceId) {
        return reviewRepository.findBySpaceId(spaceId);
    }

    public List<Review> findAllByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Transactional
    public Review update(ReviewDto review, String spaceId) throws IOException {
        String userId = review.getUserId();
        List<Review> r = reviewRepository.findByUserIdAndSpaceId(userId, spaceId);
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

