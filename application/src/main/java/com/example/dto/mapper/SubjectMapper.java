package com.example.dto.mapper;

import com.example.dto.subject.request.CreateSubject;
import com.example.dto.subject.response.ResponseSubject;
import com.example.model.entity.Subject;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SubjectMapper {

    ResponseSubject ToResponseSubject(Subject subject);

    List<ResponseSubject> ToListResponseSubject(List<Subject> subjects);

    Subject ToSubjectEntity(CreateSubject subject);
}
