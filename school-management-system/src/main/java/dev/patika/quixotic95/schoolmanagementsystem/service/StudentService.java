package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.StudentDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Student;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.StudentMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public boolean existsById(long studentId) {
        return studentRepository.existsById(studentId);
    }

    public List<Student> findAllStudents() {
        List<Student> foundStudents = (List<Student>) studentRepository.findAll();
        if (!foundStudents.isEmpty()) {
            return foundStudents;
        }
        throw new EntityNotFoundException("No students found in database");
    }

    public Optional<Student> findStudentById(long studentId) {
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        if (foundStudent.isPresent()) {
            return foundStudent;
        }
        throw new EntityNotFoundException("Student with id: " + studentId + " can not be found!");
    }

    @Transactional
    public Optional<Student> saveStudent(StudentDTO studentDTO) {
        Student mappedStudent = studentMapper.mapFromStudentDTOtoStudent(studentDTO);
        return Optional.of(studentRepository.save(mappedStudent));
    }

    @Transactional
    public Optional<Student> updateStudent(StudentDTO studentDTO, long studentId) {
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        if (foundStudent.isPresent()) {
            Student mappedStudent = studentMapper.mapFromStudentDTOtoStudent(studentDTO);
            mappedStudent.setId(studentId);
            return (Optional.of(studentRepository.save(mappedStudent)));
        }
        throw new EntityNotFoundException("Student with id: " + studentId + " can not be found!");
    }

    @Transactional
    public Optional<Student> deleteStudent(StudentDTO studentDTO) {
        Optional<Student> foundStudent = studentRepository.findStudentByFirstNameAndLastNameAndAddressAndGender(studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getAddress(),
                studentDTO.getGender());
        if (foundStudent.isPresent()) {
            studentRepository.delete(foundStudent.get());
            return foundStudent;
        }
        throw new EntityNotFoundException("Student can not be found!");
    }

    @Transactional
    public Optional<Student> deleteStudentById(long studentId) {
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        if (foundStudent.isPresent()) {
            studentRepository.deleteById(studentId);
            return foundStudent;
        }
        throw new EntityNotFoundException("Student can not be found!");
    }

}
