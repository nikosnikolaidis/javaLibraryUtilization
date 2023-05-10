package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.CallDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<CallDTO,Long> {
}
