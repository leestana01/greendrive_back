package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.dto.SpaceForSearchDto;
import com.luckyseven.greendrive.dto.SpaceReqDto;
import com.luckyseven.greendrive.dto.SpaceForMarkersDto;
import com.luckyseven.greendrive.dto.SpaceResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {

    public final SpaceRepository spaceRepository;

    // CREATE 영역 -------------------------------
    public SpaceResDto createSpace(SpaceReqDto spaceReqDto){
        Space space = new Space(spaceReqDto);
        spaceRepository.save(space);
        return space.toDTO();
    }


    // READ 영역 -------------------------------
    public List<SpaceForMarkersDto> findAll(){
        return spaceRepository.findAll().stream().map(Space::toDTOforMarkers).collect(Collectors.toList());
    }

    public SpaceResDto findById(String spaceId){
        return spaceRepository.findById(spaceId)
                .orElseThrow(() -> new EntityNotFoundException("Space가 존재하지 않음 - Id 값 : " + spaceId))
                .toDTO();
    }

    public List<SpaceForSearchDto> searchSpaces(String keyword, Integer type) {
        return spaceRepository.findByKeywordOrType(keyword, type).stream().map(Space::toDTOforSearch).collect(Collectors.toList());
    }

    // UPDATE 영역 -------------------------------

    public SpaceResDto updateById(String spaceId, SpaceReqDto reqDto){
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new EntityNotFoundException("Space가 존재하지 않음 - Id 값 : " + spaceId));

        spaceRepository.save(space.updateFromDto(reqDto));
        return space.toDTO();
    }

    // DELETE 영역 -------------------------------

    public void deleteById(String spaceId){
        spaceRepository.deleteById(spaceId);
    }
}
