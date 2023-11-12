package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.*;
import com.luckyseven.greendrive.Repository.*;
import com.luckyseven.greendrive.dto.ReviewDto;
import jdk.jfr.StackTrace;
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
    private final LikesRepository likesRepository;

    public Review save(String spaceId, ReviewDto review) throws IOException {
        String userId = review.getUserId();
        List<Review> reviewObject = reviewRepository.findByUserIdAndSpaceId(userId, spaceId);
        if (!reviewObject.isEmpty()){
            likesRepository.deleteAllByReviewId(reviewObject.get(0).getId());
            reviewRepository.deleteById(reviewObject.get(0).getId());
        } //삭제 후 업데이트 하기

        Review newReview = new Review();
        newReview.setContent(review.getContent()); //내용

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<Space> s = spaceRepository.findById(spaceId);
        s.ifPresent(newReview::setSpace);
        if (optionalUser.isPresent()){
            newReview.setUser(optionalUser.get());
            newReview.setName(optionalUser.get().getName());
        }
        //유저 정보와 주차장 정보 넣기

        LocalDate now = LocalDate.now();
        newReview.setDate(now.toString());
        newReview.setLikes(0); //처음 좋아요는 0

        newReview.setSatisfaction(review.getSatisfaction());

        List<Review> reviewList = reviewRepository.findAllBySpaceId(spaceId);
        long aveSatisfy=review.getSatisfaction();
        for (Review re : reviewList) {
            aveSatisfy += re.getSatisfaction();
        }
        aveSatisfy = aveSatisfy/(reviewList.size()+1);
        Optional<Space> space = spaceRepository.findById(spaceId);
        if (space.isPresent()) {
            Space space1 = space.get();
            space1.setAveSatisfaction(aveSatisfy);
            spaceRepository.save(space1);
        } //장소의 리뷰들을 모두 가져와서 평균을 내고 장소 DB에 업데이트


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

    public Review findById(long reviewId) {
        Optional<Review> r = reviewRepository.findById(reviewId);
        return r.orElse(null);
    }


    @Transactional
    public Integer updateSatisfy(long id, Integer satisfaction) {
        Optional<Review> r = reviewRepository.findById(id);
        int aveSatisfy =0;
        if (r.isPresent()) {
            Review review = r.get();
            review.setSatisfaction(satisfaction);
            reviewRepository.save(review);
            List<Review> reviewList = reviewRepository.findAllBySpaceId(r.get().getSpace().getId());
            for (Review re : reviewList) {
                aveSatisfy += re.getSatisfaction();
            }
            aveSatisfy = aveSatisfy/reviewList.size();
            Optional<Space> space = spaceRepository.findById(r.get().getSpace().getId());
            if (space.isPresent()) {
                Space s = space.get();
                s.setAveSatisfaction(aveSatisfy);
                spaceRepository.save(s);
                return aveSatisfy;
            }
        }
        return null;

    }
}

