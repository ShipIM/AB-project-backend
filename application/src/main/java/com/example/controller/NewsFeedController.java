package com.example.controller;

import com.example.dto.feedNews.request.FeedNewsCreateDto;
import com.example.dto.feedNews.response.FeedNewsResponseDto;
import com.example.dto.mapper.ContentMapper;
import com.example.dto.mapper.FeedNewsMapper;
import com.example.dto.page.request.PagingDto;
import com.example.service.FeedNewsService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "feed", description = "Контроллер для работы с лентой новостей")
@RequiredArgsConstructor
@Validated
public class NewsFeedController {

    private final FeedNewsService feedNewsService;
    private final FeedNewsMapper feedNewsMapper;
    private final ContentMapper contentMapper;
    private final UserService userService;

    @GetMapping("/feeds/{newsId}")
    @Operation(description = "Получить новость по идентификатору")
    public FeedNewsResponseDto getFeedNews(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор новости должен быть положительным числом типа long")
            String newsId) {
        var feedNews = feedNewsService.getById(Long.parseLong(newsId));
        var feedNewsResponse = feedNewsMapper.mapToResponseDto(feedNews);
        feedNewsResponse.setAuthor(userService.getById(feedNewsResponse.getAuthorId()).getLogin());

        return feedNewsResponse;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/feeds")
    @Operation(description = "Создать новую новость")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedNewsResponseDto createFeedNews(
            @RequestPart(value = "feedNews")
            @Valid
            FeedNewsCreateDto feedNews,
            @RequestPart(value = "files")
            List<MultipartFile> files) {
        var feedNewsEntity = feedNewsMapper.mapToEntity(feedNews);
        var contents = contentMapper.mapToFeedNewsContentList(files);

        feedNewsEntity = feedNewsService.create(feedNewsEntity, contents);
        var feedNewsResponse = feedNewsMapper.mapToResponseDto(feedNewsEntity);
        feedNewsResponse.setAuthor(userService.getById(feedNewsResponse.getAuthorId()).getLogin());

        return feedNewsResponse;
    }

    @GetMapping("/feeds")
    @Operation(description = "Получить страницу новостей")
    public Page<FeedNewsResponseDto> getFeedNews(
            @Valid PagingDto pagingDto) {
        var feedNewsList = feedNewsService.getFeedNewsPage(pagingDto.formPageRequest());

        var feedNewsResponseList = feedNewsList.map(feedNewsMapper::mapToResponseDto);

        for (var feedNews : feedNewsResponseList) {
            var login = userService.getById(feedNews.getAuthorId()).getLogin();
            feedNews.setAuthor(login);
        }

        return feedNewsResponseList;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/feed/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить новость по идентификатору")
    public void deleteFeedNews(@PathVariable
                               @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                       message = "Идентификатор новости должен быть положительным числом типа long")
                               String newsId) {
        feedNewsService.delete(Long.parseLong(newsId));
    }
}
