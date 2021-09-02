package dev.patika.quixotic95.schoolmanagementsystem.mapper;

import dev.patika.quixotic95.schoolmanagementsystem.dto.CourseDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Course;
import dev.patika.quixotic95.schoolmanagementsystem.service.InstructorService;
import dev.patika.quixotic95.schoolmanagementsystem.service.StudentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {

    @Autowired
    InstructorService instructorService;

    @Autowired
    StudentService studentService;

    @Mapping(target = "courseInstructor", expression = "java((instructorService.getCourseInstructorById(courseDTO.getInstructorId())))")
    @Mapping(target = "courseStudents",expression = "java(studentService.findAllCourseStudentsById(courseDTO.getStudentIds()))")
    public abstract Course mapFromCourseDTOtoCourse(CourseDTO courseDTO);

    @Mapping(target = "instructorId", expression = "java(course.getCourseInstructor().getId())")
    @Mapping(target = "studentIds", expression = "java(studentService.findAllCourseStudentIdsByList(course.getCourseStudents()))")
    public abstract CourseDTO mapFromCourseToCourseDTO(Course course);

}
