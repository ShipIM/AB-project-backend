package com.example.controller;

import com.example.dto.comment.request.CommentCreateDto;
import com.example.dto.comment.request.ResourceCommentCreateDto;
import com.example.dto.comment.response.CommentResponseDto;
import com.example.dto.comment.response.ResourceCommentResponseDto;
import com.example.dto.mapper.CommentMapper;
import com.example.dto.page.request.PagingDto;
import com.example.model.entity.CommentEntity;
import com.example.model.entity.FeedNewsComment;
import com.example.model.entity.ResourceCommentEntity;
import com.example.service.FeedNewsCommentService;
import com.example.service.ResourceCommentService;
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

@RestController
@Tag(name = "comments", description = "Контроллер для работы с комментариями")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final ResourceCommentService resourceCommentService;
    private final FeedNewsCommentService feedNewsCommentService;

    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping("/resources/{resourceId}/comments")
    @Operation(description = "Получить все комментарии по id ресурса")
    public Page<CommentResponseDto> getResourceComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resourceId,
            @Valid PagingDto pagingDto) {
        Page<CommentEntity> comments = resourceCommentService.getCommentsByResource(Long.parseLong(resourceId), pagingDto.formPageRequest());
        var responseComments = comments.map(commentMapper::ToResponseComment);

        for (var comment : responseComments) {
            var login = userService.getById(Long.parseLong(comment.getAuthorId())).getLogin();
            comment.setAuthor(login);
        }

        return responseComments;
    }

    @GetMapping("/feed/{feedNewsId}/comments")
    @Operation(description = "Получить все комментарии по id новости")
    public Page<CommentResponseDto> getFeedNewsComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор новости должен быть положительным числом типа long")
            String feedNewsId,
            @Valid PagingDto pagingDto) {
        Page<CommentEntity> comments = feedNewsCommentService.getCommentsByFeedNewsId(Long.parseLong(feedNewsId), pagingDto.formPageRequest());
        var responseComments = comments.map(commentMapper::ToResponseComment);

        for (var comment : responseComments) {
            var login = userService.getById(Long.parseLong(comment.getAuthorId())).getLogin();
            comment.setAuthor(login);
        }

        return responseComments;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/resources/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий")
    public CommentResponseDto createResourceComment(@RequestBody @Valid CommentCreateDto createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        comment = resourceCommentService.create(comment);
        var responseComment = commentMapper.ToResponseComment(comment);
        responseComment.setAuthor(userService.getById(comment.getAuthorId()).getLogin());

        return responseComment;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/feed/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий")
    public CommentResponseDto createFeedComment(@RequestBody @Valid CommentCreateDto createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        comment = feedNewsCommentService.create(comment);
        var responseComment = commentMapper.ToResponseComment(comment);
        responseComment.setAuthor(userService.getById(comment.getAuthorId()).getLogin());

        return responseComment;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/resources/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить комментарий по id")
    public void deleteResourceComment(@PathVariable
                              @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                      message = "Идентификатор комментария должен быть положительным числом типа long")
                              String commentId) {
        resourceCommentService.delete(Long.parseLong(commentId));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/feed/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить комментарий по id")
    public void deleteFeedComment(@PathVariable
                              @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                      message = "Идентификатор комментария должен быть положительным числом типа long")
                              String commentId) {
        feedNewsCommentService.delete(Long.parseLong(commentId));
    }
}
