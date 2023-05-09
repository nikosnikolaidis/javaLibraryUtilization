package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.ProjectModuleDTO;
import javaLibraryUtilization.models.ProjectVersionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectModuleRepository extends JpaRepository<ProjectModuleDTO,Long> {

      ProjectModuleDTO findByProjectName(String projectName);
}
