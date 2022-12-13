package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Alarm;
import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.AlarmRepository;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.support.AlarmObject;
import com.example.hibuddy.api.domain.support.AlarmType;
import com.example.hibuddy.api.exception.AlarmNotFoundException;
import com.example.hibuddy.api.exception.CompanionNotFoundException;
import com.example.hibuddy.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final UserRepository userRepository;

    private final CompanionRepository companionRepository;

    private final ChatRepository chatRepository;

    @Transactional(readOnly = true)
    public List<Alarm> getAlarmsByUserId(Long userId) {
        return alarmRepository.findAllByUserId(userId).orElseThrow(AlarmNotFoundException::new);
    }

    @Transactional
    public void save(User user, Long oppositeId, Long objectId, AlarmType alarmType) {
        User opposite = userRepository.findById(oppositeId).orElseThrow(UserNotFoundException::new);

        if (alarmType == AlarmType.COMPANION_PROMISE) {
            saveCompanionPromiseAlarm(user, opposite, objectId, alarmType);
        } else {
            // TODO :: 크루 생성 후 크루 알람 만들기
        }

    }

    @Transactional
    public void readAlarm(Long alarmId) {
        alarmRepository.findById(alarmId).ifPresent(Alarm::readAlarm);
    }

    private void saveCompanionPromiseAlarm(User user, User opposite, Long objectId, AlarmType alarmType) {
        Companion companion = companionRepository.findById(objectId).orElseThrow(CompanionNotFoundException::new);

        User companionWriter = companion.getUser().getId() == user.getId() ? user : opposite;
        User companionContacter = companion.getUser().getId() == user.getId() ? opposite : user;

        Room room = chatRepository.getRoom(companionWriter, companionContacter, companion);

        AlarmObject userObject = AlarmObject.builder()
                .objectId(companion.getId())
                .objectProfileUrl(opposite.getProfilePictureUrl())
                .roomId(room.getId())
                .build();

        AlarmObject oppsiteObject = AlarmObject.builder()
                .objectId(companion.getId())
                .objectProfileUrl(user.getProfilePictureUrl())
                .roomId(room.getId())
                .build();

        Alarm userAlarm = Alarm.builder()
                .userId(user.getId())
                .alarmType(alarmType)
                .message(AlarmType.makeMessage(alarmType, opposite.getNickname()))
                .userRead(false)
                .object(userObject)
                .build();

        Alarm oppositeAlarm = Alarm.builder()
                .userId(opposite.getId())
                .alarmType(alarmType)
                .message(AlarmType.makeMessage(alarmType, user.getNickname()))
                .userRead(false)
                .object(oppsiteObject)
                .build();

        alarmRepository.saveAll(List.of(userAlarm, oppositeAlarm));
    }
}
