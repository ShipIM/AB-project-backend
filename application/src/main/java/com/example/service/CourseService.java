package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Course;
import com.example.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getCourses() {
        Sort sort = Sort.by(Sort.Order.asc("name"));

        Iterable<Course> iterableCourses = courseRepository.findAll(sort);
        List<Course> result = new ArrayList<>();
        iterableCourses.forEach(result::add);

        return result;
    }

    public Course getById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курса с таким идентификатором не существует"));
    }

    public boolean isCourseExists(long id) {
        return courseRepository.existsById(id);
    }
}
