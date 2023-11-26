package com.example.dto.mapper;

import com.example.dto.comment.request.CreateComment;
import com.example.dto.comment.response.ResponseComment;
import com.example.model.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    ResponseComment ToResponseComment(Comment comment);

    List<ResponseComment> ToResponseCommentContent(List<Comment> comment);

    Comment ToCommentEntity(CreateComment comment);
}
