package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.PromiseStatusType;
import com.example.hibuddy.api.interfaces.request.PromiseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "promise", indexes = {
        @Index(name = "company_user_index", columnList = "companionId, firstPromiseUserId, secondPromiseUserId"),
        @Index(name = "company_first_user_index", columnList = "firstPromiseUserId, companionId"),
        @Index(name = "promise_user_index", columnList = "firstPromiseUserId, secondPromiseUserId, promiseStatusType")
})
public class Promise extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long firstPromiseUserId;

    private Long secondPromiseUserId;

    private Long companionId;

    private Long roomId;

    @Enumerated(value = EnumType.STRING)
    private PromiseStatusType promiseStatusType;

    public static Promise of(PromiseRequest request, Long userId) {
        return Promise.builder()
                .companionId(request.getCompanionId())
                .roomId(request.getRoomId())
                .firstPromiseUserId(userId)
                .promiseStatusType(PromiseStatusType.ONE)
                .build();
    }

    public void setSecondPromiseUserId(Long userId) {
        if (this.firstPromiseUserId != null) {
            this.secondPromiseUserId = userId;
            this.promiseStatusType = PromiseStatusType.BOTH;
        }
    }

}
