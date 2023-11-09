package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Domain.Favorite;
import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Service.FavoriteService;
import com.luckyseven.greendrive.Service.UserService;
import com.luckyseven.greendrive.dto.FavoriteDto;
import com.luckyseven.greendrive.dto.memberdto.FindIdReqDto;
import com.luckyseven.greendrive.dto.memberdto.LoginReqDto;
import com.luckyseven.greendrive.dto.memberdto.SignupReqDto;
import com.luckyseven.greendrive.dto.spacedto.SpaceForSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupReqDto signupReqDto) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.createUser(signupReqDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> checkUser(@RequestBody LoginReqDto loginReqDto)  {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.checkUser(loginReqDto));
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> findUserId(@RequestBody FindIdReqDto findIdReqDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findUserId(findIdReqDto));
    }

    @PostMapping("/favorites")
    public ResponseEntity<FavoriteDto> create(@RequestBody FavoriteDto favoriteDto){
        favoriteService.create(favoriteDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<SpaceForSearchDto>> read(@RequestParam String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(favoriteService.read(userId));
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<String> delete(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.delete(favoriteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
