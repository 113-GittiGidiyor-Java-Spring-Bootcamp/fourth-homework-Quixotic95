package dev.patika.quixotic95.schoolmanagementsystem.exception;

public class CourseIsAlreadyExistException extends RuntimeException{

    public CourseIsAlreadyExistException(String message) {
        super(message);
    }
}
