package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.MountainBoard;
import com.example.hibuddy.api.domain.RegionBoard;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.RegionBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionBoardService {

    private final RegionBoardRepository regionBoardRepository;
    private final MountainBoardService mountainBoardService;

    public HashMap<Long, Boolean> findFamousMountainRegionsMapOfUser(User user, List<FamousMountain> mountains) {
        MountainBoard mountainBoard = mountainBoardService.getByUser(user);
        List<RegionBoard> regionBoards = List.of();

        HashMap<Long, Boolean> famousMountainVisitedMap = new HashMap<Long, Boolean>();

        //regionBoards.forEach(regionBoard -> famousMountainVisitedMap.put(regionBoard.getFamousMountain().getId(), true));

        return famousMountainVisitedMap;
    }
}
