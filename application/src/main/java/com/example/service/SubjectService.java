package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Subject;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;

    public Page<Subject> getSubjectsByCourse(long courseId, Pageable pageable) {
        long total = subjectRepository.countAllByCourseId(courseId);
        List<Subject> subjects = subjectRepository.findAllByCourseId(
                courseId,
                pageable.getPageSize(),
                pageable.getPageNumber());

        return new PageImpl<>(subjects, pageable, total);
    }

    public Subject create(Subject subject) {
        if (!courseService.isCourseExists(subject.getCourseId())) {
            throw new EntityNotFoundException("Курса с таким идентификатором не существует");
        }

        return subjectRepository.save(subject);
    }

    public Subject getSubjectById(long id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Предмета с таким идентификатором не существует"));
    }

    public void delete(long id) {
        if (!isSubjectExists(id)) {
            throw new EntityNotFoundException("Предмета с таким идентификатором не существует");
        }

        subjectRepository.deleteById(id);
    }

    public boolean isSubjectExists(long id) {
        return subjectRepository.existsById(id);
    }
}
