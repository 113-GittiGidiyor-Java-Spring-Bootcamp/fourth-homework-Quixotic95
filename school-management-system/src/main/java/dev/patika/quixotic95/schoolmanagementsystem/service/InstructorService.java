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

/**
 * InstructorService class for:
 * calling crud operations from database
 * checking requests and throwing exceptions in planned conditions
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository<?> instructorRepository;
    private final InstructorMapper instructorMapper;

    /**
     * calls findAll() from repository
     * maps returned Instructors to InstructorDTOs
     *
     * @return List<InstructorDTO> - InstructorDTO list
     */
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

    /**
     * calls findById() from repository with given instructorId
     * checks the foundInstructor if it's a PermanentInstructor or VisitingResearcher
     * maps found Instructor to related InstructorDTO
     *
     * @param instructorId - ID of the Instructor
     * @return InstructorDTO - found Instructor mapped to related InstructorDTO
     */
    public InstructorDTO findInstructorById(long instructorId) {
        Instructor foundInstructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        if (foundInstructor.getClass() == PermanentInstructor.class) {
            return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO((PermanentInstructor) foundInstructor);
        } else {
            return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO((VisitingResearcher) foundInstructor);
        }
    }

    /**
     * checks if PermanentInstructor already exists in Instructor database table with given permanentInstructorDTO's phoneNumber
     * if it exists, throws an exception
     * if it does not, maps the permanentInstructorDTO to PermanentInstructor
     * calls save() from repository with PermanentInstructor object
     *
     * @param permanentInstructorDTO - PermanentInstructorDTO request object
     * @return InstructorDTO - saved PermanentInstructor mapped to PermanentInstructorDTO
     */
    @Transactional
    public InstructorDTO savePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO) {

        checkIfInstructorExists(permanentInstructorDTO.getPhoneNumber(), 0);

        PermanentInstructor mappedInstructor = instructorMapper.mapFromPermanentInstructorDTOToPermanentInstructor(permanentInstructorDTO);

        return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(instructorRepository.save(mappedInstructor));
    }

    /**
     * checks if VisitingResearcher already exists in Instructor database table with given visitingResearcherDTO's phoneNumber
     * if it exists, throws an exception
     * if it does not, maps the visitingResearcherDTO to VisitingResearcher
     * calls save() from repository with VisitingResearcher object
     *
     * @param visitingResearcherDTO - VisitingResearcherDTO request object
     * @return InstructorDTO - saved VisitingResearcher mapped to VisitingResearcherDTO
     */
    @Transactional
    public InstructorDTO saveVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO) {

        checkIfInstructorExists(visitingResearcherDTO.getPhoneNumber(), 0);

        VisitingResearcher mappedInstructor = instructorMapper.mapFromVisitingResearcherDTOToVisitingResearcher(visitingResearcherDTO);

        return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(instructorRepository.save(mappedInstructor));
    }

    /**
     * checks if PermanentInstructor exists in Instructor database table with given instructorId
     * if it doesn't exist, throws an exception
     * if it exists, checks permanentInstructorDTO's phoneNumber if any other Instructor has the same phoneNumber
     * while checking the phoneNumber, excludes the updated one.
     * maps PermanentInstructorDTO to PermanentInstructor
     * calls save() from repository with PermanentInstructor object
     *
     * @param permanentInstructorDTO - PermanentInstructorDTO request object
     * @param instructorId           - ID of the will updated PermanentInstructor
     * @return PermanentInstructorDTO - updated PermanentInstructorDTO mapped from PermanentInstructor
     */
    @Transactional
    public PermanentInstructorDTO updatePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO, long instructorId) {

        instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        checkIfInstructorExists(permanentInstructorDTO.getPhoneNumber(), instructorId);

        PermanentInstructor permanentInstructor = instructorMapper.mapFromPermanentInstructorDTOToPermanentInstructor(permanentInstructorDTO);
        permanentInstructor.setId(instructorId);

        return instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(instructorRepository.save(permanentInstructor));
    }

    /**
     * checks if VisitingResearcher exists in Instructor database table with given instructorId
     * if it doesn't exist, throws an exception
     * if it exists, checks visitingResearcherDTO's phoneNumber if any other Instructor has the same phoneNumber
     * while checking the phoneNumber, excludes the updated one.
     * maps VisitingResearcherDTO to VisitingResearcher
     * calls save() from repository with VisitingResearcher object
     *
     * @param visitingResearcherDTO - VisitingResearcherDTO request object
     * @param instructorId          - ID of the will updated VisitingResearcher
     * @return VisitingResearcherDTO - updated VisitingResearcherDTO mapped from VisitingResearcher
     */
    @Transactional
    public VisitingResearcherDTO updateVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO, long instructorId) {

        instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));

        checkIfInstructorExists(visitingResearcherDTO.getPhoneNumber(), instructorId);

        VisitingResearcher visitingResearcher = instructorMapper.mapFromVisitingResearcherDTOToVisitingResearcher(visitingResearcherDTO);
        visitingResearcher.setId(instructorId);

        return instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(instructorRepository.save(visitingResearcher));
    }

    /**
     * checks if PermanentInstructor exits in database with given PermanentInstructorDTO's phoneNumber
     * if it doesn't exist, throws an exception
     * if it exists, maps the found PermanentInstructor to PermanentInstructorDTO
     * calls delete() from repository with found PermanentInstructor object
     *
     * @param permanentInstructorDTO - PermanentInstructorDTO request object
     * @return PermanentInstructorDTO - mapped PermanentInstructorDTO from found PermanentInstructor object
     */
    @Transactional
    public PermanentInstructorDTO deletePermanentInstructor(PermanentInstructorDTO permanentInstructorDTO) {
        PermanentInstructor foundInstructor = (PermanentInstructor) instructorRepository.findInstructorByPhoneNumberAndIdIsNot(permanentInstructorDTO.getPhoneNumber(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Instructor can not be found!"));

        PermanentInstructorDTO result = instructorMapper.mapFromPermanentInstructorToPermanentInstructorDTO(foundInstructor);
        instructorRepository.delete(foundInstructor);
        return result;
    }

    /**
     * checks if VisitingResearcher exits in database with given VisitingResearcherDTO's phoneNumber
     * if it doesn't exist, throws an exception
     * if it exists, maps the found VisitingResearcher to VisitingResearcherDTO
     * calls delete() from repository with found VisitingResearcher object
     *
     * @param visitingResearcherDTO - VisitingResearcherDTO request object
     * @return VisitingResearcherDTO - mapped VisitingResearcherDTO from found VisitingResearcher object
     */
    @Transactional
    public VisitingResearcherDTO deleteVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO) {
        VisitingResearcher foundInstructor = (VisitingResearcher) instructorRepository.findInstructorByPhoneNumberAndIdIsNot(visitingResearcherDTO.getPhoneNumber(), 0)
                .orElseThrow(() -> new EntityNotFoundException("Instructor can not be found!"));

        VisitingResearcherDTO result = instructorMapper.mapFromVisitingResearcherToVisitingResearcherDTO(foundInstructor);
        instructorRepository.delete(foundInstructor);
        return result;
    }

    /**
     * checks if Instructor exists in database with given instructorId
     * if it does not exist, throws an exception.
     * if it exists, due to Instructor's subtype, it makes the mapping process to related DTO
     * deletes the found instructor via calling delete() from repository
     *
     * @param instructorId - ID of the will deleted Instructor
     * @return InstructorDTO - mapped related InstructorDTO of the deleted Instructor
     */
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

    /**
     * helper method for mapping CourseDTO's instructorId to Course's Instructor object
     * gets the Instructor of given id
     *
     * @param instructorId - ID of the Instructor
     * @return Instructor - found instructor related to ID
     */
    public Instructor getCourseInstructorById(long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with id: " + instructorId + " can not be found!"));
    }

    /**
     * helper method for checking if an Instructor with given phoneNumber exists in database
     * calls findInstructorByPhoneNumberAndIdIsNot() method from repository
     * with given phoneNumber and excludes given instructorId for not preventing update opertaion with same phoneNumber.
     *
     * @param phoneNumber  - phoneNumber of Instructor for checking uniqueness
     * @param instructorId - ID of the Instructor for excluding phoneNumber check on update methods
     */
    private void checkIfInstructorExists(String phoneNumber, long instructorId) {

        if (instructorRepository.findInstructorByPhoneNumberAndIdIsNot(phoneNumber, instructorId).isPresent()) {
            throw new InstructorIsAlreadyExistException("An instructor with this phone number already exists!");
        }
    }
}
