package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.AlarmObject;
import com.example.hibuddy.api.domain.support.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "alarm", indexes = { @Index(name = "user_id_index", columnList = "userId")})
public class Alarm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private AlarmType alarmType;

    private String message;

    @Embedded
    private AlarmObject object;

    private boolean userRead;

    public void readAlarm() {
        this.userRead = true;
    }
}
