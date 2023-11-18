package com.example.controller;

import com.example.dto.comment.request.CreateComment;
import com.example.dto.comment.request.GetCommentsByResource;
import com.example.dto.comment.response.ResponseComment;
import com.example.dto.comment.response.ResponseCommentContent;
import com.example.dto.mapper.CommentMapper;
import com.example.model.entity.Comment;
import com.example.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "comments", description = "Controller for working with comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/resources/{resourceId}")
    @Operation(description = "To get all comments by resource id")
    public List<ResponseCommentContent> getCourseSubjects(
            @PathVariable
            @Min(value = 1, message = "Id предмета не может быть меньше 1 или равным null")
            Long resourceId) {

        List<Comment> comments = commentService.findAll(GetCommentsByResource.toPredicate(resourceId));

        return commentMapper.ToResponseCommentContent(comments);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "To create comment")
    public ResponseComment CreateSubject(@RequestBody @Valid CreateComment createComment) {
        var comment = commentMapper.ToCommentEntity(createComment);

        var responseComment = commentService.create(comment);

        return commentMapper.ToResponseComment(responseComment);
    }
}
