package dev.patika.quixotic95.schoolmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLoggerDTO {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private long id;

    private LocalDateTime timestamp;

    private String statusCode;

    private String message;

}
