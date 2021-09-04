package dev.patika.quixotic95.schoolmanagementsystem.repository;

import dev.patika.quixotic95.schoolmanagementsystem.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository<T extends Instructor> extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findInstructorByPhoneNumberAndIdIsNot(String phoneNumber, long instructorId);

}
