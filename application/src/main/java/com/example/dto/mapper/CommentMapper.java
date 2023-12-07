package com.example.dto.mapper;

import com.example.dto.comment.request.CommentCreateDto;
import com.example.dto.comment.request.ResourceCommentCreateDto;
import com.example.dto.comment.response.CommentResponseDto;
import com.example.dto.comment.response.ResourceCommentResponseDto;
import com.example.model.entity.CommentEntity;
import com.example.model.entity.ResourceCommentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponseDto ToResponseComment(CommentEntity comment);

    List<CommentResponseDto> ToResponseCommentContent(List<CommentEntity> comment);

    CommentEntity ToCommentEntity(CommentCreateDto comment);
}
