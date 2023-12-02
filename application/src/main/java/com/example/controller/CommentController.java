package com.example.controller;

import com.example.dto.comment.request.CreateComment;
import com.example.dto.comment.response.ResponseComment;
import com.example.dto.mapper.CommentMapper;
import com.example.dto.page.request.PagingDto;
import com.example.model.entity.Comment;
import com.example.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "comments", description = "Контроллер для работы с комментариями")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/resources/{resourceId}/comments")
    @Operation(description = "Получить все комментарии по id ресурса")
    public Page<ResponseComment> getComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор ресурса должен быть положительным числом типа long")
            String resourceId,
            @Valid PagingDto pagingDto) {
        Page<Comment> comments = commentService.getCommentsByResource(Long.parseLong(resourceId),
                pagingDto.formPageRequest());

        return comments.map(commentMapper::ToResponseComment);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий")
    public ResponseComment createComment(@RequestBody @Valid CreateComment createComment) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        createComment.setAuthor(userDetails.getUsername());

        var comment = commentMapper.ToCommentEntity(createComment);

        var responseComment = commentService.create(comment);

        return commentMapper.ToResponseComment(responseComment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить комментарий по id")
    public void deleteComment(@PathVariable
                                         @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                                                 message = "Идентификатор комментария должен быть положительным числом типа long")
                                         String commentId) {
        commentService.delete((Long.parseLong(commentId)));
    }
}
