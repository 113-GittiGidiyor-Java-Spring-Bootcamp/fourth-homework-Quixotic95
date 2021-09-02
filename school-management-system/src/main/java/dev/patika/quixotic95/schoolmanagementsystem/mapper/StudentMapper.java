package dev.patika.quixotic95.schoolmanagementsystem.mapper;

import dev.patika.quixotic95.schoolmanagementsystem.dto.StudentDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class StudentMapper {

    public abstract StudentDTO mapFromStudentToStudentDTO(Student student);

    public abstract Student mapFromStudentDTOtoStudent(StudentDTO studentDTO);

}
