package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.ExceptionLoggerDTO;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.ExceptionLoggerMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.ExceptionLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExceptionLoggerService {

    private final ExceptionLoggerRepository exceptionLoggerRepository;
    private final ExceptionLoggerMapper exceptionLoggerMapper;

    public List<ExceptionLoggerDTO> findByTypeAndOrDate(String type, String date) {

        if (!(date == null)) {
            LocalDate parsedDate = LocalDate.parse(date);
            return exceptionLoggerRepository.findByStatusCodeContainingAndTimestampBetween(type, parsedDate.atStartOfDay(), parsedDate.plusDays(1).atStartOfDay())
                    .orElseThrow(() -> new EntityNotFoundException("Found no exception log on database like this."))
                    .stream()
                    .map(exceptionLoggerMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return exceptionLoggerRepository.findByStatusCodeContaining(type)
                    .orElseThrow(() -> new EntityNotFoundException("Found no exception log on database like this.")).stream()
                    .map(exceptionLoggerMapper::toDto)
                    .collect(Collectors.toList());

        }


    }
}
