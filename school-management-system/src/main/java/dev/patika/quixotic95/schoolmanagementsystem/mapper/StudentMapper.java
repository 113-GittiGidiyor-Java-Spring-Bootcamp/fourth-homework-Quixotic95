package dev.patika.quixotic95.schoolmanagementsystem.mapper;

import dev.patika.quixotic95.schoolmanagementsystem.dto.StudentDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO mapFromStudentToStudentDTO(Student student);

    Student mapFromStudentDTOtoStudent(StudentDTO studentDTO);

}
