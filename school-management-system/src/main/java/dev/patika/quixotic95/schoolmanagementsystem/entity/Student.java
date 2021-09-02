package dev.patika.quixotic95.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.patika.quixotic95.schoolmanagementsystem.entity.enumeration.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends Person {

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    @EqualsAndHashCode.Exclude
    private Set<Course> studentCourses = new HashSet<>();

}
