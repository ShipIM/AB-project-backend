package com.example.service;

import com.example.model.entity.Content;
import com.example.repository.ContentRepository;
import com.querydsl.core.types.Predicate;
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

    public List<Content> getContent(Predicate predicate) {
        Sort sort = Sort.by(Sort.Order.desc("filename"));
        List<Content> result = new ArrayList<>();
        contentRepository.findAll(predicate, sort).forEach(result::add);

        return result;
    }

    public List<Content> createContent(List<Content> contents, long resourceId) {
        contents.forEach(content -> content.setResource(resourceService.getResourceById(resourceId)));

        return contentRepository.saveAll(contents);
    }

}
