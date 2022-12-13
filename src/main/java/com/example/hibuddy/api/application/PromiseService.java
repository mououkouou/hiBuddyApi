package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.Promise;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.PromiseRepository;
import com.example.hibuddy.api.domain.support.AlarmType;
import com.example.hibuddy.api.domain.support.PromiseStatusType;
import com.example.hibuddy.api.exception.CompanionNotFoundException;
import com.example.hibuddy.api.exception.DuplicatedPromiseException;
import com.example.hibuddy.api.interfaces.request.MessageRequest;
import com.example.hibuddy.api.interfaces.request.PromiseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromiseService {

    private final PromiseRepository promiseRepository;

    private final CompanionRepository companionRepository;

    private final AlarmService alarmService;

    private final MessageService messageService;

    @Transactional
    public Promise save(final PromiseRequest promiseRequest, final User user) {
        if (existsPromise(user.getId(), promiseRequest.getRoomId())) throw new DuplicatedPromiseException();

        Optional<Promise> promise = promiseRepository.getPromise(promiseRequest.getOppositeUserId(), promiseRequest.getRoomId());
        promise.ifPresentOrElse(p ->
        {
            p.setSecondPromiseUserId(user.getId());
            Companion companion = companionRepository.findById(promiseRequest.getCompanionId()).orElseThrow(CompanionNotFoundException::new);
            companion.addFixedNumber();

            messageService.save(promiseRequest.getRoomId(), user.getNickname() + "님도 동행 확정을 눌렀어요 :) 모두 동행 확정을 눌러 동행이 확정 되었어요. 버디와 함께 즐거운 등산 되시길 바래요.", user);
            alarmService.save(user, promiseRequest.getOppositeUserId(), promiseRequest.getCompanionId(), AlarmType.COMPANION_PROMISE);
        }, () -> messageService.save(promiseRequest.getRoomId(), user.getNickname() + "님이 동행 확정을 눌렀어요 :) 양쪽 모두 동행 확정을 눌러야 동행이 확정되어요. 함께 동행 확정 버튼을 눌러 즐거운 등산을 떠나보아요.", user));

        return promise.orElseGet(() -> promiseRepository.save(Promise.of(promiseRequest, user.getId())));
    }

    public List<Companion> getPromisesById(final Long userId) {
        List<Long> companionIds = promiseRepository.getPromisesByUserId(userId)
                .stream().map(Promise::getCompanionId).collect(Collectors.toList());

        return companionRepository.findAllByIdIn(companionIds);
    }

    public Long countPromises(final Long userId) {
        return promiseRepository.countPromises(userId, "BOTH");
    }

    public boolean existsPromise(Long userId, Long roomId) {
        return promiseRepository.existsPromise(userId, roomId);
    }
}
