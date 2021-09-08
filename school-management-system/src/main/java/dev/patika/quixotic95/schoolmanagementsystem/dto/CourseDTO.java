package dev.patika.quixotic95.schoolmanagementsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(example = "Ataturk’s Principles and Turkish Revolution History")
    @NotBlank(message = "course name is required!")
    private String courseName;

    @ApiModelProperty(example = "ATA1001")
    @NotBlank(message = "a course cannot be created without course code!")
    private String courseCode;

    @ApiModelProperty(example = "6")
    @NotNull(message = "credit score is required!")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double creditScore;

    @ApiModelProperty(example = "1")
    @NotNull(message = "a course cannot be created without instructor!")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long instructorId;

    @ApiModelProperty(example = "[2, 9, 12]")
    private List<Long> studentIds;

}
