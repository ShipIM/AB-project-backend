package com.example.controller;

import com.example.dto.comment.request.CreateComment;
import com.example.dto.comment.response.ResponseComment;
import com.example.dto.comment.response.ResponseCommentContent;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
    public Page<ResponseCommentContent> getComments(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор комментария должен быть положительным числом типа long")
            String resourceId,
            PagingDto pagingDto) {
        PageRequest pageRequest = PageRequest.of(
                Integer.parseInt(pagingDto.getPageNumber()),
                Integer.parseInt(pagingDto.getPageSize())
        );

        Page<Comment> comments = commentService.getCommentsByResource(Long.parseLong(resourceId), pageRequest);

        return comments.map(commentMapper::ToResponseCommentContent);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создать комментарий")
    public ResponseComment createComment(@RequestBody @Valid CreateComment createComment) {

        var comment = commentMapper.ToCommentEntity(createComment);

        var responseComment = commentService.create(comment);

        return commentMapper.ToResponseComment(responseComment);
    }
}
