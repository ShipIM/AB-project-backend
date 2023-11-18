package com.example.dto.subject.response;

import com.example.dto.course.CourseResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSubject {
    private Long id;
    private String name;
    private CourseResponseDto course;
}
