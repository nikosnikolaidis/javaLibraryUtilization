package javaLibraryUtilization.repositories;

import javaLibraryUtilization.models.LibraryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryDTO,Long> {
}
