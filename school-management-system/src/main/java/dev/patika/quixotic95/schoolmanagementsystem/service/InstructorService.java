package dev.patika.quixotic95.schoolmanagementsystem.service;

import dev.patika.quixotic95.schoolmanagementsystem.dto.InstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.PermanentInstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.VisitingResearcherDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Instructor;
import dev.patika.quixotic95.schoolmanagementsystem.entity.PermanentInstructor;
import dev.patika.quixotic95.schoolmanagementsystem.entity.VisitingResearcher;
import dev.patika.quixotic95.schoolmanagementsystem.exception.InstructorIsAlreadyExistException;
import dev.patika.quixotic95.schoolmanagementsystem.mapper.InstructorMapper;
import dev.patika.quixotic95.schoolmanagementsystem.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository<?> instructorRepository;
    private final InstructorMapper instructorMapper;

    public List<InstructorDTO> findAllInstructors() {

        List<Instructor> foundInstructors = instructorRepository.findAll();

        List<InstructorDTO> result = new ArrayList<>();

        for (Instructor i : foundInstructors) {
            if (i.getClass() == PermanentInstructor.class) {
                result.add(instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO((PermanentInstructor) i));
            } else {
                result.add(instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO((VisitingResearcher) i));
            }
        }
        return result;

    }

    public InstructorDTO findInstructorById(long instructorId) {
        Instructor foundInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        if (foundInstructor.getClass() == PermanentInstructor.class) {
            return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO((PermanentInstructor) foundInstructor);
        } else {
            return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO((VisitingResearcher) foundInstructor);
        }
    }

    @Transactional
    public InstructorDTO savePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO) {

        checkIfInstructorExists(permanentInstructorDTO.getPhoneNumber(), 0);

        PermanentInstructor mappedInstructor = instructorMapper.mapFromPermanentInstructorDTOToPermanentInstructor(permanentInstructorDTO);

        return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(instructorRepository.save(mappedInstructor));
    }

    @Transactional
    public InstructorDTO saveVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO) {

        checkIfInstructorExists(visitingResearcherDTO.getPhoneNumber(), 0);

        VisitingResearcher mappedInstructor = instructorMapper.mapFromVisitingResearcherDTOToVisitingResearcher(visitingResearcherDTO);

        return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(instructorRepository.save(mappedInstructor));
    }

    @Transactional
    public PermanentInstructorDTO updatePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO, long instructorId) {

        instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        checkIfInstructorExists(permanentInstructorDTO.getPhoneNumber(), instructorId);

        PermanentInstructor permanentInstructor = instructorMapper.mapFromPermanentInstructorDTOToPermanentInstructor(permanentInstructorDTO);
        permanentInstructor.setId(instructorId);

        return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(instructorRepository.save(permanentInstructor));
    }

    @Transactional
    public VisitingResearcherDTO updateVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO, long instructorId) {

        instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        checkIfInstructorExists(visitingResearcherDTO.getPhoneNumber(), instructorId);

        VisitingResearcher visitingResearcher = instructorMapper.mapFromVisitingResearcherDTOToVisitingResearcher(visitingResearcherDTO);
        visitingResearcher.setId(instructorId);

        return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(instructorRepository.save(visitingResearcher));
    }

    @Transactional
    public PermanentInstructorDTO deletePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO) {
        PermanentInstructor foundInstructor = (PermanentInstructor) instructorRepository.findInstructorByPhoneNumberAndIdIsNot(permanentInstructorDTO.getPhoneNumber(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Instructor can not be found!"));

        PermanentInstructorDTO result = instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(foundInstructor);
        instructorRepository.delete(foundInstructor);
        return result;
    }

    @Transactional
    public VisitingResearcherDTO deleteVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO) {
        VisitingResearcher foundInstructor = (VisitingResearcher) instructorRepository.findInstructorByPhoneNumberAndIdIsNot(visitingResearcherDTO.getPhoneNumber(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Instructor can not be found!"));

        VisitingResearcherDTO result = instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(foundInstructor);
        instructorRepository.delete(foundInstructor);
        return result;
    }

    @Transactional
    public InstructorDTO deleteInstructorById(long instructorId) {

        Instructor foundInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        InstructorDTO result;

        if (foundInstructor.getClass() == PermanentInstructor.class) {
            result = instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO((PermanentInstructor) foundInstructor);
        } else {
            result = instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO((VisitingResearcher) foundInstructor);
        }

        instructorRepository.delete(foundInstructor);
        return result;
    }

    public Instructor getCourseInstructorById(long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));
    }

    private void checkIfInstructorExists(String phoneNumber, long instructorId) {

        if (instructorRepository.findInstructorByPhoneNumberAndIdIsNot(phoneNumber, instructorId).isPresent()) {
            throw new InstructorIsAlreadyExistException("An instructor with this phone number already exists!");
        }
    }
}
