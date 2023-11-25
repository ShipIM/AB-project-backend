package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Course;
import com.example.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Page<Course> getCourses(PageRequest pageRequest) {
        Sort sort = Sort.by(Sort.Order.desc("name"));

        return courseRepository.findAll(pageRequest.withSort(sort));
    }

    public Course getById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Курса с таким id не существует"));
    }
}
