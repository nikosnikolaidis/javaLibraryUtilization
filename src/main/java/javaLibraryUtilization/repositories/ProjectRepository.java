package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.ProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDTO,Long> {

    ProjectDTO findByProjectName(String projectName);

}
