package com.example.service;

import com.example.model.entity.ResourceContentEntity;
import com.example.repository.ResourceContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceContentService {
    private final ResourceContentRepository contentRepository;

    public List<ResourceContentEntity> getContentsByResource(long resourceId) {
        return contentRepository.findAllByResourceId(resourceId);
    }

    @Transactional
    public List<ResourceContentEntity> createContent(List<ResourceContentEntity> contents, long resourceId) {
        contents.forEach(content -> content.setResourceId(resourceId));

        Iterable<ResourceContentEntity> iterableContents = contentRepository.saveAll(contents);
        List<ResourceContentEntity> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }
}
