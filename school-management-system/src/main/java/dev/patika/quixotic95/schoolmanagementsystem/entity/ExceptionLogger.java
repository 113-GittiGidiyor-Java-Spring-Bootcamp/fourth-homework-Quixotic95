package dev.patika.quixotic95.schoolmanagementsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExceptionLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime timestamp;

    private String statusCode;

    private String message;

    public ExceptionLogger(String message, HttpStatus type) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.statusCode = type.toString();
    }
}
