package com.example.dto.mapper;

import com.example.dto.commentaudit.CommentAuditResponseDto;
import com.example.model.entity.CommentAudit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentAuditMapper {
    CommentAuditResponseDto mapToDto(CommentAudit commentAudit);
}
