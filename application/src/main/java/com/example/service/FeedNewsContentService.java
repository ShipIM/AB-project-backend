package com.example.service;

import com.example.model.entity.FeedNewsContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedNewsContentService {
    private final FeedNewsContentRepository contentRepository;

    public List<FeedNewsContent> getContentsByFeedNewsId(long feedNewsId) {
        return contentRepository.findAllByFeedNewsId(feedNewsId);
    }

    @Transactional
    public List<FeedNewsContent> createContent(List<FeedNewsContent> contents, long feedNewsId) {
        contents.forEach(content -> content.setFeedNewsId(feedNewsId));

        Iterable<FeedNewsContent> iterableContents = contentRepository.saveAll(contents);
        List<FeedNewsContent> result = new ArrayList<>();
        iterableContents.forEach(result::add);

        return result;
    }
}
