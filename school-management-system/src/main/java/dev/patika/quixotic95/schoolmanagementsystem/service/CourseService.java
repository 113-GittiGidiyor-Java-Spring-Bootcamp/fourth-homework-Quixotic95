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

/**
 * CourseService class for:
 * calling crud operations from database
 * checking requests and throwing exceptions in planned conditions
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    /**
     * calls findAll() from repository
     * maps returned Courses' to CourseDTOs'
     * @return List<CourseDTO> - CourseDTO list
     */
    public List<CourseDTO> findAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::mapFromCourseToCourseDTO)
                .collect(Collectors.toList());
    }

    /**
     * calls findCourseById() from repository with given courseId
     * maps returned Course to CourseDTO
     * @param courseId - ID of the Course
     * @return CourseDTO - found Course mapped to CourseDTO
     */
    public CourseDTO findCourseById(long courseId) {
        return courseMapper.mapFromCourseToCourseDTO(courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + courseId + " can not be found!")));
    }

    /**
     * checks if Course already exists in database with given CourseDTOs' CourseCode
     * if it doesn't exist, checks if given CourseDTO object has more than 20 Students
     * if student count is not more than 20, maps CourseDTO to Course
     * calls save() from repository with Course object
     * @param courseDTO - CourseDTO request object
     * @return CourseDTO - saved Course mapped to CourseDTO
     */
    @Transactional
    public CourseDTO saveCourse(CourseDTO courseDTO) {

        checkIfCourseExists(courseDTO.getCourseCode(), 0);

        checkIfCourseHasMoreThanTwentyStudents(courseDTO.getStudentIds().size());

        Course mappedCourse = courseMapper.mapFromCourseDTOtoCourse(courseDTO);

        return courseMapper.mapFromCourseToCourseDTO(courseRepository.save(mappedCourse));
    }

    /**
     * checks if Course exists with given courseId
     * if it doesn't exist, throws an exception
     * if it exists, checks courseDTO request if any other Course has the same CourseCode
     * while checking CourseCode, excludes the updated one.
     * checks if given CourseDTO object has more than 20 Students
     * maps CourseDTO to Course
     * calls save() from repository with Course object
     * @param courseDTO - CourseDTO request object
     * @param courseId - ID of the will updated Course
     * @return CourseDTO - updated Course mapped to CourseDTO
     */
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

    /**
     * checks if Course exists in database with given CourseDTOs' CourseCode
     * if it doesn't exist, throws an exception
     * if it exists, maps the found Course to CourseDTO
     * calls delete() from repository with found Course object
     * @param courseDTO - CourseDTO request object
     * @return CourseDTO - mapped CourseDTO from found Course object
     */
    @Transactional
    public CourseDTO deleteCourse(CourseDTO courseDTO) {

        Course foundCourse = courseRepository.findCourseByCourseCode(courseDTO.getCourseCode())
                .orElseThrow(() -> new EntityNotFoundException("Course can not be found!"));

        CourseDTO result = courseMapper.mapFromCourseToCourseDTO(foundCourse);
        courseRepository.delete(foundCourse);
        return result;
    }

    /**
     * checks if Course exists with given courseId
     * if it doesn't exist, throws an exception
     * if it exists, maps the found Course to CourseDTO
     * calls delete() from repository with found Course object
     * @param courseId - ID of the will deleted Course
     * @return CourseDTO - mapped CourseDTO from found Course object
     */
    @Transactional
    public CourseDTO deleteCourseById(long courseId) {

        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id: " + courseId + " can not be found!"));

        CourseDTO result = courseMapper.mapFromCourseToCourseDTO(foundCourse);
        courseRepository.delete(foundCourse);
        return result;
    }

    /**
     * helper method for checking if a course already exists in database with given CourseCode
     * calls findCourseByCourseCodeAndIdIsNot(courseCode, courseId) from repository
     * if a course found with this given courseCode, throws an exception.
     * @param courseCode - courseCode to search in database
     * @param courseId - courseId to exclude from search. Used in update method to exclude updated Course's courseCode to prevent conflict.
     */
    private void checkIfCourseExists(String courseCode, long courseId) {

        if (courseRepository.findCourseByCourseCodeAndIdIsNot(courseCode, courseId).isPresent()) {
            throw new CourseIsAlreadyExistException("A course with this course code already exists!");
        }
    }

    /**
     * helper method for checking if a course has more than 20 students.
     * checks received integer number if it's greater than 20.
     * if it's, throws an exception.
     * @param numberOfStudents - integer value of CourseDTOs' students' list size.
     */
    private void checkIfCourseHasMoreThanTwentyStudents(int numberOfStudents) {
        if (numberOfStudents > 20) {
            throw new StudentNumberForOneCourseExceededException("Course can maximum have 20 students!");
        }
    }

}
