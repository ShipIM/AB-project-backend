package com.example.controller;

import com.example.dto.mapper.SubjectMapper;
import com.example.dto.subject.request.CreateSubject;
import com.example.dto.subject.request.GetSubjectByCourse;
import com.example.dto.subject.response.ResponseSubject;
import com.example.dto.subject.response.ResponseSubjectWithoutCourse;
import com.example.model.entity.Subject;
import com.example.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@Tag(name = "subjects", description = "Controller for working with courses")
@RequiredArgsConstructor
@Validated
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @GetMapping("/courses/{courseId}")
    @Operation(description = "To get all subjects by course id")
    public List<ResponseSubjectWithoutCourse> getCourseSubjects(
            @PathVariable
            @Min(value = 1, message = "Id курса не может быть меньше 1 или равным null")
            Long courseId) {

        List<Subject> subjects = subjectService.findAll(GetSubjectByCourse.toPredicate(courseId));

        return subjectMapper.ToListResponseSubjectWithoutCourse(subjects);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "To create subject")
    public ResponseSubject CreateSubject(@RequestBody @Valid CreateSubject createSubject) {
        var subject = subjectMapper.ToSubjectEntity(createSubject);

        var responseSubject = subjectService.create(subject);

        return subjectMapper.ToResponseSubject(responseSubject);
    }
}
