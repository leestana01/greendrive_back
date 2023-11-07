package com.luckyseven.greendrive.exception;

public class UserDuplicateException extends RuntimeException{
    public UserDuplicateException(String message) {
        super(message);
    }
}