package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.exception.FavoriteAlreadyExistException;
import com.luckyseven.greendrive.exception.FavoriteNotFoundException;
import com.luckyseven.greendrive.exception.SpaceNotFoundException;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SpaceNotFoundException.class)
    public ResponseEntity<?> handleSpaceNotFoundException(SpaceNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<?> handleFavoriteNotFoundException(FavoriteNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FavoriteAlreadyExistException.class)
    public ResponseEntity<?> handleFavoriteAlreadyExistException(FavoriteAlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
