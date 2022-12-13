package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.repository.ScrapRepository;
import com.example.hibuddy.api.domain.support.ScrapResource;
import com.example.hibuddy.api.exception.DuplicatedScrapException;
import com.example.hibuddy.api.interfaces.request.ScrapMgetRequest;
import com.example.hibuddy.api.interfaces.request.ScrapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;

    @Transactional
    public Scrap save(ScrapRequest request) {
        if (existsByResourceAndUserId(request.getResourceId(), request.getResourceType(), request.getUserId())) {
            throw new DuplicatedScrapException();
        }

        // TODO: UserBoard 생성 뒤 이벤트 생성
        return scrapRepository.save(Scrap.of(request));
    }

    @Transactional(readOnly = true)
    public boolean existsByResourceAndUserId(Long resourceId,
                                             ScrapResource resourceType,
                                             Long userId) {
        return scrapRepository.existsByResourceIdAndUserIdAndResourceType(resourceId, userId, resourceType);
    }

    @Transactional(readOnly = true)
    public List<Scrap> getScrapsByResourceIdsAndType(ScrapMgetRequest request) {
        return scrapRepository.findAllByResourceIdInAndResourceType(request.getResourceIds(), request.getResourceType());
    }

    @Transactional(readOnly = true)
    public List<Scrap> getScrapsByUserId(Long userId) {
        return scrapRepository.findAllByUserId(userId);
    }

    @Transactional
    public void deleteById(Long id) {
        scrapRepository.deleteById(id);
    }
}
