package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Subject;
import com.example.repository.SubjectRepository;
import com.example.service.SubjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

public class SubjectServiceTests extends BaseTestClass {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    private Subject defaultSubject;

    @BeforeEach
    @AfterEach
    void init() {
        subjectRepository.deleteAll();

        defaultSubject = new Subject();
        defaultSubject.setCourseId(1L);
        defaultSubject.setName("name");
    }

    @Test
    public void createOnlyOneAutoDealerTest() {
        subjectService.createOrUpdate(defaultSubject);

        var subjectsCount = subjectRepository.count();

        Assertions.assertEquals(1, subjectsCount);
    }

    @Test
    public void createCorrectAutoDealerTest() {
        var createSubject = subjectService.createOrUpdate(defaultSubject);
        var repositorySubject = subjectService.getById(createSubject.getId());

        defaultSubject.setId(createSubject.getId());

        Assertions.assertEquals(defaultSubject, repositorySubject);
        Assertions.assertEquals(defaultSubject, createSubject);
    }

    @Test
    public void createIncorrectCourseIdTest() {
        defaultSubject.setCourseId(0L);
        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.createOrUpdate(defaultSubject));
    }

    @Test
    public void updateCorrectTest() {
        subjectService.createOrUpdate(defaultSubject);
        var newName = defaultSubject.getName() + "diff";
        defaultSubject.setName(newName);

        subjectService.createOrUpdate(defaultSubject);

        Assertions.assertEquals(newName, subjectService.getById(defaultSubject.getId()).getName());
    }

    @Test
    public void uniqConstraintCloneCreateFailTest() {
        subjectService.createOrUpdate(defaultSubject);
        defaultSubject.setId(null);
        Assertions.assertThrows(DbActionExecutionException.class, () -> subjectService.createOrUpdate(defaultSubject));
    }

    @Test
    public void uniqCreateTestDifferentNamesTest() {
        subjectService.createOrUpdate(defaultSubject);

        var diffNameSubject = new Subject(defaultSubject);
        diffNameSubject.setName(diffNameSubject.getName() + "2");

        Assertions.assertDoesNotThrow(() -> subjectService.createOrUpdate(diffNameSubject));
    }

    @Test
    public void uniqCreateTestDifferentCoursesTest() {
        subjectService.createOrUpdate(defaultSubject);

        var diffNameSubject = new Subject(defaultSubject);
        diffNameSubject.setCourseId(diffNameSubject.getCourseId() + 1);

        Assertions.assertDoesNotThrow(() -> subjectService.createOrUpdate(diffNameSubject));
    }

    @Test
    public void getExistIdTest() {
        var id = subjectService.createOrUpdate(defaultSubject).getId();

        var subject = subjectService.getById(id);

        Assertions.assertNotNull(subject);
    }

    @Test
    public void getNotExistIdTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.getById(1));
    }

    @Test
    public void isExistTrueTest() {
        var id = subjectService.createOrUpdate(defaultSubject).getId();

        var isExist = subjectService.isSubjectExists(id);

        Assertions.assertTrue(isExist);
    }

    @Test
    public void isExistFalseTest() {
        var isExist = subjectService.isSubjectExists(1);

        Assertions.assertFalse(isExist);
    }

    @Test
    public void getSubjectsByCourseSuccessTest() {
        subjectService.createOrUpdate(defaultSubject);
        var pageRequest = PageRequest.of(0, 1);

        var subjects = subjectService.getSubjectsByCourse(defaultSubject.getCourseId(), pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(defaultSubject, subjects.getContent().get(0));
    }

    @Test
    public void getSubjectsByCourseEmptyPageTest() {
        subjectService.createOrUpdate(defaultSubject);
        var pageRequest = PageRequest.of(1, 1);

        var subjects = subjectService.getSubjectsByCourse(defaultSubject.getCourseId(), pageRequest);

        Assertions.assertEquals(1, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        Assertions.assertEquals(0, subjects.getContent().size());
    }

    @Test
    public void getSubjectsByCourseCorrectOrderTest() {
        defaultSubject.setName("2");
        subjectService.createOrUpdate(defaultSubject);
        defaultSubject.setName("1");
        defaultSubject.setId(null);
        subjectService.createOrUpdate(defaultSubject);
        var pageRequest = PageRequest.of(0, 2);

        var subjects = subjectService.getSubjectsByCourse(defaultSubject.getCourseId(), pageRequest);

        Assertions.assertEquals(2, subjects.getTotalElements());
        Assertions.assertEquals(1, subjects.getTotalPages());
        for (int i = 0; i < subjects.getSize() - 1; i++) {
            Assertions.assertEquals(-1, subjects.getContent().get(i).getName().compareTo(subjects.getContent().get(i + 1).getName()));
        }
    }

    @Test
    public void deleteSubjectTest() {
        var id = subjectService.createOrUpdate(defaultSubject).getId();

        Assertions.assertNotNull(subjectService.getById(id));
        Assertions.assertDoesNotThrow(() -> subjectService.delete(id));
        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.getById(id));
    }

    @Test
    public void deleteDontExistSubjectTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.delete(1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> subjectService.getById(1));
    }
}
