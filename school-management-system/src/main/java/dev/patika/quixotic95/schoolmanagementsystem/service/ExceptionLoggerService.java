package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.ExceptionLoggerDTO;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.ExceptionLoggerMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.ExceptionLoggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ExceptionLoggerService class for:
 * calling read operation of exception logs from database
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExceptionLoggerService {

    private final ExceptionLoggerRepository exceptionLoggerRepository;
    private final ExceptionLoggerMapper exceptionLoggerMapper;

    /**
     * checks if date is sent or not
     * <p>
     * if sent, calls findByStatusCodeContainingAndTimestampBetween() from repository with given date and type
     * for date, parses String date to two LocalDateTime variable.
     * for example, string 2021-09-06 becomes 2021-09-06 00:00:00 and 2021-09-07 00:00:00
     * and searches the exception between these days.
     * <p>
     * if not sent, calls findByStatusCodeContaining() from repository with given type
     * searches database with LIKE keyword for the given type.
     *
     * @param type - type/status code of the exception. Could take code like:"400", "404" or "BAD_REQUEST", "NOT_FOUND"
     * @param date - thrown date of the exception
     * @return List<ExceptionLoggerDTO> - List of found exceptions through query mapped to ExceptionLoggerDTOs
     */
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
