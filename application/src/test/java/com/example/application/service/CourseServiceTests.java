package com.example.application.service;

import com.example.application.configuration.BaseTestClass;
import com.example.exception.EntityNotFoundException;
import com.example.model.entity.Course;
import com.example.model.enumeration.DegreeType;
import com.example.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseServiceTests extends BaseTestClass {

    @Autowired
    private CourseService courseService;

    private final int DEFAULT_COURSE_NUMBER = 6;

    @Test
    public void getCourses() {
        List<Course> retrievedCourses = courseService.getCourses();

        Assertions.assertEquals(retrievedCourses.size(), DEFAULT_COURSE_NUMBER);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, 1, BACHELOR",
            "2, 2, BACHELOR",
            "3, 3, BACHELOR",
            "4, 4, BACHELOR",
            "5, 5, MASTER",
            "6, 6, MASTER"
    })
    public void getById(long id, String name, DegreeType degreeType) {
        Course course = new Course(id, name, degreeType);

        Course retrievedCourse = courseService.getById(id);

        Assertions.assertEquals(retrievedCourse, course);
    }

    @Test
    public void getByIdOnNonExistingCourse() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> courseService.getById(7));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5", "6"})
    public void isCourseExists(long id) {
        Assertions.assertTrue(courseService.isCourseExists(id));
    }

    @Test
    public void isCourseExistsOnNonExistingCourse() {
        Assertions.assertFalse(courseService.isCourseExists(7));
    }
}
