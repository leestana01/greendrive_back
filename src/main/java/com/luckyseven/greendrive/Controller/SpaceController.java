package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Service.SpaceService;
import com.luckyseven.greendrive.dto.spacedto.SpaceForMarkersDto;
import com.luckyseven.greendrive.dto.spacedto.SpaceForSearchDto;
import com.luckyseven.greendrive.dto.spacedto.SpaceReqDto;
import com.luckyseven.greendrive.dto.spacedto.SpaceResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<SpaceResDto> createSpace(@RequestBody SpaceReqDto spaceReqDto) {
        SpaceResDto spaceResDto = spaceService.createSpace(spaceReqDto);
        return ResponseEntity.ok(spaceResDto);
    }

    @GetMapping
    public ResponseEntity<List<SpaceForMarkersDto>> getAllSpaces() {
        List<SpaceForMarkersDto> spaces = spaceService.findAll();
        return ResponseEntity.ok(spaces);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResDto> getSpaceById(@PathVariable String spaceId) {
        SpaceResDto spaceResDto = spaceService.findById(spaceId);
        return ResponseEntity.ok(spaceResDto);
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResDto> updateSpace(@PathVariable String spaceId, @RequestBody SpaceReqDto spaceReqDto) {
        SpaceResDto updatedSpace = spaceService.updateById(spaceId, spaceReqDto);
        return ResponseEntity.ok(updatedSpace);
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<Void> deleteSpace(@PathVariable String spaceId) {
        spaceService.deleteById(spaceId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SpaceForSearchDto>> searchSpaces(@RequestParam String keyword) {
        Integer spaceType = null;
        if ("공영".equals(keyword)) {
            spaceType = 0;
        } else if ("민영".equals(keyword)) {
            spaceType = 1;
        } else if ("가짜".equals(keyword)) {
            spaceType = 2;
        }
        return ResponseEntity.ok(spaceService.searchSpaces(keyword, spaceType));
    }
}
