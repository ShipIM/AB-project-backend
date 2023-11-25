package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Course;
import com.example.model.entity.Subject;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;

    public Page<Subject> getSubjectsByCourse(long courseId, PageRequest pageRequest) {
        var sort = Sort.by(Sort.Order.desc("name"));

        return subjectRepository.findAllByCourseId(courseId, pageRequest.withSort(sort));
    }

    public Subject create(Subject subject) {
        Course course = courseService.getById(subject.getCourseId());

        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(long id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Предмета с таким идентификатором не существует"));
    }
}
