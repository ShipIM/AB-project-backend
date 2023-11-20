package com.example.controller;

import com.example.dto.mapper.SubjectMapper;
import com.example.dto.subject.request.CreateSubject;
import com.example.dto.subject.response.ResponseSubject;
import com.example.dto.subject.response.ResponseSubjectWithoutCourse;
import com.example.model.entity.QSubject;
import com.example.model.entity.Subject;
import com.example.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "subjects", description = "Контроллер для работы с предметами")
@RequiredArgsConstructor
@Validated
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @GetMapping("/courses/{courseId}/subjects")
    @Operation(description = "Получить все предметы по id курса")
    public List<ResponseSubjectWithoutCourse> getCourseSubjects(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String courseId) {

        List<Subject> subjects = subjectService.findAll(QSubject.subject.course.id.eq(Long.parseLong(courseId)));

        return subjectMapper.ToListResponseSubjectWithoutCourse(subjects);
    }

    @PostMapping("/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание предмета")
    public ResponseSubject CreateSubject(@RequestBody @Valid CreateSubject createSubject) {
        var subject = subjectMapper.ToSubjectEntity(createSubject);

        var responseSubject = subjectService.create(subject);

        return subjectMapper.ToResponseSubject(responseSubject);
    }
}
