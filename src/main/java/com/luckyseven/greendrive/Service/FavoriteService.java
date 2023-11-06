package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Favorite;
import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.FavoriteRepository;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.FavoriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

    public Favorite create(String userId, FavoriteDto favoriteDto) {
        Optional<User> u = userRepository.findByUserId(userId);
        Optional<Space> s = spaceRepository.findById(favoriteDto.getSpaceId());
        Favorite fav = new Favorite();
        u.ifPresent(fav::setUser);
        s.ifPresent(fav::setSpace);
        favoriteRepository.save(fav);
        return fav;
    }

    public List<Space> read(String userId) {
        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);
        List<Space> spaces = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            spaces.add(favorite.getSpace());
        }
        return spaces;
    }

    public void delete(long id) {
        favoriteRepository.deleteById(id);
    }
}
