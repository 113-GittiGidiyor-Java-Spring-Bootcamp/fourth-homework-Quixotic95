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
    @NotBlank
    private String courseName;

    @ApiModelProperty(example = "ATA1001")
    @NotBlank
    private String courseCode;

    @ApiModelProperty(example = "6")
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double creditScore;

    @ApiModelProperty(example = "1")
    @NotNull
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long instructorId;

    @ApiModelProperty(example = "[2, 9, 12]")
    private List<Long> studentIds;

}
