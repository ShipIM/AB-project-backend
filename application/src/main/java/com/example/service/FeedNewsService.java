package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.ContentEntity;
import com.example.model.entity.FeedNewsEntity;
import com.example.repository.FeedNewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedNewsService {
    private final FeedNewsRepository feedNewsRepository;
    private final UserService userService;
    private final ContentService contentService;

    public Page<FeedNewsEntity> getFeedNewsPage(Pageable pageable) {
        long total = feedNewsRepository.count();
        List<FeedNewsEntity> feedNewsList = feedNewsRepository.findAll(
                pageable.getPageSize(),
                pageable.getPageNumber()
        );

        return new PageImpl<>(feedNewsList, pageable, total);
    }

    public FeedNewsEntity getById(long id) {
        return feedNewsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Новости с таким идентификатором не существует"));
    }

    @Transactional
    public FeedNewsEntity createOrUpdate(FeedNewsEntity feedNews, List<ContentEntity> contents) {
        if (!userService.isUserExists(feedNews.getAuthorId())) {
            throw new EntityNotFoundException("Пользователя с таким идентификатором не существует");
        }

        feedNews = feedNewsRepository.save(feedNews);
        contentService.createFeedNewsContent(contents, feedNews.getId());

        return feedNews;
    }

    public boolean isFeedNewsExists(long id) {
        return feedNewsRepository.existsById(id);
    }

    public void delete(long id) {
        if (isFeedNewsExists(id)) {
            feedNewsRepository.deleteById(id);
            return;
        }

        throw new EntityNotFoundException("Новости с таким идентификатором не существует");
    }
}
