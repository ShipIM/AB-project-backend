package com.example.controller;

import com.example.dto.course.response.CourseResponseDto;
import com.example.dto.mapper.CourseMapper;
import com.example.dto.page.request.PagingDto;
import com.example.model.entity.Course;
import com.example.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "courses", description = "Контроллер для работы с курсами")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping
    @Operation(description = "Получить все существующие курсы")
    public List<CourseResponseDto> getCourses() {
        List<Course> courses = courseService.getCourses();

        return courseMapper.mapCourseListToDtoList(courses);
    }
}
