package com.luckyseven.greendrive.Service;

import com.luckyseven.greendrive.Domain.Space;
import com.luckyseven.greendrive.Repository.SpaceRepository;
import com.luckyseven.greendrive.dto.SpaceReqDto;
import com.luckyseven.greendrive.dto.SpaceForMarkersDto;
import com.luckyseven.greendrive.dto.SpaceResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Space> spaceList = spaceRepository.findAll();
        List<SpaceForMarkersDto> spaceDtoList = new ArrayList<>();
        for (Space space : spaceList) {
            SpaceForMarkersDto dto = new SpaceForMarkersDto();
            dto.setId(space.getId());
            dto.setType(space.getType());
            dto.setLatitude(space.getLatitude());
            dto.setLongitude(space.getLongitude());
            dto.setParkName(space.getParkName());

            spaceDtoList.add(dto);
        }
        return spaceDtoList;
    }

    public SpaceResDto findById(String spaceId){
        return spaceRepository.findById(spaceId)
                .orElseThrow(() -> new EntityNotFoundException("Space가 존재하지 않음 - Id 값 : " + spaceId))
                .toDTO();
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
