package dev.patika.quixotic95.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course extends GenericEntity {

    private String courseName;
    private String courseCode;
    private double creditScore;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "instructor_id")
    private Instructor courseInstructor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> courseStudents = new HashSet<>();

}
