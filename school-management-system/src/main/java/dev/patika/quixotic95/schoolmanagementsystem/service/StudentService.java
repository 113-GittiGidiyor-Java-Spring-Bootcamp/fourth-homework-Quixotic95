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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentDTO> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::mapFromStudentToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO findStudentById(long studentId) {
        return studentMapper.mapFromStudentToStudentDTO(studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " can not be found!")));
    }

    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {

        checkStudentAge(studentDTO.getBirthDate());

        Student mappedStudent = studentMapper.mapFromStudentDTOtoStudent(studentDTO);
        return studentMapper.mapFromStudentToStudentDTO(studentRepository.save(mappedStudent));
    }

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

    @Transactional
    public StudentDTO deleteStudentById(long studentId) {

        Student foundStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with id: " + studentId + " can not be found!"));

        StudentDTO result = studentMapper.mapFromStudentToStudentDTO(foundStudent);
        studentRepository.deleteById(studentId);
        return result;
    }

    public Set<Student> findAllCourseStudentsById(List<Long> studentIds) {

        Set<Student> students = new HashSet<>();

        for (long l : studentIds) {
            students.add(studentRepository.findById(l)
                    .orElseThrow(() -> new EntityNotFoundException("Student with id: " + l + " can not be found!")));
        }
        return students;
    }

    public List<Long> findAllCourseStudentIdsByList(Set<Student> students) {

        List<Long> studentIds = new ArrayList<>();

        for (Student s : students) {
            studentIds.add(s.getId());
        }
        return studentIds;
    }

    private void checkStudentAge(LocalDate birthDate) {

        Period period = Period.between(birthDate, LocalDate.now());
        int studentAge = period.getYears();

        if (studentAge < 18 || studentAge > 40) {
            throw new StudentAgeNotValidException("Student age must be between 18 and 40!");
        }
    }

}
