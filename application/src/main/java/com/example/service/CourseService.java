package com.example.service;

import com.example.model.entity.Course;
import com.example.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getCourses() {
        Sort sort = Sort.by(Sort.Order.desc("name"));

        return courseRepository.findAll(sort);
    }

    public Course getById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курса с таким id не существует"));
    }
}
