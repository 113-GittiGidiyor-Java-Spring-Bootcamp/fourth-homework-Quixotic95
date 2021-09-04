package dev.patika.quixotic95.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        defaultImpl = VisitingResearcherDTO.class)
public class VisitingResearcherDTO extends InstructorDTO {

    @ApiModelProperty(example = "300")
    private double hourlySalary;

}
