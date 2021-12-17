package com.douzone.pingpong.service.part;

import com.douzone.pingpong.domain.part.Part2;
import com.douzone.pingpong.repository.part.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    public List<Part2> getPartList(Long teamId) {
        return partRepository.getPartList(teamId);
    }

    public void addPart(Long teamId, String partName) {
         partRepository.addPart(teamId, partName);
    }

    public void delPart(Long partId) {
        partRepository.delPart(partId);
    }

    public Long getFirstPartId(Long teamId) {
        return partRepository.getFirstPartId(teamId);
    }



}
