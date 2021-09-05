package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.CourseDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Course;
import dev.patika.quixotic95.schoolmanagementsystem.exception.CourseIsAlreadyExistException;
import dev.patika.quixotic95.schoolmanagementsystem.exception.StudentNumberForOneCourseExceededException;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.CourseMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<CourseDTO> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::mapFromCourseToCourseDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findCourseById(long courseId) {
        return courseMapper.mapFromCourseToCourseDTO(courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + courseId + " can not be found!")));
    }

    @Transactional
    public CourseDTO saveCourse(CourseDTO courseDTO) {

        checkIfCourseExists(courseDTO.getCourseCode(), 0);

        checkIfCourseHasMoreThanTwentyStudents(courseDTO.getStudentIds().size());

        Course mappedCourse = courseMapper.mapFromCourseDTOtoCourse(courseDTO);

        return courseMapper.mapFromCourseToCourseDTO(courseRepository.save(mappedCourse));
    }

    @Transactional
    public CourseDTO updateCourse(CourseDTO courseDTO, long courseId) {

        courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + courseId + " can not be found!"));

        checkIfCourseExists(courseDTO.getCourseCode(), courseId);

        checkIfCourseHasMoreThanTwentyStudents(courseDTO.getStudentIds().size());

        Course mappedCourse = courseMapper.mapFromCourseDTOtoCourse(courseDTO);
        mappedCourse.setId(courseId);

        return courseMapper.mapFromCourseToCourseDTO(courseRepository.save(mappedCourse));
    }

    @Transactional
    public CourseDTO deleteCourse(CourseDTO courseDTO) {

        Course foundCourse = courseRepository.findCourseByCourseCode(courseDTO.getCourseCode())
                .orElseThrow(() -> new EntityNotFoundException("Course can not be found!"));

        CourseDTO result = courseMapper.mapFromCourseToCourseDTO(foundCourse);
        courseRepository.delete(foundCourse);
        return result;
    }

    @Transactional
    public CourseDTO deleteCourseById(long courseId) {

        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + courseId + " can not be found!"));

        CourseDTO result = courseMapper.mapFromCourseToCourseDTO(foundCourse);
        courseRepository.delete(foundCourse);
        return result;
    }

    private void checkIfCourseExists(String courseCode, long courseId) {

        if (courseRepository.findCourseByCourseCodeAndIdIsNot(courseCode, courseId).isPresent()) {
            throw new CourseIsAlreadyExistException("A course with this course code already exists!");
        }
    }

    private void checkIfCourseHasMoreThanTwentyStudents(int numberOfStudents) {
        if (numberOfStudents > 20) {
            throw new StudentNumberForOneCourseExceededException("Course can maximum have 20 students!");
        }
    }

}
