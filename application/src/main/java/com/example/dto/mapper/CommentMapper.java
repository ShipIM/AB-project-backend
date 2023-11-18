package com.example.dto.mapper;

import com.example.dto.comment.request.CreateComment;
import com.example.dto.comment.response.ResponseComment;
import com.example.dto.comment.response.ResponseCommentContent;
import com.example.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    ResponseComment ToResponseComment(Comment comment);

    ResponseCommentContent ToResponseCommentContent(Comment comment);

    List<ResponseCommentContent> ToResponseCommentContent(List<Comment> comment);

    @Mapping(target = "resource.id", source = "resourceId")
    Comment ToCommentEntity(CreateComment comment);
}
