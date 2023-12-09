package com.example.dto.mapper;

import com.example.dto.comment.request.CommentCreateDto;
import com.example.dto.comment.request.CommentEditRequestDto;
import com.example.dto.comment.response.CommentResponseDto;
import com.example.model.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponseDto toResponseComment(CommentEntity comment);

    List<CommentResponseDto> toResponseCommentContent(List<CommentEntity> comment);

    CommentEntity toCommentEntity(CommentCreateDto comment);

    @Mapping(target = "id", source = "comment.commentId")
    CommentEntity toCommentEntity(CommentEditRequestDto comment);
}
