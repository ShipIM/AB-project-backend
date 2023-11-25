package com.example.dto.mapper;

import com.example.dto.subject.request.CreateSubject;
import com.example.dto.subject.response.ResponseSubject;
import com.example.dto.subject.response.ResponseSubjectWithoutCourse;
import com.example.model.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SubjectMapper {
    ResponseSubjectWithoutCourse ToResponseSubjectWithoutCourse(Subject subject);

    List<ResponseSubjectWithoutCourse> ToListResponseSubjectWithoutCourse(List<Subject> subjects);

    ResponseSubject ToResponseSubject(Subject subject);

    List<ResponseSubject> ToListResponseSubject(List<Subject> subjects);

    Subject ToSubjectEntity(CreateSubject subject);
}
