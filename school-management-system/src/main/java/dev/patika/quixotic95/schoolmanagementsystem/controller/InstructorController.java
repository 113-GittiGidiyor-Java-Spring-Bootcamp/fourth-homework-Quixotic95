package dev.patika.quixotic95.schoolmanagementsystem.controller;

import dev.patika.quixotic95.schoolmanagementsystem.dto.InstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.PermanentInstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.VisitingResearcherDTO;
import dev.patika.quixotic95.schoolmanagementsystem.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InstructorController {

    private final InstructorService instructorService;

    // dependency injection with @Autowired annotation (not necessary to write, injects automatically; but placed for better-reading)
    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // expose "/instructors" and return list of instructors
    @GetMapping("/instructors")
    public ResponseEntity<?> findAllInstructors() {
        List<InstructorDTO> result = instructorService.findAllInstructors();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for GET /instructors/{instructorId} to get an instructor by id
    @GetMapping("/instructors/{instructorId}")
    public ResponseEntity<?> findInstructorById(@PathVariable long instructorId) {
        InstructorDTO result = instructorService.findInstructorById(instructorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for POST /instructors/permanentInstructor - add new permanent instructor
    @PostMapping("/instructors/permanentInstructor")
    public ResponseEntity<?> savePermanentInstructor(@RequestBody @Valid PermanentInstructorDTO permanentInstructorDTO) {
        InstructorDTO result = instructorService.savePermanentInstructor(permanentInstructorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for POST /instructors/visitingResearcher - add new visiting researcher
    @PostMapping("/instructors/visitingResearcher")
    public ResponseEntity<?> saveVisitingResearcher(@RequestBody @Valid VisitingResearcherDTO visitingResearcherDTO) {
        InstructorDTO result = instructorService.saveVisitingResearcher(visitingResearcherDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for PUT /instructors/permanentInstructor - update existing permanent instructor
    @PutMapping("/instructors/permanentInstructor/{instructorId}")
    public ResponseEntity<?> updatePermanentInstructor(@PathVariable long instructorId, @RequestBody @Valid PermanentInstructorDTO permanentInstructorDTO) {
        PermanentInstructorDTO result = instructorService.updatePermanentInstructor(permanentInstructorDTO, instructorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for PUT /instructors/visitingResearcher - update existing visiting researcher
    @PutMapping("/instructors/visitingResearcher/{instructorId}")
    public ResponseEntity<?> updateVisitingResearcher(@PathVariable long instructorId, @RequestBody @Valid VisitingResearcherDTO visitingResearcherDTO) {
        VisitingResearcherDTO result = instructorService.updateVisitingResearcher(visitingResearcherDTO, instructorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /instructors - delete instructor
    @DeleteMapping("/instructors/permanentInstructor")
    public ResponseEntity<?> deletePermanentInstructor(@RequestBody @Valid PermanentInstructorDTO permanentInstructorDTO) {
        PermanentInstructorDTO result = instructorService.deletePermanentInstructor(permanentInstructorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /instructors - delete instructor
    @DeleteMapping("/instructors/visitingResearcher")
    public ResponseEntity<?> deleteVisitingResearcher(@RequestBody @Valid VisitingResearcherDTO visitingResearcherDTO) {
        VisitingResearcherDTO result = instructorService.deleteVisitingResearcher(visitingResearcherDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // mapping for DELETE /instructors/{instructorId} - delete instructor by id
    @DeleteMapping("/instructors/{instructorId}")
    public ResponseEntity<?> deleteInstructorById(@PathVariable long instructorId) {
        InstructorDTO result = instructorService.deleteInstructorById(instructorId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

