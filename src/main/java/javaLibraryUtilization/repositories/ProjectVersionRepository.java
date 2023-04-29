package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.ProjectVersionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectVersionRepository extends JpaRepository<ProjectVersionDTO,Long> {

    ProjectVersionDTO findByProjectName(String projectName);
    ProjectVersionDTO findBySha(String sha);
}
