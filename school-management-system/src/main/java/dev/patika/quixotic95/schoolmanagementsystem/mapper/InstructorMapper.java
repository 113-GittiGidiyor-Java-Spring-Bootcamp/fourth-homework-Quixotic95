package dev.patika.quixotic95.schoolmanagementsystem.mapper;

import dev.patika.quixotic95.schoolmanagementsystem.dto.InstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.PermanentInstructorDTO;
import dev.patika.quixotic95.schoolmanagementsystem.dto.VisitingResearcherDTO;
import dev.patika.quixotic95.schoolmanagementsystem.entity.Instructor;
import dev.patika.quixotic95.schoolmanagementsystem.entity.PermanentInstructor;
import dev.patika.quixotic95.schoolmanagementsystem.entity.VisitingResearcher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class InstructorMapper {

    public abstract Instructor mapFromInstructorDTOToInstructor(InstructorDTO instructorDTO);

    public abstract InstructorDTO mapFromInstructorToInstructorDTO(Instructor instructor);

    public abstract VisitingResearcher mapFromVisitingResearcherDTOToVisitingResearcher(VisitingResearcherDTO visitingResearcherDTO);

    public abstract VisitingResearcherDTO mapFromVisitingResearcherToVisitingResearcherDTO(VisitingResearcher visitingResearcher);

    public abstract PermanentInstructor mapFromPermanentInstructorDTOToPermanentInstructor(PermanentInstructorDTO permanentInstructorDTO);

    public abstract PermanentInstructorDTO mapFromPermanentInstructorToPermanentInstructorDTO(PermanentInstructor permanentInstructor);

}
