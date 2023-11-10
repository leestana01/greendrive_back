package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, UserNotFoundException.class, FavoriteNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFoundException(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserDuplicateException.class, FavoriteAlreadyExistException.class, ImageNotFoundException.class})
    public ResponseEntity<?> handleFavoriteAlreadyExistException(FavoriteAlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
