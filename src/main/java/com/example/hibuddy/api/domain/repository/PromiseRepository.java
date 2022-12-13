package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Promise;
import com.example.hibuddy.api.domain.support.PromiseStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PromiseRepository extends JpaRepository<Promise, Long> {
    default List<Promise> getPromisesByUserId(Long userId) {
        return findAllByFirstPromiseUserIdAndPromiseStatusTypeOrSecondPromiseUserIdAndPromiseStatusType(userId, PromiseStatusType.BOTH, userId, PromiseStatusType.BOTH);
    }

    default Optional<Promise> getPromise(Long userId, Long roomId) {
        return findByFirstPromiseUserIdAndRoomId(userId, roomId);
    }

    @Query(value = "select count(distinct companion_id) from promise p where p.promise_status_type = :promiseStatusType and (p.first_promise_user_id = :userId or p.second_promise_user_id = :userId)", nativeQuery = true)
    Long countPromises(@Param("userId") Long userId, @Param("promiseStatusType") String promiseStatusType);

    default boolean existsPromise(Long userId, Long roomId) {
        return existsByFirstPromiseUserIdAndRoomIdOrSecondPromiseUserIdAndRoomId(userId, roomId, userId, roomId);
    }

    List<Promise> findAllByFirstPromiseUserIdAndPromiseStatusTypeOrSecondPromiseUserIdAndPromiseStatusType(Long firstUserId, PromiseStatusType firstType, Long secondUserId, PromiseStatusType secondType);

    Optional<Promise> findByFirstPromiseUserIdAndRoomId(Long firstUserId, Long roomId);

    boolean existsByFirstPromiseUserIdAndRoomIdOrSecondPromiseUserIdAndRoomId(Long firstUserId, Long firstChainRoomId, Long secondUserId, Long secondChainRoomId);
}
