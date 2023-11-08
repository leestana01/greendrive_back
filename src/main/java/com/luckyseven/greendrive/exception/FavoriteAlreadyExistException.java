package com.luckyseven.greendrive.exception;

public class FavoriteAlreadyExistException extends RuntimeException {
    public FavoriteAlreadyExistException(String userId, String spaceId) {
        super("Favorite이 이미 존재함. userId: " + userId + " , spaceId: " + spaceId);
    }
}
