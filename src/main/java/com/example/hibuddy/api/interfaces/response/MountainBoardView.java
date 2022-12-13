package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.MonthlyRegionBoard;
import com.example.hibuddy.api.domain.MountainBoard;
import com.example.hibuddy.api.domain.RegionBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MountainBoardView {
    private Long totalHikingCount;
    private Double totalHeightCount;
    private Double percentage;
    private Double monthlyHeight;
    private List<MonthlyView> monthlyTop3;
    private String mostVisited;

    public static MountainBoardView of(List<MonthlyRegionBoard> monthlyBoards,
                                       MountainBoard mountainBoard,
                                       Double percentage,
                                       String mostVisited) {

        return MountainBoardView.builder()
                .totalHikingCount(mountainBoard.getTotalHikingCount())
                .totalHeightCount(mountainBoard.getTotalHeightCount())
                .percentage(percentage)
                .monthlyHeight(monthlyBoards.stream()
                        .mapToDouble(board -> board.getRegionBoard()
                                .getHeight())
                        .sum())
                .monthlyTop3(monthlyBoards.subList(0, Math.min(monthlyBoards.size(), 3))
                        .stream()
                        .map(MonthlyView::of)
                        .collect(Collectors.toList()))
                .mostVisited(mostVisited)
                .build();
    }
}
