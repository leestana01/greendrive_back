package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Favorite;
import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Domain.User;
import com.luckyseven.greendrive.Repository.FavoriteRepository;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.Repository.UserRepository;
import com.luckyseven.greendrive.dto.FavoriteDto;
import com.luckyseven.greendrive.dto.spacedto.SpaceForSearchDto;
import com.luckyseven.greendrive.exception.FavoriteAlreadyExistException;
import com.luckyseven.greendrive.exception.FavoriteNotFoundException;
import com.luckyseven.greendrive.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

    public FavoriteDto create(FavoriteDto favoriteDto) {
        User user = userRepository.findByUserId(favoriteDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(favoriteDto.getUserId()));
        Space space = spaceRepository.findById(favoriteDto.getSpaceId())
                .orElseThrow(() -> new EntityNotFoundException(favoriteDto.getSpaceId()));

        if (favoriteRepository.findByUserAndSpace(user,space).isPresent()){
            throw new FavoriteAlreadyExistException(user.getUserId(), space.getId());
        }
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setSpace(space);
        favoriteRepository.save(favorite);
        return favorite.toDto();
    }

    public List<SpaceForSearchDto> read(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<Favorite> favoriteList = favoriteRepository.findByUser(user);
        return favoriteList.stream().map(Favorite::getSpace).map(Space::toDTOforSearch).collect(Collectors.toList());
    }

    public void delete(FavoriteDto favoriteDto) {
        User user = userRepository.findByUserId(favoriteDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(favoriteDto.getUserId()));
        Space space = spaceRepository.findById(favoriteDto.getSpaceId())
                .orElseThrow(() -> new EntityNotFoundException(favoriteDto.getSpaceId()));

        Favorite favorite = favoriteRepository.findByUserAndSpace(user, space)
                .orElseThrow(() -> new FavoriteNotFoundException(favoriteDto.getUserId(), favoriteDto.getSpaceId()));

        favoriteRepository.delete(favorite);
    }
}
