package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.ProjectDTO;
import javaLibraryUtilization.models.ProjectVersionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ProjectRepository extends JpaRepository<ProjectDTO,Long> {
    Optional<ProjectDTO> findByProjectName(String projectName);
}

