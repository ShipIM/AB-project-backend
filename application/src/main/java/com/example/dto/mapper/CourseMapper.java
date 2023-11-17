package com.example.dto.mapper;

import com.example.dto.course.CourseResponseDto;
import com.example.model.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponseDto mapToCourseDto(Course course);

    List<CourseResponseDto> mapCourseListToDtoList(List<Course> courses);
}
