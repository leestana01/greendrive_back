package com.luckyseven.greendrive.Controller;

import com.luckyseven.greendrive.Service.SpaceService;
import com.luckyseven.greendrive.dto.SpaceForMarkersDto;
import com.luckyseven.greendrive.dto.SpaceReqDto;
import com.luckyseven.greendrive.dto.SpaceResDto;
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
}
