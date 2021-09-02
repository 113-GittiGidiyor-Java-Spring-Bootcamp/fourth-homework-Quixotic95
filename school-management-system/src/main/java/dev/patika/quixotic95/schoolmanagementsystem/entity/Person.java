package dev.patika.quixotic95.schoolmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Person extends GenericEntity {

    private String firstName;
    private String lastName;
    private String address;

}
