package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Companion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompanionRepository extends JpaRepository<Companion, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Companion> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Page<Companion> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    List<Companion> findAllByIdIn(List<Long> ids);
}
