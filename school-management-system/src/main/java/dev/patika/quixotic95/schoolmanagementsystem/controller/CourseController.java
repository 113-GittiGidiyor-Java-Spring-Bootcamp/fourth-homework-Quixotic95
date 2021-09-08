package dev.patika.quixotic95.schoolmanagementsystem.controller;

import dev.patika.quixotic95.schoolmanagementsystem.dto.CourseDTO;
import dev.patika.quixotic95.schoolmanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    // dependency injection with @Autowired annotation (not necessary to write, injects automatically; but placed for better-reading)
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // expose "/courses" and return list of courses
    @GetMapping("/courses")
    public ResponseEntity<?> findAllCourses() {
        List<CourseDTO> foundCourses = courseService.findAllCourses();
        return new ResponseEntity<>(foundCourses, HttpStatus.OK);
    }

    // mapping for GET /courses/{courseId} to get a course by id
    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> findCourseById(@PathVariable long courseId) {
        CourseDTO foundCourse = courseService.findCourseById(courseId);
        return new ResponseEntity<>(foundCourse, HttpStatus.OK);
    }

    // mapping for POST /courses - add a new course
    @PostMapping("/courses")
    public ResponseEntity<?> saveCourse(@RequestBody @Valid CourseDTO courseDTO) {
        CourseDTO result = courseService.saveCourse(courseDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for PUT /courses - update existing course
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable long courseId, @RequestBody @Valid CourseDTO courseDTO) {
        CourseDTO result = courseService.updateCourse(courseDTO, courseId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /courses - delete course
    @DeleteMapping("/courses")
    public ResponseEntity<?> deleteCourse(@RequestBody @Valid CourseDTO courseDTO) {
        CourseDTO result = courseService.deleteCourse(courseDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /courses/{courseId} - delete course by id
    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseId) {
        CourseDTO result = courseService.deleteCourseById(courseId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

