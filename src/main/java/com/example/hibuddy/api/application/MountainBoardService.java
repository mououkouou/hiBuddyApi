package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.*;
import com.example.hibuddy.api.domain.repository.*;
import com.example.hibuddy.api.interfaces.response.MountainBoardView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainBoardService {

    private final MountainBoardRepository mountainBoardRepository;
    private final FamousMountainService famousMountainService;
    private final RegionBoardRepository regionBoardRepository;
    private final UserRepository userRepository;
    private final MonthlyRegionBoardRepository monthlyRegionBoardRepository;
    private final UserService userService;
    private final VisitedMountainRepository visitedMountainRepository;

    @Transactional(readOnly = true)
    public MountainBoard getByUser(User user) {
        return mountainBoardRepository.findByUser(user)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public MountainBoardView composeRegionMountainBoard(Long userId) {
        User user = userService.getUserById(userId);
        MountainBoard mountainBoard = getByUser(user);

        String month = LocalDate.now()
                .format(DateTimeFormatter.ISO_DATE)
                .substring(0, 7);

        List<MonthlyRegionBoard> monthlyRegions = monthlyRegionBoardRepository.findAllByUserIdAndMonthOrderByCountDesc(userId, month);

        long allUsersCount = userRepository.count();
        Long moreThanUser = mountainBoardRepository.countAllByTotalHikingCountGreaterThan(mountainBoard.getTotalHikingCount());

        Double percentage = (double)(allUsersCount - moreThanUser) / allUsersCount * 100;

        String mostVisited = regionBoardRepository.findTopByMountainBoardOrderByCountDesc(mountainBoard)
                .getRegionType()
                .getName();

        return MountainBoardView.of(monthlyRegions, mountainBoard, Double.parseDouble(String.format("%.0f", percentage)), mostVisited);
    }

    @Transactional
    public void upsertBoards(Long userId,
                             Long famousMountainId) {
        User user = userService.getUserById(userId);

        FamousMountain famousMountain = famousMountainService.getById(famousMountainId);

        if (visitedMountainRepository.existsByUserAndFamousMountain(user, famousMountain)) {
            return;
        }

        // 1) mountain board 적재
        MountainBoard mountainBoard = mountainBoardRepository.findByUser(user)
                .orElse(MountainBoard.initialize(user));
        mountainBoardRepository.save(mountainBoard.update(famousMountain));

        // 2) region board
        RegionBoard regionBoard = regionBoardRepository.findByMountainBoardAndRegionType(mountainBoard, famousMountain.getRegion()
                        .getRegionType())
                .orElse(RegionBoard.initialize(famousMountain, mountainBoard));
        regionBoardRepository.save(regionBoard.update());

        // 3) monthly board
        String yearMonth = LocalDate.now()
                .format(DateTimeFormatter.ISO_DATE)
                .substring(0, 7);
        MonthlyRegionBoard monthlyRegionBoard = monthlyRegionBoardRepository.findByRegionBoardAndMonth(regionBoard, yearMonth)
                .orElse(MonthlyRegionBoard.initialize(regionBoard, userId));
        monthlyRegionBoardRepository.save(monthlyRegionBoard.update());

        // 4) visited
        visitedMountainRepository.save(VisitedMountain.of(user, famousMountain));
    }
}
