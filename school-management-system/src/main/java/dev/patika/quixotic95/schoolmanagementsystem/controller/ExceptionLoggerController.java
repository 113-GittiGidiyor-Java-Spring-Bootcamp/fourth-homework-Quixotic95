package dev.patika.quixotic95.schoolmanagementsystem.controller;

import dev.patika.quixotic95.schoolmanagementsystem.dto.ExceptionLoggerDTO;
import dev.patika.quixotic95.schoolmanagementsystem.service.ExceptionLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExceptionLoggerController {

    private final ExceptionLoggerService exceptionLoggerService;

    @Autowired
    public ExceptionLoggerController(ExceptionLoggerService exceptionLoggerService) {
        this.exceptionLoggerService = exceptionLoggerService;
    }

    @GetMapping("/exceptionLogs")
    public ResponseEntity<?> getExceptionLogs(@RequestParam String type,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD") String date) {
        List<ExceptionLoggerDTO> result = exceptionLoggerService.findByTypeAndOrDate(type, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
