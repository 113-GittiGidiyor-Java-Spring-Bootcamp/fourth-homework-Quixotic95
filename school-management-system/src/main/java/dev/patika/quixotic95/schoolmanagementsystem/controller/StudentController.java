package dev.patika.quixotic95.schoolmanagementsystem.controller;

import dev.patika.quixotic95.schoolmanagementsystem.dto.StudentDTO;
import dev.patika.quixotic95.schoolmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    // dependency injection with @Autowired annotation (not necessary to write, injects automatically; but placed for better-reading)
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // expose "/students" and return list of students
    @GetMapping("/students")
    public ResponseEntity<?> findAllStudents() {
        return new ResponseEntity<>(studentService.findAllStudents(), HttpStatus.OK);
    }

    // mapping for GET /students/{studentId} to get a student by id
    @GetMapping("/students/{studentId}")
    public ResponseEntity<?> findStudentById(@PathVariable long studentId) {
        return new ResponseEntity<>(studentService.findStudentById(studentId), HttpStatus.OK);
    }

    // mapping for POST /students - add a new student
    @PostMapping("/students")
    public ResponseEntity<?> saveStudent(@RequestBody @Valid StudentDTO studentDTO) {
        StudentDTO result = studentService.saveStudent(studentDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for PUT /students/{studentId} - update an existing student
    @PutMapping("/students/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable long studentId, @RequestBody StudentDTO studentDTO) {
        StudentDTO result = studentService.updateStudent(studentDTO, studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /students - delete student
    @DeleteMapping("/students")
    public ResponseEntity<?> deleteStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO result = studentService.deleteStudent(studentDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /students/{studentId} - delete student by id
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long studentId) {
        StudentDTO result = studentService.deleteStudentById(studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
