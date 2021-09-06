package dev.patika.quixotic95.schoolmanagementsystem.mapper;

import dev.patika.quixotic95.schoolmanagementsystem.dto.ExceptionLoggerDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.ExceptionLogger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ExceptionLoggerMapper {

    public abstract ExceptionLoggerDTO toDto(ExceptionLogger exceptionLogger);

}
