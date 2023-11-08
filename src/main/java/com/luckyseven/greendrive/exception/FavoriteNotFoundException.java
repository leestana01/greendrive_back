package com.luckyseven.greendrive.exception;

public class FavoriteNotFoundException extends RuntimeException {
    public FavoriteNotFoundException(String userId, String spaceId) {
        super("Favorite 이 발견되지 않음. userId: " + userId + " , spaceId: " + spaceId);
    }
}
