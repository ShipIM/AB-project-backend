package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Content;
import com.example.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ResourceService resourceService;

    public List<Content> getContentsByResource(long resourceId) {
        Sort sort = Sort.by(Sort.Order.asc("filename"));

        Iterable<Content> iterableContents = contentRepository.findAllByResourceId(resourceId, sort);
        List<Content> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }

    public List<Content> createContent(List<Content> contents, long resourceId) {
        if (!resourceService.isResourceExists(resourceId))
            throw new EntityNotFoundException("Ресурса с таким идентификатором не существует");

        contents.forEach(content -> content.setResourceId(resourceId));

        Iterable<Content> iterableContents = contentRepository.saveAll(contents);
        List<Content> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }
}