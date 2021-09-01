package dev.patika.quixotic95.schoolmanagementsystem.dto;

import dev.patika.quixotic95.schoolmanagementsystem.entity.enumeration.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(example = "Ahmet Emre")
    @NotBlank(message = "first name is required!")
    private String firstName;

    @ApiModelProperty(example = "Oğuz")
    @NotBlank(message = "last name is required!")
    private String lastName;

    @ApiModelProperty(example = "2 Kiptaş Sokak")
    @NotBlank(message = "address is required!")
    private String address;

    @ApiModelProperty(example = "1995-07-26")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @NotNull(message = "birth date is required!")
    private LocalDate birthDate;

    @ApiModelProperty(example = "MALE, FEMALE or OTHER")
    @NotNull(message = "gender must be specified!")
    private Gender gender;
}
