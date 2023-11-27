package com.example.service;

import com.example.model.entity.Content;
import com.example.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;

    public List<Content> getContentsByResource(long resourceId) {
        Sort sort = Sort.by(Sort.Order.asc("filename"));

        Iterable<Content> iterableContents = contentRepository.findAllByResourceId(resourceId, sort);
        List<Content> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }

    @Transactional
    public List<Content> createContent(List<Content> contents, long resourceId) {
        contents.forEach(content -> content.setResourceId(resourceId));

        Iterable<Content> iterableContents = contentRepository.saveAll(contents);
        List<Content> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }
}
