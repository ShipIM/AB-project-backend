package com.example.controller;

import com.example.dto.mapper.SubjectMapper;
import com.example.dto.page.request.PagingDto;
import com.example.dto.subject.request.CreateSubject;
import com.example.dto.subject.response.ResponseSubject;
import com.example.model.entity.Subject;
import com.example.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "subjects", description = "Контроллер для работы с предметами")
@RequiredArgsConstructor
@Validated
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @GetMapping("/courses/{courseId}/subjects")
    @Operation(description = "Получить все предметы по id курса")
    public Page<ResponseSubject> getCourseSubjects(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String courseId,
            @Valid PagingDto pagingDto) {
        Page<Subject> subjects = subjectService.getSubjectsByCourse(Long.parseLong(courseId),
                pagingDto.formPageRequest());

        return subjects.map(subjectMapper::ToResponseSubject);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание предмета")
    public ResponseSubject createSubject(@RequestBody @Valid CreateSubject createSubject) {
        var subject = subjectMapper.ToSubjectEntity(createSubject);

        var responseSubject = subjectService.createOrUpdate(subject);

        return subjectMapper.ToResponseSubject(responseSubject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить предмет по id")
    public void deleteSubject(
            @PathVariable
            @Pattern(regexp = "^(?!0+$)\\d{1,19}$",
                    message = "Идентификатор предмета должен быть положительным числом типа long")
            String subjectId) {
        subjectService.delete(Long.parseLong(subjectId));
    }
}
