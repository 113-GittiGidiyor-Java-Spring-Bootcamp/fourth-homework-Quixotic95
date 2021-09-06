package dev.patika.quixotic95.schoolmanagementsystem.exception;

import dev.patika.quixotic95.schoolmanagementsystem.dto.ExceptionLoggerDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.ExceptionLogger;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.ExceptionLoggerMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.ExceptionLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ExceptionLoggerRepository exceptionLoggerRepository;

    @Autowired
    private ExceptionLoggerMapper exceptionLoggerMapper;

    @ExceptionHandler({CourseIsAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionLoggerDTO> handleException(CourseIsAlreadyExistException exc) {
        ExceptionLogger exception = new ExceptionLogger(exc.getMessage(), HttpStatus.BAD_REQUEST);
        exceptionLoggerRepository.save(exception);
        ExceptionLoggerDTO response = exceptionLoggerMapper.toDto(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InstructorIsAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionLoggerDTO> handleException(InstructorIsAlreadyExistException exc) {
        ExceptionLogger exception = new ExceptionLogger(exc.getMessage(), HttpStatus.BAD_REQUEST);
        exceptionLoggerRepository.save(exception);
        ExceptionLoggerDTO response = exceptionLoggerMapper.toDto(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StudentAgeNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionLoggerDTO> handleException(StudentAgeNotValidException exc) {
        ExceptionLogger exception = new ExceptionLogger(exc.getMessage(), HttpStatus.BAD_REQUEST);
        exceptionLoggerRepository.save(exception);
        ExceptionLoggerDTO response = exceptionLoggerMapper.toDto(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StudentNumberForOneCourseExceededException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionLoggerDTO> handleException(StudentNumberForOneCourseExceededException exc) {
        ExceptionLogger exception = new ExceptionLogger(exc.getMessage(), HttpStatus.BAD_REQUEST);
        exceptionLoggerRepository.save(exception);
        ExceptionLoggerDTO response = exceptionLoggerMapper.toDto(exception);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionLoggerDTO> handleException(EntityNotFoundException exc) {
        ExceptionLogger exception = new ExceptionLogger(exc.getMessage(), HttpStatus.NOT_FOUND);
        exceptionLoggerRepository.save(exception);
        ExceptionLoggerDTO response = exceptionLoggerMapper.toDto(exception);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
