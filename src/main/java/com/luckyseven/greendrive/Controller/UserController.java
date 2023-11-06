package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Service.UserService;
import com.luckyseven.greendrive.dto.memberdto.FindIdReqDto;
import com.luckyseven.greendrive.dto.memberdto.LoginReqDto;
import com.luckyseven.greendrive.dto.memberdto.SignupReqDto;
import com.luckyseven.greendrive.exception.UserDuplicateException;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
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
}
