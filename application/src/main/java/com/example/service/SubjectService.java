package com.example.service;

import com.example.model.entity.Subject;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject getSubjectById(long id) {
        return subjectRepository.getReferenceById(id);
    }
}
