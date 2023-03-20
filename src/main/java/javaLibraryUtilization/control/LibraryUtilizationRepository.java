package javaLibraryUtilization.control;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUtilizationRepository extends JpaRepository<ProjectDTO,Long> {
}
