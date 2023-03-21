package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.ProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectDTO,Long> {

}
