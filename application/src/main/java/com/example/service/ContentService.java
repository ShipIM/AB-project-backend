package com.example.service;

import com.example.model.entity.Content;
import com.example.model.entity.Resource;
import com.example.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ResourceService resourceService;

    public Page<Content> getContentsByResource(long resourceId, PageRequest pageRequest) {
        Sort sort = Sort.by(Sort.Order.desc("filename"));

        return contentRepository.findAllByResourceId(resourceId, pageRequest.withSort(sort));
    }

    public List<Content> createContent(List<Content> contents, long resourceId) {
        Resource resource = resourceService.getResourceById(resourceId);
        contents.forEach(content -> content.setResourceId(resourceId));

        Iterable<Content> iterableContents = contentRepository.saveAll(contents);
        List<Content> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }

}
