package com.example.service;

import com.example.model.entity.ContentEntity;
import com.example.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<ContentEntity> getContentsByResource(long resourceId) {
        return contentRepository.findAllByResourceId(resourceId);
    }

    public List<ContentEntity> getContentsByFeedNewsId(long feedNewsId) {
        return contentRepository.findAllByFeedNewsId(feedNewsId);
    }

    @Transactional
    public List<ContentEntity> createContent(List<ContentEntity> contents) {
        Iterable<ContentEntity> iterableContents = contentRepository.saveAll(contents);
        List<ContentEntity> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }

    @Transactional
    public List<ContentEntity> createResourceContent(List<ContentEntity> contents, Long resourceId) {
        var result = createContent(contents);

        for (var content: result){
            contentRepository.createResourceContent(resourceId, content.getId());
        }

        return result;
    }

    @Transactional
    public List<ContentEntity> createFeedNewsContent(List<ContentEntity> contents, Long feedNewsId) {
        var result = createContent(contents);

        for (var content: result){
            contentRepository.createFeedNewsContent(feedNewsId, content.getId());
        }

        return result;
    }
}
