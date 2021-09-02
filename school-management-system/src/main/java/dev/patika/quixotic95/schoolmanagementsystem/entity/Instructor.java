package dev.patika.quixotic95.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Instructor extends Person {

    private String phoneNumber;

    @OneToMany(mappedBy = "courseInstructor")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private Set<Course> instructorCourses = new HashSet<>();

}
