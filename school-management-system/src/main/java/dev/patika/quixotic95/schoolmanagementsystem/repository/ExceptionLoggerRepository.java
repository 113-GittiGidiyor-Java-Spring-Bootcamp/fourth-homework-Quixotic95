package dev.patika.quixotic95.schoolmanagementsystem.repository;

import dev.patika.quixotic95.schoolmanagementsystem.entity.ExceptionLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExceptionLoggerRepository extends JpaRepository<ExceptionLogger, Long> {

    Optional<List<ExceptionLogger>> findByStatusCodeContainingAndTimestampBetween(String statusCode, LocalDateTime from, LocalDateTime to);

    Optional<List<ExceptionLogger>> findByStatusCodeContaining(String statusCode);
}
