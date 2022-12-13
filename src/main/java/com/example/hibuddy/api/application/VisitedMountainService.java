package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.VisitedMountain;
import com.example.hibuddy.api.domain.repository.FamousMountainRepository;
import com.example.hibuddy.api.domain.repository.VisitedMountainRepository;
import com.example.hibuddy.api.domain.support.RegionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitedMountainService {

    private final UserService userService;
    private final VisitedMountainRepository visitedMountainRepository;
    private final FamousMountainRepository famousMountainRepository;

    public List<VisitedMountain> getByUser(Long userId) {
        User user = userService.getUserById(userId);

        return visitedMountainRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public Pair<List<VisitedMountain>, Integer> getByUserAndRegion(Long userId, RegionType regionType) {
        User user = userService.getUserById(userId);
        int totalCounts = famousMountainRepository.countAllByRegion_RegionType(regionType);

        return Pair.of(visitedMountainRepository.findAllByUserAndFamousMountain_Region_RegionType(user, regionType), totalCounts);
    }
}
