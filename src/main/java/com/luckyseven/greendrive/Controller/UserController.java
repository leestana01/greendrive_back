package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Domain.Favorite;
import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Service.FavoriteService;
import com.luckyseven.greendrive.dto.FavoriteDto;
import com.luckyseven.greendrive.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final FavoriteService favoriteService;

    @PostMapping("/{userId}/favorites")
    public ResponseEntity<Favorite> create(@RequestBody FavoriteDto favoriteDto,
                                           @PathVariable String userId){
        Favorite fav = favoriteService.create(userId, favoriteDto);
        return ResponseEntity.status(HttpStatus.OK).body(fav);
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Space>> read(@PathVariable String userId){
        List<Space> favoriteSpaceList = favoriteService.read(userId);
        return ResponseEntity.status(HttpStatus.OK).body(favoriteSpaceList);
    }

    @DeleteMapping("/favorites")
    public void delete(@RequestBody FavoriteDto favoriteDto){
        favoriteService.delete(favoriteDto.getId());
    }
}
