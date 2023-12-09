package com.example.controller;

import com.example.dto.comment.request.CommentCreateDto;
import com.example.dto.comment.response.CommentResponseDto;
import com.example.dto.mapper.CommentMapper;
import com.example.dto.page.request.PagingDto;
import com.example.model.entity.CommentEntity;
import com.example.service.CommentService;
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
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping("/comments/{commentId}")
    @Operation(description = "Получить комментарий по id")
    public CommentResponseDto getComment(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String commentId) {
        var comment = commentService.getById(Long.parseLong(commentId));

        var responseComment = commentMapper.ToResponseComment(comment);
        var login = userService.getById(Long.parseLong(responseComment.getAuthorId())).getLogin();
        responseComment.setAuthor(login);

        return responseComment;
    }

    @GetMapping("/resources/{resourceId}/comments")
    @Operation(description = "Получить все комментарии по id ресурса")
    public Page<CommentResponseDto> getResourceComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resourceId,
            @Valid PagingDto pagingDto) {
        Page<CommentEntity> comments = commentService.getCommentsByResource(Long.parseLong(resourceId), pagingDto.formPageRequest());
        var responseComments = comments.map(commentMapper::ToResponseComment);

        for (var comment : responseComments) {
            var login = userService.getById(Long.parseLong(comment.getAuthorId())).getLogin();
            comment.setAuthor(login);
        }

        return responseComments;
    }

    @GetMapping("/feeds/{feedNewsId}/comments")
    @Operation(description = "Получить все комментарии по id новости")
    public Page<CommentResponseDto> getFeedNewsComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор новости должен быть положительным числом типа long")
            String feedNewsId,
            @Valid PagingDto pagingDto) {
        Page<CommentEntity> comments = commentService.getCommentsByFeedNewsId(Long.parseLong(feedNewsId), pagingDto.formPageRequest());
        var responseComments = comments.map(commentMapper::ToResponseComment);

        for (var comment : responseComments) {
            var login = userService.getById(Long.parseLong(comment.getAuthorId())).getLogin();
            comment.setAuthor(login);
        }

        return responseComments;
    }

    @GetMapping("/thread/{commentsId}")
    @Operation(description = "Получить тред комменатриев по id комментария")
    public Page<CommentResponseDto> getThreadComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор комментария должен быть положительным числом типа long")
            String commentsId,
            @Valid PagingDto pagingDto) {
        Page<CommentEntity> comments = commentService.getCommentsByCommentId(Long.parseLong(commentsId), pagingDto.formPageRequest());
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
    @Operation(description = "Создать комментарий к ресурсу")
    public CommentResponseDto createResourceComment(@RequestBody @Valid CommentCreateDto createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        var resourceId = Long.parseLong(createComment.getSourceId());
        comment = commentService.createResourceComment(comment, resourceId);

        var responseComment = commentMapper.ToResponseComment(comment);
        responseComment.setAuthor(userService.getById(comment.getAuthorId()).getLogin());

        return responseComment;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/thread/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий треда")
    public CommentResponseDto createThreadComment(@RequestBody @Valid CommentCreateDto createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        var feedNewsId = Long.parseLong(createComment.getSourceId());
        comment = commentService.createThreadComment(comment, feedNewsId);

        var responseComment = commentMapper.ToResponseComment(comment);
        responseComment.setAuthor(userService.getById(comment.getAuthorId()).getLogin());

        return responseComment;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/feeds/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий к новости")
    public CommentResponseDto createFeedComment(@RequestBody @Valid CommentCreateDto createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        var feedNewsId = Long.parseLong(createComment.getSourceId());
        comment = commentService.createFeedNewsComment(comment, feedNewsId);

        var responseComment = commentMapper.ToResponseComment(comment);
        responseComment.setAuthor(userService.getById(comment.getAuthorId()).getLogin());

        return responseComment;
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить комментарий по id")
    public void deleteComment(@PathVariable
                              @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                      message = "Идентификатор комментария должен быть положительным числом типа long")
                              String commentId) {
        commentService.delete(Long.parseLong(commentId));
    }
}
