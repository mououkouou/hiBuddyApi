package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.support.ScrapResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsByResourceIdAndUserIdAndResourceType(Long resourceId, Long userId, ScrapResource resourceType);
    List<Scrap> findAllByUserId(Long userId);
    List<Scrap> findAllByResourceIdInAndResourceType(List<Long> resourceIds, ScrapResource resourceType);
}
