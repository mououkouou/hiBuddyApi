package com.example.hibuddy.api.application;

import com.example.hibuddy.api.application.support.QuizDto;
import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.FamousMountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamousMountainService {

    private final FamousMountainRepository famousMountainRepository;
    private final UserService userService;



    @Transactional(readOnly = true)
    public FamousMountain getById(Long id) {
        return famousMountainRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public QuizDto generateRandomQuiz() {
        Pair<Long, Long> randomIds = generateRandomIds();

        return new QuizDto(getById(randomIds.getFirst()), getById(randomIds.getSecond()));
    }

    private Pair<Long, Long> generateRandomIds() {
        Long winner = (long)(Math.random() * 100) + 1;
        Long loser = (long)(Math.random() * 100) + 1;

        if (winner.equals(loser)) {
            return generateRandomIds();
        }

        return Pair.of(winner, loser);
    }

    @Transactional(readOnly = true)
    public List<FamousMountain> findAllMountains(Long userId) {
        return famousMountainRepository.findAll();
    }
}
