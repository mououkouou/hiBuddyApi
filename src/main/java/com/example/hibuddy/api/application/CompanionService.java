package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.exception.CompanionNotFoundException;
import com.example.hibuddy.api.interfaces.request.CompanionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanionService {
    private final CompanionRepository companionRepository;
    private final UserService userService;

    @Transactional
    public Companion save(final CompanionRequest request) {
        User user = userService.getUserById(request.getUserId());
        return companionRepository.save(Companion.of(user, request));
    }

    public Companion getCompanionById(final Long id) {
        return companionRepository.findById(id)
                .orElseThrow(CompanionNotFoundException::new);
    }

    public List<Companion> getCompanions(final int page, final int size) {
        return companionRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending())).getContent();
    }
}
