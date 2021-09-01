package dev.patika.quixotic95.schoolmanagementsystem.repository;

import dev.patika.quixotic95.schoolmanagementsystem.entity.Student;
import dev.patika.quixotic95.schoolmanagementsystem.entity.enumeration.Gender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findStudentByFirstNameAndLastNameAndAddressAndGender(String firstName, String lastName, String address, Gender gender);
}
