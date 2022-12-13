package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.ScrapResource;
import com.example.hibuddy.api.interfaces.request.ScrapRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "scrap", indexes = {
        @Index(name = "resource_user_index", columnList = "resourceId, userId, resourceType"),
        @Index(name = "resource_id_index", columnList = "resourceId"),
        @Index(name = "resource_index", columnList = "resourceId, resourceType"),
        @Index(name = "user_id_index", columnList = "userId")})
public class Scrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long resourceId;

    @Enumerated(value = EnumType.STRING)
    private ScrapResource resourceType;

    public static Scrap of(ScrapRequest request) {
        return Scrap.builder()
                .userId(request.getUserId())
                .resourceId(request.getResourceId())
                .resourceType(request.getResourceType())
                .build();
    }
}
