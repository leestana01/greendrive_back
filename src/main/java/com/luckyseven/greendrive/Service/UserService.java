package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.memberdto.FindIdReqDto;
import com.luckyseven.greendrive.dto.memberdto.LoginReqDto;
import com.luckyseven.greendrive.dto.memberdto.LoginResDto;
import com.luckyseven.greendrive.dto.memberdto.SignupReqDto;
import com.luckyseven.greendrive.exception.UserDuplicateException;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public String createUser(SignupReqDto signupReqDto){
        if(userRepository.findByUserId(signupReqDto.getUserId()).isPresent()){
            throw new UserDuplicateException("아이디가 이미 존재합니다.");
        }
        if(userRepository.findByPhoneNo(signupReqDto.getPhoneNo()).isPresent()){
            throw new UserDuplicateException("전화번호가 이미 존재합니다.");
        }
        User user = User.builder()
                .userId(signupReqDto.getUserId())
                .userPassword(signupReqDto.getPassword())
                .name(signupReqDto.getName())
                .carType(signupReqDto.getCarType())
                .phoneNo(signupReqDto.getPhoneNo())
                .build();

        userRepository.save(user);

        return signupReqDto.getUserId();
    }

    public LoginResDto checkUser(LoginReqDto loginReqDto){
        User user =userRepository.findByUserId(loginReqDto.getUserId())
                .orElseThrow(()-> new UserNotFoundException("ID가 존재하지 않습니다."));
        if(loginReqDto.getPassword().equals(user.getUserPassword())){
            return LoginResDto.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .build();
        }
        else{
            throw new UserNotFoundException("비밀번호가 올바르지 않습니다.");
        }
    }

    public String findUserId(FindIdReqDto findIdReqDto){
        User user = userRepository.findByPhoneNoAndName(findIdReqDto.getPhoneNo(),findIdReqDto.getName())
                .orElseThrow(()->new UserNotFoundException("해당 정보와 일치하는 유저가 존재하지 않습니다."));

        return user.getUserId();
    }
}
