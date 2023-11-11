package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Domain.Likes;
import com.luckyseven.greendrive.Service.LikesService;
import com.luckyseven.greendrive.dto.LikesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/likes")
    public ResponseEntity<Integer> insert(@RequestBody LikesDto likesDto){
        int likes = likesService.insert(likesDto.getUserId(), likesDto.getReviewId());
        return ResponseEntity.ok(likes);
    }
}
