package com.example.service;

import com.example.model.entity.Subject;
import com.example.repository.SubjectRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;

    public List<Subject> findAll(Predicate predicate)
    {
        var sort = Sort.by(Sort.Order.desc("name"));
        List<Subject> result = new ArrayList<>();
        subjectRepository.findAll(predicate, sort).forEach(result::add);
        return result;
    }

    public Subject create(Subject subject)
    {
        subject.setCourse(courseService.getById(subject.getCourse().getId()));

        return subjectRepository.save(subject);
    }
  
    public Subject getSubjectById(long id) {
        return subjectRepository.getReferenceById(id);
    }
}
