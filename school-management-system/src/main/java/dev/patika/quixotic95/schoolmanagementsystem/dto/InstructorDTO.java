package dev.patika.quixotic95.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VisitingResearcherDTO.class,
                name = "VisitingResearcher"),
        @JsonSubTypes.Type(value = PermanentInstructorDTO.class,
                name = "PermanentInstructor")
})
public class InstructorDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(example = "Koray")
    @NotBlank(message = "first name is required!")
    private String firstName;

    @ApiModelProperty(example = "Güney")
    @NotBlank(message = "last name is required!")
    private String lastName;

    @ApiModelProperty(example = "8 Hazine Street")
    @NotBlank(message = "address is required!")
    private String address;

    @ApiModelProperty(example = "541-851-9986")
    private String phoneNumber;

}
