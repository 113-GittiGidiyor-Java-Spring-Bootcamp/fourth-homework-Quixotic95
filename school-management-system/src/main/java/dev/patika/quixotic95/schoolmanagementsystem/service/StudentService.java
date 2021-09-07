package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.StudentDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Student;
import dev.patika.quixotic95.schoolmanagementsystem.exception.StudentAgeNotValidException;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.StudentMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * StudentService class for:
 * calling crud operations from database
 * checking requests and throwing exceptions in planned conditions
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    /**
     * calls findAll() from repository
     * maps returned Students to StudentDTOs
     *
     * @return List<StudentDTO> - StudentDTO list
     */
    public List<StudentDTO> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::mapFromStudentToStudentDTO)
                .collect(Collectors.toList());
    }

    /**
     * calls findById() method from repository with given studentId
     * if Student could not found, throws an exception
     * maps found Student to StudentDTO
     *
     * @param studentId - ID of the Student
     * @return StudentDTO - found Student mapped to StudentDTO
     */
    public StudentDTO findStudentById(long studentId) {
        return studentMapper.mapFromStudentToStudentDTO(studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " can not be found!")));
    }

    /**
     * checks if StudentDTO's age is acceptable.
     * if not, throws an exception.
     * if acceptable, maps the StudentDTO to Student
     * then calls save() method from repository with Student object
     *
     * @param studentDTO - StudentDTO request object
     * @return StudentDTO - response object which is mapped from saved Student
     */
    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {

        checkStudentAge(studentDTO.getBirthDate());

        Student mappedStudent = studentMapper.mapFromStudentDTOtoStudent(studentDTO);
        return studentMapper.mapFromStudentToStudentDTO(studentRepository.save(mappedStudent));
    }

    /**
     * calls findById() method from repository with given studentId
     * if Student found, continues to process. else throws an exception
     * checks if student's age is acceptable via helper method
     * <p>
     * maps the request object StudentDTO to a Student object
     * <p>
     * sets the Id of the mapped Student to request long studentId
     * sets the courses of the mapped Student to found Student's courses
     * <p>
     * calls the save() method from repository with mapped Student object
     *
     * @param studentDTO - StudentDTO request object
     * @param studentId  - ID of the Student which will be updated
     * @return StudentDTO - StudentDTO response object which mapped from saved Student object
     */
    @Transactional
    public StudentDTO updateStudent(StudentDTO studentDTO, long studentId) {

        Student foundStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " can not be found!"));

        checkStudentAge(studentDTO.getBirthDate());

        Student mappedStudent = studentMapper.mapFromStudentDTOtoStudent(studentDTO);
        mappedStudent.setId(studentId);
        mappedStudent.setStudentCourses(foundStudent.getStudentCourses());

        return studentMapper.mapFromStudentToStudentDTO(studentRepository.save(mappedStudent));
    }

    /**
     * checks if Student exists in database with necessary fields taken from StudentDTO
     * if Student is not found, throws an exception
     * if found, maps the found Student to StudentDTO
     * then calls delete() method from repository found Student
     *
     * @param studentDTO - StudentDTO request object
     * @return StudentDTO - StudentDTO object which is mapped from deleted Student
     */
    @Transactional
    public StudentDTO deleteStudent(StudentDTO studentDTO) {

        Student foundStudent = studentRepository.findStudentByFirstNameAndLastNameAndAddressAndGender(studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getAddress(),
                studentDTO.getGender()).orElseThrow(() -> new EntityNotFoundException("Student can not be found!"));

        StudentDTO result = studentMapper.mapFromStudentToStudentDTO(foundStudent);
        studentRepository.delete(foundStudent);
        return result;
    }

    /**
     * checks if Student is exists in database via calling findById() method from repository
     * if Student is not exists, throws an exception
     * if exists, maps the found Student to StudentDTO
     * <p>
     * calls delete() method from repository with found Student object
     *
     * @param studentId - studentId which will be deleted
     * @return StudentDTO - tudentDTO object which is mapped from deleted Student
     */
    @Transactional
    public StudentDTO deleteStudentById(long studentId) {

        Student foundStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " can not be found!"));

        StudentDTO result = studentMapper.mapFromStudentToStudentDTO(foundStudent);
        studentRepository.deleteById(studentId);
        return result;
    }


    /**
     * helper method used for mapping CourseDTO to Course
     * checks given studentIds if they're exists in database.
     * if some of them are not exists, throws an exception
     *
     * @param studentIds - studentIds given to CourseDTO object
     * @return Set<Student> - students that are added to CourseDTO via their IDs
     */
    public Set<Student> findAllCourseStudentsById(List<Long> studentIds) {

        Set<Student> students = new HashSet<>();

        for (long l : studentIds) {
            students.add(studentRepository.findById(l)
                    .orElseThrow(() -> new EntityNotFoundException("Student with id: " + l + " can not be found!")));
        }
        return students;
    }

    /**
     * helper method used for mapping Course to CourseDTO
     * takes Students list and returns List of Long which is IDs of Students
     *
     * @param students - Students of the Course
     * @return List<Long> - IDs of the Students
     */
    public List<Long> findAllCourseStudentIdsByList(Set<Student> students) {

        List<Long> studentIds = new ArrayList<>();

        for (Student s : students) {
            studentIds.add(s.getId());
        }
        return studentIds;
    }

    /**
     * helper method for checking if Student's age is between 18 and 40
     * takes Student's birthDate and calculates the year between today
     * throws an exception if Student's age is not between 40
     *
     * @param birthDate - birthDate of the Student
     */
    private void checkStudentAge(LocalDate birthDate) {

        Period period = Period.between(birthDate, LocalDate.now());
        int studentAge = period.getYears();

        if (studentAge < 18 || studentAge > 40) {
            throw new StudentAgeNotValidException("Student age must be between 18 and 40!");
        }
    }

}
