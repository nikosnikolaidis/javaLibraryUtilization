package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.MethodDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MethodDetailsRepository extends JpaRepository<MethodDetailsDTO,Long> {
}
